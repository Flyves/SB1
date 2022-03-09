package util.rocket_league.controllers.ground.navigation.destination;

import util.math.vector.Vector3;
import util.rocket_league.controllers.ground.steer.angular_velocity.GroundSteering;

import java.util.function.Function;

public class DestinationProfileBuilder {
    private Vector3 firstDestination;
    private Function<Double, Double> angularVelocityFunction;

    public DestinationProfileBuilder() {
        firstDestination = new Vector3();
        angularVelocityFunction = GroundSteering::findMaxSpin;
    }

    public DestinationProfileBuilder withDestination(final Vector3 firstDestination) {
        this.firstDestination = firstDestination;
        return this;
    }

    /**
     *  Use this building method to set the dynamic angular velocity expected when turning on the first destination.
     * @param angularVelocityFunction a function to map the velocity of the car to the angular velocity expected when turning
     * @return the builder object
     */
    public DestinationProfileBuilder withAngularVelocity(final Function<Double, Double> angularVelocityFunction) {
        this.angularVelocityFunction = angularVelocityFunction;
        return this;
    }

    public DestinationProfile build() {
        return new DestinationProfile(firstDestination, angularVelocityFunction);
    }
}
