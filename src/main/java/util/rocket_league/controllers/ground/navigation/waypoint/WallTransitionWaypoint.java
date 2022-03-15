package util.rocket_league.controllers.ground.navigation.waypoint;

import util.math.vector.Ray3;
import util.math.vector.Vector3;

public class WallTransitionWaypoint extends PlayfieldWaypoint {
    public WallTransitionWaypoint(final Vector3 waypoint) {
        super(new Ray3(waypoint, new Vector3()), WaypointCollisions.DEFAULT_WAYPOINT_COLLISION_FUNCTION);
    }
}
