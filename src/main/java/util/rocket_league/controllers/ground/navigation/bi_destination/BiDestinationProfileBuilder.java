package util.rocket_league.controllers.ground.navigation.bi_destination;

import util.math.vector.Vector3;
import util.rocket_league.controllers.ground.steer.angular_velocity.GroundSteering;

import java.util.function.Function;

public class BiDestinationProfileBuilder {
    private Vector3 firstDestination;
    private Vector3 secondDestination;
    private Function<Double, Double> angularVelocityFunction;

    public BiDestinationProfileBuilder() {
        firstDestination = new Vector3();
        secondDestination = new Vector3();
        angularVelocityFunction = GroundSteering::findMaxSpin;
    }

    public BiDestinationProfileBuilder withFirstDestination(final Vector3 firstDestination) {
        this.firstDestination = firstDestination;
        return this;
    }

    public BiDestinationProfileBuilder withSecondDestination(final Vector3 secondDestination) {
        this.secondDestination = secondDestination;
        return this;
    }

    /**
     *  Use this building method to set the dynamic angular velocity expected when turning on the first destination.
     * @param angularVelocityFunction a function to map the velocity of the car to the angular velocity expected when turning
     * @return the builder object
     */
    public BiDestinationProfileBuilder withAngularVelocity(final Function<Double, Double> angularVelocityFunction) {
        this.angularVelocityFunction = angularVelocityFunction;
        return this;
    }

    public BiDestinationProfile build() {
        return new BiDestinationProfile(firstDestination, secondDestination, angularVelocityFunction);
    }
}
