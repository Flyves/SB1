package bot.flyve.states.kickoff.states;

import util.data_structure.tupple.Tuple2;
import util.data_structure.tupple.Tuple3;
import util.math.vector.Vector3;
import util.rocket_league.controllers.ground.navigation.waypoint.WaypointNavigator;
import util.rocket_league.controllers.ground.navigation.waypoint.WaypointNavigatorProfileBuilder;
import util.rocket_league.controllers.ground.throttle.SpeedController;
import util.rocket_league.io.input.DataPacket;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.State;

import java.util.ArrayList;
import java.util.List;

public class BaseState extends State<DataPacket, ControlsOutput> {
    private final WaypointNavigator waypointNavigator;
    private final SpeedController speedController;

    public BaseState() {
        final List<Vector3> waypoints = new ArrayList<>();
        waypoints.add(new Vector3());
        waypointNavigator = new WaypointNavigator(new WaypointNavigatorProfileBuilder()
                .withAngularVelocity(v -> 3.0)
                .build());
        speedController = new SpeedController();
    }

    @Override
    public void start(final DataPacket input) {
        waypointNavigator.remainingWaypoints().add(input.ball.position);
    }

    @Override
    public ControlsOutput exec(DataPacket input) {
        final ControlsOutput output = waypointNavigator.exec(new Tuple2<>(input.car, new ControlsOutput()));
        return speedController.exec(new Tuple3<>(input.car, output, 2300.0));
    }

    @Override
    public State<DataPacket, ControlsOutput> next(DataPacket input) {
        return this;
    }
}
