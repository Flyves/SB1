package util.rocket_league.controllers.ground.navigation.waypoint;

import util.data_structure.tupple.Tuple2;
import util.math.vector.Vector3;
import util.renderers.RenderTasks;
import util.rocket_league.controllers.ground.navigation.destination.DestinationNavigator;
import util.rocket_league.controllers.ground.navigation.destination.DestinationProfileBuilder;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.Behaviour;
import util.state_machine.Finishable;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class WaypointNavigator implements Behaviour<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput>, Finishable {
    private final List<Vector3> waypoints;
    private final WaypointNavigatorProfile waypointNavigatorProfile;
    private DestinationNavigator destinationNavigator;
    private Vector3 activeWaypoint;

    public WaypointNavigator(final WaypointNavigatorProfile waypointNavigatorProfile) {
        this.waypoints = new LinkedList<>(waypointNavigatorProfile.waypoints);
        this.waypointNavigatorProfile = waypointNavigatorProfile;
        if(!waypoints.isEmpty()) setNextWaypoint(waypointNavigatorProfile);
    }

    private void setNextWaypoint(final WaypointNavigatorProfile waypointNavigatorProfile) {
        activeWaypoint = waypoints.get(0);
        this.destinationNavigator = new DestinationNavigator(new DestinationProfileBuilder()
                .withAngularVelocity(waypointNavigatorProfile.angularVelocityFunction)
                .withDestination(activeWaypoint)
                .build());
        waypoints.remove(activeWaypoint);
    }

    @Override
    public ControlsOutput exec(Tuple2<ExtendedCarData, ControlsOutput> io) {
        if(this.isFinished()) return io.value2;
        if(destinationNavigator == null || destinationNavigator.isFinished()) {
            setNextWaypoint(waypointNavigatorProfile);
        }
        RenderTasks.append(r -> r.drawLine3d(Color.red, activeWaypoint.toFlatVector(), activeWaypoint.plus(new Vector3(0, 0, 300)).toFlatVector()));
        waypoints.forEach(waypoint -> {
            RenderTasks.append(r -> r.drawLine3d(Color.cyan, waypoint.toFlatVector(), waypoint.plus(new Vector3(0, 0, 300)).toFlatVector()));
        });
        return destinationNavigator.exec(io);
    }

    @Override
    public boolean isFinished() {
        return waypoints.isEmpty() && destinationNavigator.isFinished();
    }

    public List<Vector3> remainingWaypoints() {
        return waypoints;
    }

    public Optional<Vector3> activeWaypoint() {
        if(isFinished()) return Optional.empty();
        return Optional.of(activeWaypoint);
    }
}
