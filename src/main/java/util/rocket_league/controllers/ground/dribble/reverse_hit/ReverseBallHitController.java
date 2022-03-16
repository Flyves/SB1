package util.rocket_league.controllers.ground.dribble.reverse_hit;

import util.data_structure.tupple.Tuple3;
import util.rocket_league.controllers.ground.dribble.reverse_hit.states.GoToOppositeSideOfBallDirection;
import util.rocket_league.dynamic_objects.ball.BallData;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.StateMachine;

public class ReverseBallHitController extends StateMachine<Tuple3<ExtendedCarData, BallData, ControlsOutput>, ControlsOutput> {
    public ReverseBallHitController() {
        super(new GoToOppositeSideOfBallDirection());
    }
}
