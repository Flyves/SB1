package util.math.vector;

import com.google.flatbuffers.FlatBufferBuilder;
import rlbot.flat.Rotator;
import rlbot.gamestate.DesiredVector3;
import util.rocket_league.dynamic_objects.car.Orientation;
import util.math.matrix.Matrix3By3;
import util.shape.Plane3D;
import util.shape.Triangle3D;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;

/**
 * A simple 3d vector class with the most essential operations.
 *
 * This class is here for your convenience, it is NOT part of the framework. You can add to it as much
 * as you want, or delete it.
 */
public class Vector3 implements Serializable {

    public static final Vector3 UP_VECTOR = new Vector3(0, 0, 1);
    public static final Vector3 DOWN_VECTOR = new Vector3(0, 0, -1);
    public static final Vector3 X_VECTOR = new Vector3(1, 0, 0);
    public static final Vector3 Y_VECTOR = new Vector3(0, 1, 0);

    public static Vector3 of(double x) {
        return new Vector3(x, x, x);
    }

    public double x;
    public double y;
    public double z;

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Vector2 xy, double z) { this(xy.x, xy.y, z); }

    public Vector3() {
        this(0, 0, 0);
    }

    public Vector3(Vector3 v) {
        this(v.x, v.y, v.z);
    }

    public Vector3(Rotator rotator) {
        this(rotator.pitch(), rotator.yaw(), rotator.roll());
    }

    public Vector3(rlbot.flat.Vector3 vec) {
        // Invert the X value so that the axes make more sense.
        this(-vec.x(), vec.y(), vec.z());
    }

    public Vector3(DesiredVector3 location) {
        this(location.getX(), location.getY(), location.getZ());
    }

    public int toFlatbuffer(FlatBufferBuilder builder) {
        // Invert the X value again so that rlbot sees the format it expects.
        return rlbot.flat.Vector3.createVector3(builder, (float)-x, (float)y, (float)z);
    }

    public Vector3 plus(Vector3 other) {
        return new Vector3(x + other.x, y + other.y, z + other.z);
    }

    public Vector3 minus(Vector3 other) {
        return new Vector3(x - other.x, y - other.y, z - other.z);
    }

    public Vector3 scaled(double scale) {
        return new Vector3(x * scale, y * scale, z * scale);
    }
    public Vector3 mutableScaled(double scale) {
        x = x * scale;
        y = y * scale;
        z = z * scale;
        return this;
    }

    public Vector3 scaled(Vector3 scale) {
        return new Vector3(x * scale.x, y * scale.y, z * scale.z);
    }

    public Vector3 scaled(double scaleX, double scaleY, double scaleZ) {
        return new Vector3(x * scaleX, y * scaleY, z * scaleZ);
    }
    public Vector3 scaled(Matrix3By3 scale) {
        return new Vector3(
                x*scale.a1.x + y*scale.a2.x + z*scale.a3.x,
                x*scale.a1.y + y*scale.a2.y + z*scale.a3.y,
                x*scale.a1.z + y*scale.a2.z + z*scale.a3.z);
    }

    /**
     * If magnitude is negative, we will return a vector facing the opposite direction.
     */
    public Vector3 scaledToMagnitude(double magnitude) {
        if (isZero()) {
            return new Vector3();
        }
        double scaleRequired = magnitude / magnitude();
        return scaled(scaleRequired);
    }

    public Vector3 mutableScaledToMagnitude(double magnitude) {
        if (isZero()) {
            return this;
        }
        double scaleRequired = magnitude / magnitude();
        return mutableScaled(scaleRequired);
    }

    public Vector3 scaledToMagnitude(double magnitudeX, double magnitudeY, double magnitudeZ) {
        double scaleRequiredX = magnitudeX / Math.abs(x);
        if (Double.isNaN(scaleRequiredX)) {
            scaleRequiredX = magnitudeX;
        }
        double scaleRequiredY = magnitudeY / Math.abs(y);
        if (Double.isNaN(scaleRequiredY)) {
            scaleRequiredY = magnitudeY;
        }
        double scaleRequiredZ = magnitudeZ / Math.abs(z);
        if (Double.isNaN(scaleRequiredZ)) {
            scaleRequiredZ = magnitudeZ;
        }
        return scaled(scaleRequiredX, scaleRequiredY, scaleRequiredZ);
    }

    public double distance(Vector3 other) {
        double xDiff = x - other.x;
        double yDiff = y - other.y;
        double zDiff = z - other.z;
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff + zDiff * zDiff);
    }

    public double distanceSquared(Vector3 other) {
        return minus(other).magnitudeSquared();
    }

    public Vector3 inverse() {
        return new Vector3(1/x, 1/y, 1/z);
    }

    public double magnitude() {
        return Math.sqrt(magnitudeSquared());
    }

    public double magnitudeSquared() {
        return x * x + y * y + z * z;
    }

    public Vector3 normalized() {

        if (isZero()) {
            return new Vector3();
        }
        return this.scaled(1 / magnitude());
    }
    public Vector3 mutableNormalized() {

        if (isZero()) {
            return this;
        }
        return this.mutableScaled(1 / magnitude());
    }

    public double dotProduct(Vector3 other) {
        return x * other.x + y * other.y + z * other.z;
    }

    public boolean isZero() {
        return x == 0 && y == 0 && z == 0;
    }

    public Vector2 flatten() {
        return new Vector2(x, y);
    }

    public double angle(Vector3 v) {
        final double mag2 = magnitudeSquared();
        final double vmag2 = v.magnitudeSquared();
        final double dot = dotProduct(v);
        return Math.acos(dot / Math.sqrt(mag2 * vmag2));
    }

    public Vector3 crossProduct(Vector3 v) {
        double tx = y * v.z - z * v.y;
        double ty = z * v.x - x * v.z;
        double tz = x * v.y - y * v.x;
        return new Vector3(tx, ty, tz);
    }
    public Vector3 mutableCrossProduct(Vector3 v) {
        final double x2 = y * v.z - z * v.y;
        final double y2 = z * v.x - x * v.z;
        final double z2 = x * v.y - y * v.x;
        x = x2;
        y = y2;
        z = z2;
        return this;
    }

    public double angleWith(Vector3 vector) {
        double cosine = this.dotProduct(vector)/(this.magnitude()*vector.magnitude());
        return Math.acos(cosine);
    }

    public Vector3 rotateToOrientationReferenceFrom(Orientation orientation) {
        final Vector3 angularDisplacement = orientation.findAngularDisplacementTo(new Orientation());
        return mutableParamRotate(angularDisplacement);
    }

    public Vector3 rotateFromOrientationReferenceTo(Orientation orientation) {
        final Vector3 angularDisplacement = new Orientation().findAngularDisplacementTo(orientation);
        return mutableParamRotate(angularDisplacement);
    }

    public Vector3 rotate(final Vector3 r) {
        final double a = r.magnitude()*0.5;
        final Vector3 sr = r.scaledToMagnitude(Math.sin(a));
        final Quaternion qr = new Quaternion(Math.cos(a), sr);
        final Quaternion qv = new Quaternion(0, this);

        return ((qr.multiply(qv)).multiply(qr.mutableConjugate())).mutableToVector3();
    }

    public Vector3 mutableParamRotate(final Vector3 r) {
        final double a = r.magnitude()*0.5;
        final Vector3 sr = r.mutableScaledToMagnitude(Math.sin(a));
        final Quaternion qr = new Quaternion(Math.cos(a), sr);
        final Quaternion qv = new Quaternion(0, this);

        return ((qr.multiply(qv)).multiply(qr.mutableConjugate())).mutableToVector3();
    }

    public Vector3 projectOnto(Vector3 vectorToProjectOnto) {
        return vectorToProjectOnto.scaled(this.dotProduct(vectorToProjectOnto)/vectorToProjectOnto.magnitudeSquared());
    }

    public Vector3 projectOnto(Plane3D plane) {
        return this.minus(this.projectOnto(plane.normal.direction))
                .plus(plane.normal.offset.projectOnto(plane.normal.direction));
    }

    public Vector3 projectOnto(final Triangle3D triangle) {
        final Vector3 p1 = triangle.point0;
        final Vector3 p2 = triangle.point1;
        final Vector3 p3 = triangle.point2;
        final Vector3 p = this;
        final Vector3 u = p2.minus(p1);
        final Vector3 v = p3.minus(p1);
        final Vector3 n = u.crossProduct(v);
        final Vector3 w = p.minus(p1);
        double Y = (u.crossProduct(w).dotProduct(n)) / n.magnitudeSquared();
        double B = (w.crossProduct(v).dotProduct(n)) / n.magnitudeSquared();

        double a = 1-Y-B;

        if(a >= 0 && a <= 1
                && B >= 0 && B <= 1
                && Y >= 0 && Y <= 1) {
            return p1.scaled(a).plus(p2.scaled(B)).plus(p3.scaled(Y));
        }
        else {
            final Vector3 edgePosition1 = triangleEdgePosition(p1, p2.minus(p1), p);
            final Vector3 edgePosition2 = triangleEdgePosition(p2, p3.minus(p2), p);
            final Vector3 edgePosition3 = triangleEdgePosition(p3, p1.minus(p3), p);

            final double dist1 = edgePosition1.minus(p).magnitudeSquared();
            final double dist2 = edgePosition2.minus(p).magnitudeSquared();
            final double dist3 = edgePosition3.minus(p).magnitudeSquared();

            if(dist1 <= dist2 && dist1 <= dist3) {
                return edgePosition1;
            }
            if(dist2 <= dist1 && dist2 <= dist3) {
                return edgePosition2;
            }
            return edgePosition3;
        }
    }

    private Vector3 triangleEdgePosition(final Vector3 start, final Vector3 dir, final Vector3 p) {
        final double u = clamp(p.minus(start).dotProduct(dir)/dir.dotProduct(dir), 0, 1);
        return start.plus(dir.scaled(u));
    }

    private double clamp(final double valueToClamp, final double min, final double max) {
        return Math.max(min, Math.min(max, valueToClamp));
    }

    @Override
    public String toString() {
        return "[ x:" + this.x + ", y:" + this.y + ", z:" + this.z + " ]";
    }

    @Override
    public int hashCode() {
        return Objects.hash((int)x, (int)y, (int)z);
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Vector3)) {
            return false;
        }
        Vector3 that = (Vector3)o;
        return this.x == that.x
            && this.y == that.y
            && this.z == that.z;
    }

    public Quaternion toQuaternion() {
        return new Quaternion(0, this);
    }

    public DesiredVector3 toDesiredVector3() {
        return new DesiredVector3((float)x, (float)y, (float)z);
    }

    public Vector3Int toVector3Int() {
        return new Vector3Int((int)x, (int)y, (int)z);
    }

    public rlbot.vector.Vector3 toFlatVector() {
        return new rlbot.vector.Vector3((float)-x, (float)y, (float)z);
    }

    public Matrix3By3 asUnitMatrix() {
        return new Matrix3By3(
                new Vector3(x, 0, 0),
                new Vector3(0, y, 0),
                new Vector3(0, 0, z));
    }

    public Quaternion asQuaternionExponential() {
        final double magnitude = magnitude();
        return new Quaternion(Math.cos(magnitude), this.normalized().scaled(Math.sin(magnitude)));
    }

    public Vector3 findRotator(Vector3 v) {
        if(minus(v).magnitudeSquared() < 0.000001) {
            return new Vector3();
        }
        if(crossProduct(v).magnitudeSquared() < 0.000001) {
            if(v.x != 0 && v.y != 0) {
                return findRotator(new Vector3(0, 0, 1)).scaledToMagnitude(angle(v));
            }
            else {
                //return findRotator(new Vector3(1, 0, 0)).scaledToMagnitude(angle(v));
            }
        }
        return crossProduct(v).mutableScaledToMagnitude(angle(v));
    }

    public Vector3 abs() {
        return new Vector3(Math.abs(x), Math.abs(y), Math.abs(z));
    }

    public Vector3 axisIndependentCap(double maxMagnitude) {
        return new Vector3(singleValueCap(x, maxMagnitude), singleValueCap(y, maxMagnitude), singleValueCap(z, maxMagnitude));
    }

    private double singleValueCap(double value, double maxMagnitude) {
        final double absoluteMaxMagnitude = Math.abs(maxMagnitude);
        if(value > absoluteMaxMagnitude) {
            return absoluteMaxMagnitude;
        }
        if(value < -absoluteMaxMagnitude) {
            return -absoluteMaxMagnitude;
        }
        return value;
    }

    public Vector3 capped(double magnitudeCap) {
        if(magnitudeSquared() > magnitudeCap*magnitudeCap) {
            return scaledToMagnitude(magnitudeCap);
        }

        return new Vector3(this);
    }

    public Matrix3By3 toCrossProductMatrix() {
        return new Matrix3By3(
                0, -z, y,
                z, 0, -x,
                -y, x, 0);
    }

    public Orientation asOrientation() {
        return new Orientation().rotate(this);
    }
}
