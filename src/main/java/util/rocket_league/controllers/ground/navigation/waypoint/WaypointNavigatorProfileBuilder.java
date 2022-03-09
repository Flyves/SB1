package util.rocket_league.controllers.ground.navigation.waypoint;

import util.data_structure.builder.Builder;
import util.math.vector.Vector3;

import java.util.List;
import java.util.function.Function;

public class WaypointNavigatorProfileBuilder implements Builder<WaypointNavigatorProfile> {
    private Function<Double, Double> angularVelocityFunction;
    private List<Vector3> waypoints;

    /**
     *  Use this building method to set the dynamic angular velocity expected when turning on the first destination.
     * @param angularVelocityFunction a function to map the velocity of the car to the angular velocity expected when turning
     * @return the builder object
     */
    public WaypointNavigatorProfileBuilder withAngularVelocity(final Function<Double, Double> angularVelocityFunction) {
        this.angularVelocityFunction = angularVelocityFunction;
        return this;
    }

    public WaypointNavigatorProfileBuilder withWaypoints(final List<Vector3> waypoints) {
        this.waypoints = waypoints;
        return this;
    }

    @Override
    public WaypointNavigatorProfile build() {
        return new WaypointNavigatorProfile(angularVelocityFunction, waypoints);
    }
}
