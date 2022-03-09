package util.rocket_league.controllers.ground.steer.angular_velocity;

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
        final double carSpeed = io.value1.groundSpeedForward;
        final double angularVelocity = io.value1.groundAngularVelocity;
        final double desiredAngularVelocity = io.value3;
        float steer = findSteerAmount(carSpeed, desiredAngularVelocity);

        if(shouldDrift(desiredAngularVelocity, carSpeed, angularVelocity, steer)) io.value2.isDrifting = true;
        if(io.value2.isDrifting && carSpeed < 0) steer = -steer;
        io.value2.steer = steer;

        return io.value2;
    }

    private float findSteerAmount(final double carSpeed, final double desiredAngularVelocity) {
        return GroundSteering.computeControlsOutput(-desiredAngularVelocity, carSpeed)
                .floatValue();
    }

    private boolean shouldDrift(
            final double desiredAngularVelocity,
            final double carSpeed,
            final double angularVelocity,
            final double steer) {
        return Math.abs(steer) > 1
                && Math.abs(desiredAngularVelocity) - Math.abs(angularVelocity) > 0
                && Math.abs(angularVelocity) > GroundSteering.findMaxSpin(carSpeed) * 0.9
                && isPositive(angularVelocity) == isPositive(desiredAngularVelocity);
    }

    private boolean isPositive(final double angularVelocity) {
        return angularVelocity >= 0;
    }
}
