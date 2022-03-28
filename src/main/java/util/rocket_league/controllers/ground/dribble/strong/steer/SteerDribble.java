package util.rocket_league.controllers.ground.dribble.strong.steer;

import util.data_structure.tupple.Tuple4;
import util.rocket_league.dynamic_objects.ball.BallData;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.Behaviour;

public class SteerDribble implements Behaviour<Tuple4<ExtendedCarData, BallData, Double, ControlsOutput>, ControlsOutput> {


    @Override
    public ControlsOutput exec(Tuple4<ExtendedCarData, BallData, Double, ControlsOutput> io) {
        final double targetAngularVelocity = io.value3;
        return io.value4;
    }
}
