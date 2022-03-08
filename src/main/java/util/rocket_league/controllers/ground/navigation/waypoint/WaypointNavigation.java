package util.rocket_league.controllers.ground.navigation.waypoint;

import util.data_structure.tupple.Tuple2;
import util.math.vector.Vector3;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.Behaviour;

import java.util.List;

public class WaypointNavigation implements Behaviour<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput> {
    private final List<Vector3> waypoints;

    public WaypointNavigation(final List<Vector3> waypoints) {
        this.waypoints = waypoints;
    }

    @Override
    public ControlsOutput exec(Tuple2<ExtendedCarData, ControlsOutput> input) {
        return null;
    }
}
