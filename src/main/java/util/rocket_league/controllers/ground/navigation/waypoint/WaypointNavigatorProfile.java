package util.rocket_league.controllers.ground.navigation.waypoint;

import util.math.vector.Vector3;
import util.rocket_league.controllers.jump.second.SecondJumpType;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;

import java.util.LinkedHashSet;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class WaypointNavigatorProfile {
    public final BiFunction<ExtendedCarData, Vector3, Boolean> collisionFunction;
    public final Function<Double, Double> angularVelocityFunction;
    public final LinkedHashSet<Vector3> waypoints;
    public final SecondJumpType secondJumpType;
    public final Supplier<Double> targetSpeedSupplier;
    public final double minimumBoostAmount;

    WaypointNavigatorProfile(
            final Function<Double, Double> angularVelocityFunction,
            final BiFunction<ExtendedCarData, Vector3, Boolean> collisionFunction,
            final LinkedHashSet<Vector3> waypoints,
            final SecondJumpType secondJumpType,
            final Supplier<Double> targetSpeedSupplier,
            final double minimumBoostAmount) {
        this.collisionFunction = collisionFunction;
        this.angularVelocityFunction = angularVelocityFunction;
        this.waypoints = waypoints;
        this.secondJumpType = secondJumpType;
        this.targetSpeedSupplier = targetSpeedSupplier;
        this.minimumBoostAmount = minimumBoostAmount;
    }
}
