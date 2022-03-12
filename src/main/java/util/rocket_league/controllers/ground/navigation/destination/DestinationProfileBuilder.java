package util.rocket_league.controllers.ground.navigation.destination;

import util.math.vector.Vector3;
import util.rocket_league.controllers.ground.navigation.DestinationCollisionDetection;
import util.rocket_league.controllers.ground.steer.angular_velocity.GroundSteering;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;

import java.util.function.BiFunction;
import java.util.function.Function;

public class DestinationProfileBuilder {
    private Vector3 firstDestination;
    private BiFunction<ExtendedCarData, Vector3, Boolean> collisionFunction;
    private Function<Double, Double> angularVelocityFunction;

    public DestinationProfileBuilder() {
        firstDestination = new Vector3();
        collisionFunction = DestinationCollisionDetection.DEFAULT_COLLISION_DETECTION_FUNCTION;
        angularVelocityFunction = GroundSteering::findMaxAngularVelocity;
    }

    public DestinationProfileBuilder withDestination(final Vector3 firstDestination) {
        this.firstDestination = firstDestination;
        return this;
    }

    /**
     *  Use this building method to set the collision condition indicating that we reached the destination.
     * @param collisionFunction a function that indicates whether we reached the destination or not
     * @return the builder object
     */
    public DestinationProfileBuilder withCollision(final BiFunction<ExtendedCarData, Vector3, Boolean> collisionFunction) {
        this.collisionFunction = collisionFunction;
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
        return new DestinationProfile(firstDestination, collisionFunction, angularVelocityFunction);
    }
}
