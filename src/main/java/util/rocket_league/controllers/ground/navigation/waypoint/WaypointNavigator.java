package util.rocket_league.controllers.ground.navigation.waypoint;

import util.data_structure.tupple.Tuple2;
import util.math.vector.Vector3;
import util.rocket_league.controllers.ground.navigation.bi_destination.BiDestinationNavigator;
import util.rocket_league.controllers.ground.navigation.bi_destination.BiDestinationProfileBuilder;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.Behaviour;

import java.util.List;

public class WaypointNavigator implements Behaviour<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput> {
    private final List<Vector3> waypoints;
    //private final BiDestinationNavigator biDestinationNavigator;

    public WaypointNavigator(final List<Vector3> waypoints) {
        if(waypoints.isEmpty()) throw new WaypointNavigatorWithNoWaypointsException();
        this.waypoints = waypoints;
        /*
        if(waypoints.size() == 1) {
            this.biDestinationNavigator = new BiDestinationNavigator(new BiDestinationProfileBuilder()
                    .withFirstDestination(waypoints.get(0))
                    .withSecondDestination(waypoints.get(0))
                    .build());
        }
        else {
            this.biDestinationNavigator = new BiDestinationNavigator(new BiDestinationProfileBuilder()
                    .withFirstDestination());
        }*/
    }

    @Override
    public ControlsOutput exec(Tuple2<ExtendedCarData, ControlsOutput> input) {
        return null;
    }
}
