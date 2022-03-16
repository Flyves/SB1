package util.rocket_league.controllers.ground.navigation.waypoints;

import util.math.vector.Ray3;
import util.math.vector.Vector3;

/** A waypoint that represents a position that lies on one of the triangles composing the surface of the playfield.
 *
 */
public class PlayfieldSurfaceWaypoint extends Waypoint {
    public final Vector3 normal;

    public PlayfieldSurfaceWaypoint(final Ray3 waypoint) {
        super(waypoint.offset, WaypointCollisions.DEFAULT_WAYPOINT_COLLISION_FUNCTION);
        this.normal = waypoint.direction;
    }
}
