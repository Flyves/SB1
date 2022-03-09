package util.rocket_league.controllers.ground.steer.orientation;

import util.data_structure.tupple.Tuple3;
import util.math.vector.Vector2;
import util.math.vector.Vector3;
import util.rocket_league.controllers.ground.steer.angular_velocity.AngularVelocityController;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.Behaviour;

public class OrientationController implements Behaviour<Tuple3<ExtendedCarData, ControlsOutput, Vector3>, ControlsOutput> {
    private static final double CONVERGENCE_RATE = 5;
    private final OrientationProfile orientationProfile;
    private final AngularVelocityController angularVelocityController;

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
        final double correctionAngle = computeCorrectionAngle(io);
        final double maxAngularVelocity = computeMaxAngularVelocity(io, correctionAngle);
        // TODO : take into account the angular velocity instead of just the angle
        //        something like "correctionAngle * CONVERGENCE_RATE * Math.abs(io.value1.angularVelocity.dotProduct(io.value1.orientation.roof))"
        return signedClamp(correctionAngle * CONVERGENCE_RATE, maxAngularVelocity);
    }

    private double signedClamp(double value, double maxOrMinValue) {
        return maxOrMinValue > 0 ? Math.min(value, maxOrMinValue) : Math.max(value, maxOrMinValue);
    }

    private double computeCorrectionAngle(final Tuple3<ExtendedCarData, ControlsOutput, Vector3> io) {
        final Vector3 rotator = io.value1.orientation.asAngularDisplacement().scaled(-1);
        final Vector3 localDestination = io.value3.minus(io.value1.position).rotate(rotator);
        final Vector2 v = localDestination.flatten().normalized();
        return Math.atan2(v.y, v.x);
    }

    private double computeMaxAngularVelocity(final Tuple3<ExtendedCarData, ControlsOutput, Vector3> io, final double correctionAngle) {
        final double carAbsSpeed = io.value1.groundSpeed;
        final double maxAbsAngularVelocity = orientationProfile.angularVelocityFunction.apply(carAbsSpeed);
        return Math.copySign(maxAbsAngularVelocity, correctionAngle);
    }
}
