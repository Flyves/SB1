package util.rocket_league.controllers.ground.steer.orientation;

import util.data_structure.builder.Builder;
import util.rocket_league.controllers.ground.steer.angular_velocity.GroundSteering;

import java.util.function.Function;

public class OrientationProfileBuilder implements Builder<OrientationProfile> {
    private Function<Double, Double> angularVelocityFunction;

    public OrientationProfileBuilder() {
        this.angularVelocityFunction = GroundSteering::findMaxSpin;
    }

    /**
     *  Use this building method to set the dynamic angular velocity expected when turning on the first destination.
     * @param angularVelocityFunction a function to map the velocity of the car to the angular velocity expected when turning
     * @return the builder object
     */
    public OrientationProfileBuilder withMaxAngularVelocity(final Function<Double, Double> angularVelocityFunction) {
        this.angularVelocityFunction = angularVelocityFunction;
        return this;
    }

    @Override
    public OrientationProfile build() {
        return new OrientationProfile(angularVelocityFunction);
    }
}
