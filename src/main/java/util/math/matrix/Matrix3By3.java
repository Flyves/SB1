package util.math.matrix;

import util.math.vector.Vector2;
import util.math.vector.Vector3;

public class Matrix3By3 {

    public static final Matrix3By3 UNIT = new Matrix3By3(1, 0, 0,
                                                        0, 1, 0,
                                                        0, 0, 1);
    public final Vector3 a1;
    public final Vector3 a2;
    public final Vector3 a3;

    public Matrix3By3(double a11, double a12, double a13,
                      double a21, double a22, double a23,
                      double a31, double a32, double a33) {
        this.a1 = new Vector3(a11, a12, a13);
        this.a2 = new Vector3(a21, a22, a23);
        this.a3 = new Vector3(a31, a32, a33);
    }

    public Matrix3By3(Vector3 a1, Vector3 a2, Vector3 a3) {
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
    }

    public static Matrix3By3 unitOf(Vector3 that) {
        return new Matrix3By3(
                new Vector3(that.x, 0, 0),
                new Vector3(0, that.y, 0),
                new Vector3(0, 0, that.z));
    }

    public Matrix3By3 minus(Matrix3By3 that) {
        return new Matrix3By3(
                this.a1.minus(that.a1),
                this.a2.minus(that.a2),
                this.a3.minus(that.a3));
    }

    public Matrix3By3 scaled(double s) {
        return new Matrix3By3(a1.scaled(s), a2.scaled(s), a3.scaled(s));
    }

    public Matrix3By3 multiply(final Matrix3By3 that) {
        final Matrix3By3 transposed = that.transpose();
        return new Matrix3By3(
                this.a1.dotProduct(transposed.a1), this.a1.dotProduct(transposed.a2), this.a1.dotProduct(transposed.a3),
                this.a2.dotProduct(transposed.a1), this.a2.dotProduct(transposed.a2), this.a2.dotProduct(transposed.a3),
                this.a3.dotProduct(transposed.a1), this.a3.dotProduct(transposed.a2), this.a3.dotProduct(transposed.a3));
    }

    public Vector3 multiply(Vector3 that) {
        return new Vector3(
                a1.dotProduct(that),
                a2.dotProduct(that),
                a3.dotProduct(that));
    }

    public Matrix3By3 inverse() {
        final Matrix3By3 adjugate = adjugate();
        final double determinant = determinant();
        if(determinant == 0d) return Matrix3By3.of(Double.NaN);
        final double inverseDeterminant = 1/determinant;
        return adjugate.scaled(inverseDeterminant);
    }

    public static Matrix3By3 of(final double x) {
        return new Matrix3By3(
                Vector3.of(x),
                Vector3.of(x),
                Vector3.of(x));
    }

    public Matrix3By3 adjugate() {
        return cofactor().transpose();
    }

    public Matrix3By3 cofactor() {
        Matrix3By3 minorMatrix = minor();
        return new Matrix3By3(
                minorMatrix.a1.scaled(1, -1, 1),
                minorMatrix.a2.scaled(-1, 1, -1),
                minorMatrix.a3.scaled(1, -1, 1));
    }

    public Matrix3By3 minor() {
        return new Matrix3By3(
                new Vector3(a2.y*a3.z - a2.z*a3.y, a2.x*a3.z - a2.z*a3.x, a2.x*a3.y - a2.y*a3.x),
                new Vector3(a1.y*a3.z - a1.z*a3.y, a1.x*a3.z - a1.z*a3.x, a1.x*a3.y - a1.y*a3.x),
                new Vector3(a1.y*a2.z - a1.z*a2.y, a1.x*a2.z - a1.z*a2.x, a1.x*a2.y - a1.y*a2.x));
    }

    public Matrix3By3 transpose() {
        return new Matrix3By3(
                a1.x, a2.x, a3.x,
                a1.y, a2.y, a3.y,
                a1.z, a2.z, a3.z);
    }

    public double determinant() {
        return    a1.x*(a2.y*a3.z - a2.z*a3.y)
                - a1.y*(a2.x*a3.z - a2.z*a3.x)
                + a1.z*(a2.x*a3.y - a2.y*a3.x);
    }

    @Override
    public String toString() {
        return a1.toString() + "\n"
                + a2.toString() + "\n"
                + a3.toString();
    }
}
