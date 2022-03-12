package feature_fidling;

import util.math.matrix.Matrix3By3;

public class Matrix3By3Inverse {

    public static void main(String[] args) {
        Matrix3By3 matrix3By3 = new Matrix3By3(
                5.5, 24.1, 3.7,
                102, 85, 43,
                118, 58, 77);
        Matrix3By3 inverse = matrix3By3.inverse();
        System.out.println(inverse);
    }
}
