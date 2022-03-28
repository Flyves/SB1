package util.rocket_league.controllers.ground.dribble.strong;

import util.data_structure.tupple.Tuple3;
import util.rocket_league.controllers.ground.navigation.navigators.multiple_destination.WaypointNavigator;
import util.rocket_league.controllers.ground.navigation.waypoints.Waypoint;
import util.rocket_league.dynamic_objects.ball.BallPrediction;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.Behaviour;

public class StrongDribble implements Behaviour<Tuple3<ExtendedCarData, BallPrediction, ControlsOutput>, ControlsOutput> {
    private final StrongDribbleProfile strongDribbleProfile;
    //private final WaypointNavigator<Waypoint> waypointNavigator;

    public StrongDribble(final StrongDribbleProfile strongDribbleProfile) {
        this.strongDribbleProfile = strongDribbleProfile;
    }

    @Override
    public ControlsOutput exec(Tuple3<ExtendedCarData, BallPrediction, ControlsOutput> input) {
        return null;
    }
}
