package util.rocket_league.controllers.ground.navigation.waypoint;

import util.math.vector.Ray3;
import util.math.vector.Vector3;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;

import java.util.function.BiFunction;

public class PlayfieldSurfaceWaypoint extends PlayfieldWaypoint {
    public PlayfieldSurfaceWaypoint(final Ray3 waypoint) {
        super(waypoint, WaypointCollisions.DEFAULT_WAYPOINT_COLLISION_FUNCTION);
    }
}
