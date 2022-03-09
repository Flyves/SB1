package util.rocket_league.controllers.ground.navigation.waypoint;

import util.math.vector.Vector3;

import java.util.List;
import java.util.function.Function;
public class WaypointNavigatorProfile {
    public final Function<Double, Double> angularVelocityFunction;
    public final List<Vector3> waypoints;

    public WaypointNavigatorProfile(final Function<Double, Double> angularVelocityFunction, final List<Vector3> waypoints) {
        this.angularVelocityFunction = angularVelocityFunction;
        this.waypoints = waypoints;
    }
}
