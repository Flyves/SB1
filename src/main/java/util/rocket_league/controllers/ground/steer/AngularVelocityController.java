package util.rocket_league.controllers.ground.steer;

import util.data_structure.tupple.Tuple3;
import util.rocket_league.controllers.ground.GroundDrivingInMidAirException;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.Behaviour;

public class AngularVelocityController implements Behaviour<Tuple3<ExtendedCarData, ControlsOutput, Double>, ControlsOutput> {
    public AngularVelocityController() {
    }

    @Override
    public ControlsOutput exec(final Tuple3<ExtendedCarData, ControlsOutput, Double> io) {
        if(!io.value1.hasWheelContact) throw new GroundDrivingInMidAirException();
        final double desiredAngularVelocity = io.value3;
        final double carAbsSpeed = Math.abs(io.value1.velocity.dotProduct(io.value1.orientation.nose));
        final double angularVelocity = io.value1.angularVelocity.dotProduct(io.value1.orientation.roof);

        final double steer = findSteerAmount(carAbsSpeed, desiredAngularVelocity);
        io.value2.steer = (float)steer;

        if(shouldDrift(desiredAngularVelocity, carAbsSpeed, angularVelocity, steer)) io.value2.isDrifting = true;

        return io.value2;
    }

    private boolean shouldDrift(
            final double desiredAngularVelocity,
            final double carAbsSpeed,
            final double angularVelocity,
            final double steer) {
        return Math.abs(steer) > 1
                && Math.abs(desiredAngularVelocity) - Math.abs(angularVelocity) > 0
                && Math.abs(angularVelocity) > GroundSteering.findMaxSpin(carAbsSpeed) * 0.7
                && isPositive(angularVelocity) == isPositive(desiredAngularVelocity);
    }

    private boolean isPositive(final double angularVelocity) {
        return angularVelocity >= 0;
    }

    private double findSteerAmount(final double carAbsSpeed, final double desiredAngularVelocity) {
        return GroundSteering.computeControlsOutput(-desiredAngularVelocity, carAbsSpeed);
    }
}
