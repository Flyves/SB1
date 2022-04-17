package util.rocket_league.controllers.ground.navigation.waypoint.multiple_destination;

import util.data_structure.tupple.Tuple2;
import util.math.vector.Vector3;
import util.renderers.RenderTasks;
import util.rocket_league.controllers.ground.navigation.waypoint.Navigator;
import util.rocket_league.controllers.ground.navigation.waypoint.single_destination.DestinationNavigator;
import util.rocket_league.controllers.ground.navigation.waypoint.single_destination.DestinationNavigatorProfileBuilder;
import util.rocket_league.controllers.jump.second.SecondJumpType;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;

import java.awt.*;
import java.util.*;

public class WaypointNavigator<T> implements Navigator {
    private final LinkedHashSet<T> waypoints;
    private T activeWaypointObject;
    private final WaypointNavigatorProfile<T> waypointNavigatorProfile;
    private DestinationNavigator<T> destinationNavigator;

    public WaypointNavigator(final WaypointNavigatorProfile<T> waypointNavigatorProfile) {
        this.waypoints = new LinkedHashSet<>(waypointNavigatorProfile.waypoints);
        this.waypointNavigatorProfile = waypointNavigatorProfile;
        if(!waypoints.isEmpty()) setNextWaypoint();
    }

    @Override
    public ControlsOutput exec(final Tuple2<ExtendedCarData, ControlsOutput> io) {
        if(this.isFinished()) return io.value2;
        if (shouldGoToNextWaypoint()) setNextWaypoint();
        destinationNavigator.exec(io);
        render();
        return io.value2;
    }

    private void setNextWaypoint() {
        //noinspection OptionalGetWithoutIsPresent
        activeWaypointObject = waypoints.stream().findFirst().get();
        this.destinationNavigator = new DestinationNavigator<>(new DestinationNavigatorProfileBuilder<T>()
                .withDestinationMapper(waypointNavigatorProfile.positionObjectMapper)
                .withCollision(waypointNavigatorProfile.collisionFunction)
                .withAngularVelocity(waypointNavigatorProfile.angularVelocityFunction)
                .withTargetSpeed(waypointNavigatorProfile.targetSpeedSupplier)
                .withDestination(activeWaypointObject)
                .withFlipType(waypointNavigatorProfile.secondJumpType)
                .withMinimumBoostAmount(waypointNavigatorProfile.minimumBoostAmountSupplier)
                .build());
        waypoints.remove(activeWaypointObject);
    }

    private boolean shouldGoToNextWaypoint() {
        return destinationNavigator == null || destinationNavigator.isFinished();
    }

    public Optional<Vector3> activeWaypointPosition() {
        if(isFinished()) return Optional.empty();
        if(activeWaypointObject != null) {
            return Optional.of(waypointNavigatorProfile.positionObjectMapper.apply(activeWaypointObject));
        }
        return Optional.empty();
    }

    @Override
    public boolean isFinished() {
        return waypoints.isEmpty() && (destinationNavigator == null || destinationNavigator.isFinished());
    }

    public void forceSecondJump(final SecondJumpType secondJumpType, final ExtendedCarData car) {
        destinationNavigator.forceSecondJump(secondJumpType, car);
    }

    public boolean isFlipping() {
        return destinationNavigator.isFlipping();
    }

    private void render() {
        activeWaypointPosition().ifPresent(activeWaypoint -> {
            waypoints.forEach(waypoint -> RenderTasks.append(r ->
                    r.drawLine3d(
                            Color.cyan,
                            waypointNavigatorProfile.positionObjectMapper.apply(waypoint).toFlatVector(),
                            waypointNavigatorProfile.positionObjectMapper.apply(waypoint).plus(new Vector3(0, 0, 300)).toFlatVector())));
        });
    }
}
