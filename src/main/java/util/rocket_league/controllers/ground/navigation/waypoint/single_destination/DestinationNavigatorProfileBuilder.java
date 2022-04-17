package util.rocket_league.controllers.ground.navigation.waypoint.single_destination;

import util.math.vector.Vector3;
import util.rocket_league.Constants;
import util.rocket_league.controllers.ground.navigation.waypoint.DestinationCollisionDetection;
import util.rocket_league.controllers.ground.steer.angular_velocity.GroundSteering;
import util.rocket_league.controllers.jump.second.SecondJumpType;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class DestinationNavigatorProfileBuilder<T> {
    private T destinationObject;
    private Function<T, Vector3> positionObjectMapper;
    private BiFunction<ExtendedCarData, T, Boolean> collisionFunction;
    private Function<Double, Double> angularVelocityFunction;
    private Supplier<Double> targetSpeedSupplier;
    private SecondJumpType secondJumpType;
    private Supplier<Double> minimumBoostAmount;

    public DestinationNavigatorProfileBuilder() {
        this.destinationObject = null;
        this.positionObjectMapper = t -> (Vector3) t;
        this.collisionFunction = (car, positionObject) -> DestinationCollisionDetection.DEFAULT_COLLISION_DETECTION_FUNCTION
                .apply(car, positionObjectMapper.apply(positionObject));
        this.angularVelocityFunction = GroundSteering::findMaxAngularVelocity;
        this.targetSpeedSupplier = () -> Constants.CAR_MAX_SPEED;
        this.secondJumpType = SecondJumpType.NONE;
        this.minimumBoostAmount = () -> 0d;
    }

    public DestinationNavigatorProfileBuilder<T> withDestination(final T destination) {
        this.destinationObject = destination;
        return this;
    }

    /**
     *  Use this building method to set the mapper between your object type and a Vector3.
     * @param positionObjectMapper a function that maps an object to a Vector3
     * @return the builder object
     */
    public DestinationNavigatorProfileBuilder<T> withDestinationMapper(final Function<T, Vector3> positionObjectMapper) {
        this.positionObjectMapper = positionObjectMapper;
        return this;
    }

    /**
     *  Use this building method to set the collision condition indicating that we reached the destination.
     * @param collisionFunction a function that indicates whether we reached the destination or not
     * @return the builder object
     */
    public DestinationNavigatorProfileBuilder<T> withCollision(final BiFunction<ExtendedCarData, T, Boolean> collisionFunction) {
        this.collisionFunction = collisionFunction;
        return this;
    }

    /**
     *  Use this building method to set the dynamic angular velocity expected when turning on the first destination.
     * @param angularVelocityFunction a function to map the velocity of the car to the angular velocity expected when turning
     * @return the builder object
     */
    public DestinationNavigatorProfileBuilder<T> withAngularVelocity(final Function<Double, Double> angularVelocityFunction) {
        this.angularVelocityFunction = angularVelocityFunction;
        return this;
    }

    /**
     *  Use this building method to set the target cruising speed the car is expected to go at.
     * @param targetSpeedSupplier a supplier to provide the desired speed
     * @return the builder object
     */
    public DestinationNavigatorProfileBuilder<T> withTargetSpeed(final Supplier<Double> targetSpeedSupplier) {
        this.targetSpeedSupplier = targetSpeedSupplier;
        return this;
    }

    public DestinationNavigatorProfileBuilder<T> withFlipType(final SecondJumpType secondJumpType) {
        this.secondJumpType = secondJumpType;
        return this;
    }

    public DestinationNavigatorProfileBuilder<T> withMinimumBoostAmount(final Supplier<Double> minimumBoostAmountSupplier) {
        this.minimumBoostAmount = minimumBoostAmountSupplier;
        return this;
    }

    public DestinationNavigatorProfile<T> build() {
        return new DestinationNavigatorProfile<T>(
                destinationObject,
                positionObjectMapper,
                collisionFunction,
                angularVelocityFunction,
                targetSpeedSupplier,
                secondJumpType,
                minimumBoostAmount);
    }
}
