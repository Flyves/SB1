package util.rocket_league.controllers.ground.navigation.waypoint.waypoints;

import util.math.vector.Vector3;

/** A default implementation of a waypoint that represents a position somewhere on the playfield.
 *
 */
public class DefaultPlayfieldWaypoint extends Waypoint {

    public DefaultPlayfieldWaypoint(final Vector3 waypoint) {
        super(waypoint, WaypointCollisions.DEFAULT_WAYPOINT_COLLISION_FUNCTION);
    }
}
