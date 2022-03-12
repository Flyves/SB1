package util.rocket_league.controllers.ground.navigation.boostpad;

import util.data_structure.tupple.Tuple2;
import util.data_structure.tupple.Tuple3;
import util.math.vector.Vector3;
import util.rocket_league.controllers.ground.navigation.DestinationCollisionDetection;
import util.rocket_league.controllers.ground.navigation.waypoint.WaypointNavigator;
import util.rocket_league.controllers.ground.navigation.waypoint.WaypointNavigatorProfileBuilder;
import util.rocket_league.controllers.ground.throttle.SpeedController;
import util.rocket_league.controllers.jump.jump_sequence.JumpController;
import util.rocket_league.controllers.jump.jump_sequence.JumpProfileBuilder;
import util.rocket_league.controllers.jump.second.SecondJumpType;
import util.rocket_league.dynamic_objects.boost.BoostPad;
import util.rocket_league.dynamic_objects.boost.BoostPadManager;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.Behaviour;
import util.state_machine.Finishable;

import java.util.LinkedHashSet;

public class BoostPadNavigator implements Behaviour<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput>, Finishable {
    private final BoostPadNavigatorProfile boostPadNavigatorProfile;
    private final WaypointNavigator waypointNavigator;
    private final SpeedController speedController;
    private JumpController jumpController;

    public BoostPadNavigator(final BoostPadNavigatorProfile boostPadNavigatorProfile) {
        this.boostPadNavigatorProfile = boostPadNavigatorProfile;
        final LinkedHashSet<Vector3> waypoints = BoostPadPathGenerator.generatePath(
                boostPadNavigatorProfile.boostPadSource,
                boostPadNavigatorProfile.boostPadTarget);
        this.waypointNavigator = new WaypointNavigator(new WaypointNavigatorProfileBuilder()
                .withWaypoints(waypoints)
                .withAngularVelocity(boostPadNavigatorProfile.angularVelocityFunction)
                .withCollision((car, destination) -> {
                    if(DestinationCollisionDetection.DEFAULT_COLLISION_DETECTION_FUNCTION.apply(car, destination)) {
                        return true;
                    }
                    final BoostPad foundBoostPad = BoostPadManager.boostPads.stream().filter(boostPad -> boostPad.location.equals(destination)).findFirst().orElse(null);
                    assert foundBoostPad != null;
                    return !foundBoostPad.isActive;
                }).build());
        this.speedController = new SpeedController();
        this.jumpController = new JumpController(new JumpProfileBuilder().build());
    }

    @Override
    public ControlsOutput exec(final Tuple2<ExtendedCarData, ControlsOutput> io) {
        if(isFinished()) {
            return io.value2;
        }
        final double maxSpeed = boostPadNavigatorProfile.targetSpeedSupplier.get();
        try {
            // steering
            waypointNavigator.exec(new Tuple2<>(io.value1, io.value2));
            // throttle
            speedController.exec(new Tuple3<>(io.value1, io.value2, boostPadNavigatorProfile.targetSpeedSupplier.get()));
        }
        catch (final Exception ignored) {}

        // base boost value
        io.value2.isBoosting = !io.value1.isSupersonic
                && io.value1.groundSpeedForward <= maxSpeed-10
                && boostPadNavigatorProfile.minimumBoostAmount < 100;
        // jumping and flipping
        if(waypointNavigator.activeWaypoint().isPresent()) {
            final Vector3 activeWaypoint = waypointNavigator.activeWaypoint().get();
            if(activeWaypoint.distance(io.value1.position) > 2200 && jumpController.isFinished()
                    && io.value1.groundSpeedForward > 800
                    && activeWaypoint.minus(io.value1.position).angle(io.value1.velocity) < 0.03
                    && activeWaypoint.minus(io.value1.position).angle(io.value1.orientation.nose) < 0.03
                    && !io.value1.isSupersonic) {
                jumpController = generateTheRightJumpController(boostPadNavigatorProfile.secondJumpType);
            }
            // prevent boost when we shouldn't boost
            if(!jumpController.isFinished()
                    || activeWaypoint.minus(io.value1.position).angle(io.value1.velocity) > 0.4
                    || io.value1.boost <= boostPadNavigatorProfile.minimumBoostAmount) {
                io.value2.isBoosting = false;
            }
        }
        return jumpController.exec(new Tuple2<>(io.value1, io.value2));
    }

    private JumpController generateTheRightJumpController(SecondJumpType secondJumpType) {
        switch (secondJumpType) {
            case FLIP: return new JumpController(new JumpProfileBuilder()
                    .withInitialImpulse(450)
                    .withSecondJumpType(SecondJumpType.FLIP)
                    .build());
            case PARTIAL_CANCEL: return new JumpController(new JumpProfileBuilder()
                    .withInitialImpulse(400)
                    .withSecondJumpType(SecondJumpType.PARTIAL_CANCEL)
                    .build());
            case WAVE_DASH: return new JumpController(new JumpProfileBuilder()
                    .withInitialImpulse(0)
                    .withSecondJumpType(SecondJumpType.WAVE_DASH)
                    .build());
            case DOUBLE_WAVE_DASH: return new JumpController(new JumpProfileBuilder()
                    .withInitialImpulse(0)
                    .withSecondJumpType(SecondJumpType.DOUBLE_WAVE_DASH)
                    .build());
            default: return new JumpController(new JumpProfileBuilder().build());
        }
    }

    @Override
    public boolean isFinished() {
        return waypointNavigator.isFinished();
    }
}
