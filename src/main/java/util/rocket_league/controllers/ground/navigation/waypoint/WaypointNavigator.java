package util.rocket_league.controllers.ground.navigation.waypoint;

import util.data_structure.tupple.Tuple2;
import util.data_structure.tupple.Tuple3;
import util.math.vector.Vector2;
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

public class WaypointNavigator implements Behaviour<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput>, Finishable {
    private final LinkedHashSet<Vector3> waypoints;
    private final WaypointNavigatorProfile waypointNavigatorProfile;
    private DestinationNavigator destinationNavigator;
    private Vector3 activeWaypoint;
    private final SpeedController speedController;
    private JumpController jumpController;

    public WaypointNavigator(final WaypointNavigatorProfile waypointNavigatorProfile) {
        this.waypoints = new LinkedHashSet<>(waypointNavigatorProfile.waypoints);
        this.waypointNavigatorProfile = waypointNavigatorProfile;
        if(!waypoints.isEmpty()) setNextWaypoint();
        this.speedController = new SpeedController();
        this.jumpController = new JumpController(new JumpProfileBuilder().build());
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
        final double maxSpeed = waypointNavigatorProfile.targetSpeedSupplier.get();
        try {
            if (destinationNavigator == null || destinationNavigator.isFinished()) {
                setNextWaypoint();
            }
            RenderTasks.append(r -> r.drawLine3d(Color.red, activeWaypoint.toFlatVector(), activeWaypoint.plus(new Vector3(0, 0, 300)).toFlatVector()));
            waypoints.forEach(waypoint -> {
                RenderTasks.append(r -> r.drawLine3d(Color.cyan, waypoint.toFlatVector(), waypoint.plus(new Vector3(0, 0, 300)).toFlatVector()));
            });
            // steer
            destinationNavigator.exec(io);
            // throttle
            speedController.exec(new Tuple3<>(io.value1, io.value2, waypointNavigatorProfile.targetSpeedSupplier.get()));
        }
        catch (final Exception ignored) {}
        // base boost value
        io.value2.isBoosting = !io.value1.isSupersonic
                && io.value1.groundSpeedForward <= maxSpeed-10
                && waypointNavigatorProfile.minimumBoostAmount < 100;
        // jumping and flipping
        if(this.activeWaypoint().isPresent()) {
            final Vector3 activeWaypoint = this.activeWaypoint().get();
            if(activeWaypoint.distance(io.value1.position) > 2200 && jumpController.isFinished()
                    && io.value1.groundSpeedForward > 800
                    && activeWaypoint.minus(io.value1.position).angle(io.value1.velocity) < 0.02
                    && activeWaypoint.minus(io.value1.position).angle(io.value1.orientation.nose) < 0.02
                    && !io.value1.isSupersonic) {
                jumpController = generateTheRightJumpController(waypointNavigatorProfile.secondJumpType, io.value1);
            }
            // prevent boost when we shouldn't boost
            if(!jumpController.isFinished()
                    || activeWaypoint.minus(io.value1.position).angle(io.value1.velocity) > 0.4
                    || io.value1.boost <= waypointNavigatorProfile.minimumBoostAmount) {
                io.value2.isBoosting = false;
            }
        }
        return jumpController.exec(new Tuple2<>(io.value1, io.value2));
    }

    public void forceSecondJump(final SecondJumpType secondJumpType, final ExtendedCarData car) {
        jumpController = generateTheRightJumpController(secondJumpType, car);
    }

    public boolean isFlipping() {
        return jumpController == null || jumpController.isFinished();
    }

    private JumpController generateTheRightJumpController(final SecondJumpType secondJumpType, final ExtendedCarData car) {
        final Vector2 flipDirection = activeWaypoint.minus(car.position).rotate(car.orientation.asAngularDisplacement().scaled(-1)).flatten().normalized().flip();
        switch (secondJumpType) {
            case FLIP: return new JumpController(new JumpProfileBuilder()
                    .withInitialImpulse(450)
                    .withSecondJumpType(SecondJumpType.FLIP)
                    .withFlipDirection(flipDirection)
                    .build());
            case PARTIAL_CANCEL: return new JumpController(new JumpProfileBuilder()
                    .withInitialImpulse(400)
                    .withSecondJumpType(SecondJumpType.PARTIAL_CANCEL)
                    .withFlipDirection(flipDirection)
                    .build());
            case WAVE_DASH: return new JumpController(new JumpProfileBuilder()
                    .withInitialImpulse(0)
                    .withSecondJumpType(SecondJumpType.WAVE_DASH)
                    .withFlipDirection(flipDirection)
                    .build());
            case DOUBLE_WAVE_DASH: return new JumpController(new JumpProfileBuilder()
                    .withInitialImpulse(0)
                    .withSecondJumpType(SecondJumpType.DOUBLE_WAVE_DASH)
                    .withFlipDirection(flipDirection)
                    .build());
            default: return new JumpController(new JumpProfileBuilder().build());
        }
    }

    @Override
    public boolean isFinished() {
        return waypoints.isEmpty() && (destinationNavigator == null || destinationNavigator.isFinished());
    }

    public LinkedHashSet<Vector3> remainingWaypoints() {
        return waypoints;
    }

    public Optional<Vector3> activeWaypoint() {
        if(isFinished()) return Optional.empty();
        return Optional.of(activeWaypoint);
    }
}
