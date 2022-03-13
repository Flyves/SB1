package util.rocket_league.controllers.ground.navigation.destination;

import util.math.vector.Vector3;
import util.rocket_league.controllers.ground.navigation.DestinationCollisionDetection;
import util.rocket_league.controllers.ground.steer.angular_velocity.GroundSteering;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;

import java.util.function.BiFunction;
import java.util.function.Function;

public class DestinationProfileBuilder<T> {
    private T destinationObject;
    private Function<T, Vector3> positionObjectMapper;
    private BiFunction<ExtendedCarData, T, Boolean> collisionFunction;
    private Function<Double, Double> angularVelocityFunction;

    public DestinationProfileBuilder() {
        this.destinationObject = null;
        this.positionObjectMapper = t -> (Vector3) t;
        this.collisionFunction = (car, positionObject) -> DestinationCollisionDetection.DEFAULT_COLLISION_DETECTION_FUNCTION
                .apply(car, positionObjectMapper.apply(positionObject));
        this.angularVelocityFunction = GroundSteering::findMaxAngularVelocity;
    }

    public DestinationProfileBuilder<T> withDestination(final T destination) {
        this.destinationObject = destination;
        return this;
    }

    /**
     *  Use this building method to set the mapper between your object type and a Vector3.
     * @param positionObjectMapper a function that maps an object to a Vector3
     * @return the builder object
     */
    public DestinationProfileBuilder<T> withDestinationMapper(final Function<T, Vector3> positionObjectMapper) {
        this.positionObjectMapper = positionObjectMapper;
        return this;
    }

    /**
     *  Use this building method to set the collision condition indicating that we reached the destination.
     * @param collisionFunction a function that indicates whether we reached the destination or not
     * @return the builder object
     */
    public DestinationProfileBuilder<T> withCollision(final BiFunction<ExtendedCarData, T, Boolean> collisionFunction) {
        this.collisionFunction = collisionFunction;
        return this;
    }

    /**
     *  Use this building method to set the dynamic angular velocity expected when turning on the first destination.
     * @param angularVelocityFunction a function to map the velocity of the car to the angular velocity expected when turning
     * @return the builder object
     */
    public DestinationProfileBuilder<T> withAngularVelocity(final Function<Double, Double> angularVelocityFunction) {
        this.angularVelocityFunction = angularVelocityFunction;
        return this;
    }

    public DestinationProfile<T> build() {
        return new DestinationProfile<T>(destinationObject, positionObjectMapper, collisionFunction, angularVelocityFunction);
    }
}
