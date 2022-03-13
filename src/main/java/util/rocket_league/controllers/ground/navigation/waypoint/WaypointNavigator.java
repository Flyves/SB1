package util.rocket_league.controllers.ground.navigation.waypoint;

import util.data_structure.tupple.Tuple2;
import util.data_structure.tupple.Tuple3;
import util.math.vector.Vector3;
import util.renderers.RenderTasks;
import util.rocket_league.controllers.ground.navigation.destination.DestinationNavigator;
import util.rocket_league.controllers.ground.navigation.destination.DestinationProfileBuilder;
import util.rocket_league.controllers.ground.throttle.SpeedController;
import util.rocket_league.controllers.jump.jump_sequence.JumpController;
import util.rocket_league.controllers.jump.jump_sequence.JumpProfileBuilder;
import util.rocket_league.controllers.jump.second.SecondJumpType;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.Behaviour;
import util.state_machine.Finishable;

import java.awt.*;
import java.util.*;

public class WaypointNavigator<T> implements Behaviour<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput>, Finishable {
    private final LinkedHashSet<T> waypoints;
    private final WaypointNavigatorProfile<T> waypointNavigatorProfile;
    private DestinationNavigator<T> destinationNavigator;
    private T activeWaypointObject;
    private final SpeedController speedController;
    private JumpController jumpController;

    public WaypointNavigator(final WaypointNavigatorProfile<T> waypointNavigatorProfile) {
        this.waypoints = new LinkedHashSet<>(waypointNavigatorProfile.waypoints);
        this.waypointNavigatorProfile = waypointNavigatorProfile;
        if(!waypoints.isEmpty()) setNextWaypoint();
        this.speedController = new SpeedController();
        this.jumpController = new JumpController(new JumpProfileBuilder().build());
    }

    @Override
    public ControlsOutput exec(final Tuple2<ExtendedCarData, ControlsOutput> io) {
        if(this.isFinished()) return io.value2;
        if (shouldGoToNextWaypoint()) setNextWaypoint();

        @SuppressWarnings("OptionalGetWithoutIsPresent")
        final Vector3 activeWaypoint = activeWaypoint().get();
        driveOnGround(io);
        handleJump(io, activeWaypoint);
        handleBoost(io, getTargetDrivingSpeed(), activeWaypoint);
        render();
        return io.value2;
    }

    private void render() {
        activeWaypoint().ifPresent(activeWaypoint -> {
            RenderTasks.append(r -> r.drawLine3d(
                    Color.red, activeWaypoint.toFlatVector(), activeWaypoint.plus(new Vector3(0, 0, 300)).toFlatVector()));
            waypoints.forEach(waypoint -> RenderTasks.append(r ->
                    r.drawLine3d(
                            Color.cyan,
                            waypointNavigatorProfile.positionObjectMapper.apply(waypoint).toFlatVector(),
                            waypointNavigatorProfile.positionObjectMapper.apply(waypoint).plus(new Vector3(0, 0, 300)).toFlatVector())));
        });
    }

    private void setNextWaypoint() {
        //noinspection OptionalGetWithoutIsPresent
        activeWaypointObject = waypoints.stream().findFirst().get();
        this.destinationNavigator = new DestinationNavigator<>(new DestinationProfileBuilder<T>()
                .withDestinationMapper(waypointNavigatorProfile.positionObjectMapper)
                .withCollision(waypointNavigatorProfile.collisionFunction)
                .withAngularVelocity(waypointNavigatorProfile.angularVelocityFunction)
                .withDestination(activeWaypointObject)
                .build());
        waypoints.remove(activeWaypointObject);
    }

    private boolean shouldGoToNextWaypoint() {
        return destinationNavigator == null || destinationNavigator.isFinished();
    }

    private void driveOnGround(final Tuple2<ExtendedCarData, ControlsOutput> io) {
        try {
            destinationNavigator.exec(io);
            speedController.exec(new Tuple3<>(io.value1, io.value2, waypointNavigatorProfile.targetSpeedSupplier.get()));
        }
        catch (final Exception ignored) {}
    }

    private void handleJump(final Tuple2<ExtendedCarData, ControlsOutput> io, final Vector3 activeWaypoint) {
        if(shouldJump(io, activeWaypoint)) {
            jumpController = getNewJumpController(io, activeWaypoint);
        }
        jumpController.exec(new Tuple2<>(io.value1, io.value2));
    }

    private void handleBoost(final Tuple2<ExtendedCarData, ControlsOutput> io, final double maxSpeed, final Vector3 activeWaypoint) {
        io.value2.isBoosting = shouldTryToBoost(io, maxSpeed);
        if(shouldPreventBoost(io, activeWaypoint)) {
            io.value2.isBoosting = false;
        }
    }

    private Double getTargetDrivingSpeed() {
        return waypointNavigatorProfile.targetSpeedSupplier.get();
    }

    private boolean shouldTryToBoost(final Tuple2<ExtendedCarData, ControlsOutput> io, final double maxSpeed) {
        return BoostHandler.shouldTryToBoost(io, maxSpeed, waypointNavigatorProfile.minimumBoostAmount);
    }

    private boolean shouldPreventBoost(final Tuple2<ExtendedCarData, ControlsOutput> io, final Vector3 activeWaypoint) {
        return BoostHandler.shouldPreventBoostToKeepTheFlow(
                io,
                activeWaypoint,
                jumpController.isFinished(),
                waypointNavigatorProfile.minimumBoostAmount);
    }

    private boolean shouldJump(final Tuple2<ExtendedCarData, ControlsOutput> io, final Vector3 activeWaypoint) {
        return JumpCondition.shouldJump(
                io,
                activeWaypoint,
                jumpController.isFinished());
    }

    private JumpController getNewJumpController(final Tuple2<ExtendedCarData, ControlsOutput> io, final Vector3 activeWaypoint) {
        return JumpControllerGenerator.generateTheRightJumpController(
                waypointNavigatorProfile.secondJumpType,
                io.value1,
                activeWaypoint);
    }

    public void forceSecondJump(final SecondJumpType secondJumpType, final ExtendedCarData car) {
        jumpController = JumpControllerGenerator.generateTheRightJumpController(
                secondJumpType,
                car,
                waypointNavigatorProfile.positionObjectMapper.apply(activeWaypointObject));
    }

    public boolean isFlipping() {
        return jumpController != null && !jumpController.isFinished();
    }

    @Override
    public boolean isFinished() {
        return waypoints.isEmpty() && (destinationNavigator == null || destinationNavigator.isFinished());
    }

    public LinkedHashSet<T> remainingWaypoints() {
        return waypoints;
    }

    public Optional<Vector3> activeWaypoint() {
        if(isFinished()) return Optional.empty();
        if(activeWaypointObject != null) {
            return Optional.of(waypointNavigatorProfile.positionObjectMapper.apply(activeWaypointObject));
        }
        return Optional.empty();
    }
}
