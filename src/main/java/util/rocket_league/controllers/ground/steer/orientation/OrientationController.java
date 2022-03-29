package util.rocket_league.controllers.ground.steer.orientation;

import util.data_structure.tupple.Tuple3;
import util.math.vector.Vector2;
import util.math.vector.Vector3;
import util.rocket_league.Constants;
import util.rocket_league.controllers.ground.steer.angular_velocity.AngularVelocityController;
import util.rocket_league.dynamic_objects.car.CarData;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.Behaviour;

public class OrientationController implements Behaviour<Tuple3<ExtendedCarData, ControlsOutput, Vector3>, ControlsOutput> {
    private static final double CONVERGENCE_RATE = 5;
    private final OrientationProfile orientationProfile;
    private final AngularVelocityController angularVelocityController;
    private CarData previousCarData;
    private Vector3 previousDestination;

    public OrientationController(final OrientationProfile orientationProfile) {
        this.orientationProfile = orientationProfile;
        this.angularVelocityController = new AngularVelocityController();
    }

    @Override
    public ControlsOutput exec(final Tuple3<ExtendedCarData, ControlsOutput, Vector3> io) {
        final double desiredAngularVelocity = computeDesiredAngularVelocity(io);
        return angularVelocityController.exec(new Tuple3<>(io.value1, io.value2, desiredAngularVelocity));
    }

    private double computeDesiredAngularVelocity(Tuple3<ExtendedCarData, ControlsOutput, Vector3> io) {
        final double correctionAngle = computeCorrectionAngle(io.value1, io.value3);
        final double maxAngularVelocity = computeMaxAngularVelocity(io, correctionAngle);
        // TODO : take into account the angular velocity instead of just the angle
        //        something like "correctionAngle * CONVERGENCE_RATE * Math.abs(io.value1.angularVelocity.dotProduct(io.value1.orientation.roof))"

        // TODO : don't forget that the target is moving when we are rotating towards it and it's close by.
        //        We need to compute it's apparent angular velocity from the car's center, and take that angular velocity into account when turning.
        //        It's probably an intersection of 2 lines on a graph or something.
        final double movingDestinationCompensation = computeMovingDestinationCompensation(io);
        previousCarData = io.value1;
        previousDestination = io.value3;

        return signedClamp(correctionAngle * CONVERGENCE_RATE + movingDestinationCompensation, maxAngularVelocity);
    }

    private double computeMovingDestinationCompensation(final Tuple3<ExtendedCarData, ControlsOutput, Vector3> io) {
        if(previousCarData == null || previousDestination == null) {
            return 0;
        }
        final Vector2 localDestination = transformToFlatLocalCoordinates(io.value1, io.value3);
        final Vector2 previousLocalDestination = transformToFlatLocalCoordinates(previousCarData, previousDestination);
        final double angularVelocityError = previousLocalDestination.correctionAngle(localDestination) * Constants.BOT_REFRESH_RATE;
        return angularVelocityError + io.value1.groundAngularVelocity;
    }

    private double computeCorrectionAngle(final ExtendedCarData carData, final Vector3 destination) {
        final Vector2 localDestination = transformToFlatLocalCoordinates(carData, destination);
        return Math.atan2(localDestination.y, localDestination.x);
    }

    private double computeMaxAngularVelocity(final Tuple3<ExtendedCarData, ControlsOutput, Vector3> io, final double correctionAngle) {
        final double carAbsSpeed = io.value1.groundSpeed;
        final double maxAbsAngularVelocity = orientationProfile.angularVelocityFunction.apply(carAbsSpeed);
        return Math.copySign(maxAbsAngularVelocity, correctionAngle);
    }

    private double signedClamp(double value, double maxOrMinValue) {
        return maxOrMinValue > 0 ? Math.min(value, maxOrMinValue) : Math.max(value, maxOrMinValue);
    }

    private Vector2 transformToFlatLocalCoordinates(final CarData carData, final Vector3 point) {
        final Vector3 angularNormalizingRotator = carData.orientation.asAngularDisplacement().scaled(-1);
        final Vector3 localDestination = point.minus(carData.position).rotate(angularNormalizingRotator);
        return localDestination.flatten().normalized();
    }
}
