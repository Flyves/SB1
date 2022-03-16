package util.rocket_league.controllers.ground.dribble.reverse_hit.states;

import util.data_structure.tupple.Tuple3;
import util.rocket_league.dynamic_objects.ball.BallData;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.State;

public class HitTheBall extends State<Tuple3<ExtendedCarData, BallData, ControlsOutput>, ControlsOutput> {
    @Override
    public ControlsOutput exec(Tuple3<ExtendedCarData, BallData, ControlsOutput> input) {
        return null;
    }

    @Override
    public State<Tuple3<ExtendedCarData, BallData, ControlsOutput>, ControlsOutput> next(Tuple3<ExtendedCarData, BallData, ControlsOutput> input) {
        return null;
    }
}
