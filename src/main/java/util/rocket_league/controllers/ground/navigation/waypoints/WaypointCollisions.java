package util.rocket_league.controllers.ground.navigation.waypoints;

import util.math.vector.Vector3;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;

import java.util.function.BiFunction;

public class WaypointCollisions {
    private static final double DEFAULT_WAYPOINT_COLLISION_DISTANCE_SQUARED = 100*100;

    public static final BiFunction<ExtendedCarData, Vector3, Boolean> DEFAULT_WAYPOINT_COLLISION_FUNCTION =
            ((car, waypointPosition) ->
                    car.position.distanceSquared(waypointPosition) < DEFAULT_WAYPOINT_COLLISION_DISTANCE_SQUARED);
}
