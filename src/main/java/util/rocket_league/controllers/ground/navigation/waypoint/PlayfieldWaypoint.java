package util.rocket_league.controllers.ground.navigation.waypoint;

import util.math.vector.Ray3;
import util.math.vector.Vector3;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;

import java.util.function.BiFunction;

public abstract class PlayfieldWaypoint extends Waypoint {
    public final Vector3 normal;

    public PlayfieldWaypoint(final Ray3 waypoint, final BiFunction<ExtendedCarData, Vector3, Boolean> collisionFunction) {
        super(waypoint.offset, collisionFunction);
        normal = waypoint.direction;
    }
}
