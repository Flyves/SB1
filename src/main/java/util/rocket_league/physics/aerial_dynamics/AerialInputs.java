package util.rocket_league.physics.aerial_dynamics;

import util.math.vector.Vector3;

public class AerialInputs {

    private static final Vector3 MAXIMUM_ANGULAR_ACCELERATION_SCALAR = new Vector3(37.58323229, -12.46001085, -9.101860202);

    public double roll;
    public double pitch;
    public double yaw;

    public AerialInputs(double roll, double pitch, double yaw) {
        this.roll = roll;
        this.pitch = pitch;
        this.yaw = yaw;
    }

    public Vector3 toVector3() {
        return new Vector3(roll, pitch, yaw);
    }

    public static Vector3 asCappedVector(AerialInputs aerialInputs) {
        final Vector3 asVector = aerialInputs.toVector3();
        return asVector.axisIndependentCap(0.9999);
    }

    public static Vector3 asLocalAccelerationWithoutDampening(AerialInputs aerialInputs) {
        final Vector3 asVector = aerialInputs.toVector3();
        final Vector3 cappedVector = asVector.axisIndependentCap(0.9999);
        return cappedVector.scaled(MAXIMUM_ANGULAR_ACCELERATION_SCALAR);
    }
}
