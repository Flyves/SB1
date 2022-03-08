package util.rocket_league.controllers.ground.throttle;

import util.data_structure.tupple.Tuple3;
import util.rocket_league.controllers.ground.GroundDrivingInMidAirException;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.Behaviour;

public class SpeedController implements Behaviour<Tuple3<ExtendedCarData, ControlsOutput, Double>, ControlsOutput> {
    @Override
    public ControlsOutput exec(Tuple3<ExtendedCarData, ControlsOutput, Double> io) {
        if(!io.value1.hasWheelContact) throw new GroundDrivingInMidAirException();

        final double speed = io.value1.velocity.dotProduct(io.value1.orientation.nose);
        final double desiredSpeed = io.value3;

        if(desiredSpeed < 1410) {
            if(desiredSpeed > 0) {
                io.value2.throttle = speed < desiredSpeed ? 1 : 0;
                if(speed - desiredSpeed > 100) io.value2.throttle = -1;
            }
            else {
                io.value2.throttle = speed < desiredSpeed ? 0 : -1;
                if(speed - desiredSpeed < -100) io.value2.throttle = 1;
            }
        }
        else if(speed <= desiredSpeed) {
            io.value2.throttle = 1;
        }
        else {
            io.value2.throttle = 0;
        }

        return io.value2;
    }
}
