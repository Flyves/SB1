package util.rocket_league.dynamic_objects.car;

import rlbot.flat.PlayerInfo;
import util.math.vector.Vector3;

/**
 * The car's orientation in space, a.k.a. what direction it's pointing.
 *
 * This class is here for your convenience, it is NOT part of the framework. You can change it as much
 * as you want, or delete it.
 */
public class Orientation {

    /** The direction that the front of the car is facing */
    public Vector3 nose;

    /** The direction the roof of the car is facing. (0, 0, 1) means the car is upright. */
    public Vector3 roof;

    /** The direction that the right side of the car is facing. */
    public Vector3 right;

    public Orientation() {
        this.nose = Vector3.X_VECTOR;
        this.roof = Vector3.Z_VECTOR;
        this.right = nose.crossProduct(roof);
    }

    public Orientation(Vector3 nose, Vector3 roof) {
        this.nose = nose;
        this.roof = roof;
        this.right = nose.crossProduct(roof);
    }

    public static Orientation fromFlatbuffer(PlayerInfo playerInfo) {
        return convert(
                playerInfo.physics().rotation().pitch(),
                playerInfo.physics().rotation().yaw(),
                playerInfo.physics().rotation().roll());
    }

    /**
     * All params are in radians.
     */
    private static Orientation convert(double pitch, double yaw, double roll) {
        double noseX = -1 * Math.cos(pitch) * Math.cos(yaw);
        double noseY = Math.cos(pitch) * Math.sin(yaw);
        double noseZ = Math.sin(pitch);

        double roofX = Math.cos(roll) * Math.sin(pitch) * Math.cos(yaw) + Math.sin(roll) * Math.sin(yaw);
        double roofY = Math.cos(yaw) * Math.sin(roll) - Math.cos(roll) * Math.sin(pitch) * Math.sin(yaw);
        double roofZ = Math.cos(roll) * Math.cos(pitch);

        return new Orientation(new Vector3(noseX, noseY, noseZ), new Vector3(roofX, roofY, roofZ));
    }

    public Orientation rotate(Vector3 orientationRotator) {
        return new Orientation(
                nose.rotate(orientationRotator),
                roof.rotate(orientationRotator));
    }

    public Vector3 findAngularDisplacementTo(Orientation that) {
        final Vector3 noseRotationDisk = findRotationDisk(this.nose, that.nose);
        final Vector3 roofRotationDisk = findRotationDisk(this.roof, that.roof);
        final Vector3 directionOfDisplacementVector = noseRotationDisk.crossProduct(roofRotationDisk);
        final Vector3 flatteningRotator = directionOfDisplacementVector.findRotator(Vector3.Z_VECTOR);

        final double displacementVectorMagnitude;
        if(!noseRotationDisk.isZero()) {
            displacementVectorMagnitude = signedAngleBetweenDirectionsForASpecificRotator(that.nose, this.nose, flatteningRotator);
        }
        else {
            displacementVectorMagnitude = signedAngleBetweenDirectionsForASpecificRotator(that.roof, this.roof, flatteningRotator);
        }

        return directionOfDisplacementVector.scaledToMagnitude(displacementVectorMagnitude);
    }

    private Vector3 findRotationDisk(final Vector3 direction1, final Vector3 direction2) {
        final Vector3 middleNoseVector = direction1.plus(direction2).mutableNormalized();
        return middleNoseVector.crossProduct(direction1).mutableCrossProduct(middleNoseVector);
    }

    private double signedAngleBetweenDirectionsForASpecificRotator(final Vector3 direction1, final Vector3 direction2, final Vector3 rotator) {
        final Vector3 rotatorCopy = new Vector3(rotator);
        return direction2.mutableParamRotate(rotatorCopy).flatten()
                .correctionAngle(direction1.mutableParamRotate(rotator).flatten());
    }

    public Vector3 asAngularDisplacement() {
        return new Orientation().findAngularDisplacementTo(this);
    }
}
