package util.data_structure.bvh.math;

import util.data_structure.bvh.shape.Triangle3;

import java.io.Serializable;
import java.util.Objects;

public class Vector3 implements Serializable {
    public double x;
    public double y;
    public double z;

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3() {
        this(0, 0, 0);
    }

    public Vector3(util.math.vector.Vector3 that) {
        this.x = that.x;
        this.y = that.y;
        this.z = that.z;
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

    public Vector3 scaled(final double x, final double y, final double z) {
        return new Vector3(this.x * x, this.y * y, this.z * z);
    }

    public Vector3 scaled(Vector3 that) {
        return scaled(that.x, that.y, that.z);
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

    public double dotProduct(Vector3 other) {
        return x * other.x + y * other.y + z * other.z;
    }

    public boolean isZero() {
        return x == 0 && y == 0 && z == 0;
    }

    public Vector3 crossProduct(Vector3 v) {
        double tx = y * v.z - z * v.y;
        double ty = z * v.x - x * v.z;
        double tz = x * v.y - y * v.x;
        return new Vector3(tx, ty, tz);
    }

    public Vector3 projectOnto(final Triangle3 triangle) {
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

        if(        a >= 0 && a <= 1
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
        final double u = unitClamp(p.minus(start).dotProduct(dir)/dir.dotProduct(dir));
        return start.plus(dir.scaled(u));
    }

    private double unitClamp(final double valueToClamp) {
        return Math.max(0, Math.min(1, valueToClamp));
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
}
