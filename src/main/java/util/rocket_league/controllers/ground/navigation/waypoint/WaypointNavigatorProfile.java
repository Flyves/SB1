package util.rocket_league.controllers.ground.navigation.waypoint;

import util.math.vector.Vector3;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;

import java.util.LinkedHashSet;
import java.util.function.BiFunction;
import java.util.function.Function;
public class WaypointNavigatorProfile {
    public final BiFunction<ExtendedCarData, Vector3, Boolean> collisionFunction;
    public final Function<Double, Double> angularVelocityFunction;
    public final LinkedHashSet<Vector3> waypoints;

    WaypointNavigatorProfile(final Function<Double, Double> angularVelocityFunction, final BiFunction<ExtendedCarData, Vector3, Boolean> collisionFunction, final LinkedHashSet<Vector3> waypoints) {
        this.collisionFunction = collisionFunction;
        this.angularVelocityFunction = angularVelocityFunction;
        this.waypoints = waypoints;
    }
}
