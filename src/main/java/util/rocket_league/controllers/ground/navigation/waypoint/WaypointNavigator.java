package util.rocket_league.controllers.ground.navigation.waypoint;

import util.data_structure.tupple.Tuple2;
import util.math.vector.Vector3;
import util.renderers.RenderTasks;
import util.rocket_league.controllers.ground.navigation.destination.DestinationNavigator;
import util.rocket_league.controllers.ground.navigation.destination.DestinationProfileBuilder;
import util.rocket_league.dynamic_objects.boost.BoostPad;
import util.rocket_league.dynamic_objects.boost.BoostPadManager;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.Behaviour;
import util.state_machine.Finishable;

import java.awt.*;
import java.util.*;
import java.util.List;

public class WaypointNavigator implements Behaviour<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput>, Finishable {
    private final LinkedHashSet<Vector3> waypoints;
    private final WaypointNavigatorProfile waypointNavigatorProfile;
    private DestinationNavigator destinationNavigator;
    private Vector3 activeWaypoint;

    public WaypointNavigator(final WaypointNavigatorProfile waypointNavigatorProfile) {
        this.waypoints = new LinkedHashSet<>(waypointNavigatorProfile.waypoints);
        this.waypointNavigatorProfile = waypointNavigatorProfile;
        if(!waypoints.isEmpty()) setNextWaypoint();
    }

    private void setNextWaypoint() {
        activeWaypoint = waypoints.stream().findFirst().get();
        this.destinationNavigator = new DestinationNavigator(new DestinationProfileBuilder()
                .withCollision(waypointNavigatorProfile.collisionFunction)
                .withAngularVelocity(waypointNavigatorProfile.angularVelocityFunction)
                .withDestination(activeWaypoint)
                .build());
        waypoints.remove(activeWaypoint);
    }

    @Override
    public ControlsOutput exec(Tuple2<ExtendedCarData, ControlsOutput> io) {
        if(this.isFinished()) return io.value2;
        if(destinationNavigator == null || destinationNavigator.isFinished()) {
            setNextWaypoint();
        }
        RenderTasks.append(r -> r.drawLine3d(Color.red, activeWaypoint.toFlatVector(), activeWaypoint.plus(new Vector3(0, 0, 300)).toFlatVector()));
        waypoints.forEach(waypoint -> {
            RenderTasks.append(r -> r.drawLine3d(Color.cyan, waypoint.toFlatVector(), waypoint.plus(new Vector3(0, 0, 300)).toFlatVector()));
        });
        return destinationNavigator.exec(io);
    }

    @Override
    public boolean isFinished() {
        return waypoints.isEmpty() && (destinationNavigator == null || destinationNavigator.isFinished());
    }

    public HashSet<Vector3> remainingWaypoints() {
        return waypoints;
    }

    public Optional<Vector3> activeWaypoint() {
        if(isFinished()) return Optional.empty();
        return Optional.of(activeWaypoint);
    }
}
