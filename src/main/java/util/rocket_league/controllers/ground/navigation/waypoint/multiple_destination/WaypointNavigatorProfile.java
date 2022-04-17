package util.rocket_league.controllers.ground.navigation.waypoint.multiple_destination;

import util.math.vector.Vector3;
import util.rocket_league.controllers.jump.second.SecondJumpType;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;

import java.util.LinkedHashSet;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class WaypointNavigatorProfile<T> {
    public final BiFunction<ExtendedCarData, T, Boolean> collisionFunction;
    public final Function<Double, Double> angularVelocityFunction;
    public final LinkedHashSet<T> waypoints;
    public final Function<T, Vector3> positionObjectMapper;
    public final SecondJumpType secondJumpType;
    public final Supplier<Double> targetSpeedSupplier;
    public final Supplier<Double> minimumBoostAmountSupplier;

    WaypointNavigatorProfile(
            final Function<Double, Double> angularVelocityFunction,
            final BiFunction<ExtendedCarData, T, Boolean> collisionFunction,
            final LinkedHashSet<T> waypoints,
            final Function<T, Vector3> positionObjectMapper,
            final SecondJumpType secondJumpType,
            final Supplier<Double> targetSpeedSupplier,
            final Supplier<Double> minimumBoostAmountSupplier) {
        this.collisionFunction = collisionFunction;
        this.angularVelocityFunction = angularVelocityFunction;
        this.waypoints = waypoints;
        this.positionObjectMapper = positionObjectMapper;
        this.secondJumpType = secondJumpType;
        this.targetSpeedSupplier = targetSpeedSupplier;
        this.minimumBoostAmountSupplier = minimumBoostAmountSupplier;
    }
}
