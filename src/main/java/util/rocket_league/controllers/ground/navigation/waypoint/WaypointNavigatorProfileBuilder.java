package util.rocket_league.controllers.ground.navigation.waypoint;

import util.data_structure.builder.Builder;
import util.math.vector.Vector3;
import util.rocket_league.Constants;
import util.rocket_league.controllers.ground.navigation.DestinationCollisionDetection;
import util.rocket_league.controllers.ground.steer.angular_velocity.GroundSteering;
import util.rocket_league.controllers.jump.second.SecondJumpType;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;

import java.util.LinkedHashSet;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class WaypointNavigatorProfileBuilder<T> implements Builder<WaypointNavigatorProfile<T>> {
    private BiFunction<ExtendedCarData, T, Boolean> collisionFunction;
    private Function<Double, Double> angularVelocityFunction;
    private LinkedHashSet<T> waypoints;
    private Function<T, Vector3> positionObjectMapper;
    private SecondJumpType secondJumpType;
    private Supplier<Double> targetSpeedSupplier;
    private double minimumBoostAmount;

    public WaypointNavigatorProfileBuilder() {
        this.collisionFunction = (car, positionObject) -> DestinationCollisionDetection.DEFAULT_COLLISION_DETECTION_FUNCTION
                .apply(car, positionObjectMapper.apply(positionObject));
        this.angularVelocityFunction = GroundSteering::findMaxAngularVelocity;
        this.waypoints = new LinkedHashSet<>();
        this.positionObjectMapper = t -> (Vector3) t;
        this.secondJumpType = SecondJumpType.NONE;
        this.targetSpeedSupplier = () -> Constants.CAR_MAX_SPEED;
        this.minimumBoostAmount = 0;
    }

    /**
     *  Use this building method to set the mapper between your object type and a Vector3.
     * @param positionObjectMapper a function that maps an object to a Vector3
     * @return the builder object
     */
    public WaypointNavigatorProfileBuilder<T> withDestinationMapper(final Function<T, Vector3> positionObjectMapper) {
        this.positionObjectMapper = positionObjectMapper;
        return this;
    }

    /**
     *  Use this building method to set the collision condition that indicates we reached the destination.
     * @param collisionFunction a function that indicates whether we reached the destination or not
     * @return the builder object
     */
    public WaypointNavigatorProfileBuilder<T> withCollision(final BiFunction<ExtendedCarData, T, Boolean> collisionFunction) {
        this.collisionFunction = collisionFunction;
        return this;
    }

    /**
     *  Use this building method to set the dynamic angular velocity expected when turning on the first destination.
     * @param angularVelocityFunction a function to map the velocity of the car to the angular velocity expected when turning
     * @return the builder object
     */
    public WaypointNavigatorProfileBuilder<T> withAngularVelocity(final Function<Double, Double> angularVelocityFunction) {
        this.angularVelocityFunction = angularVelocityFunction;
        return this;
    }

    public WaypointNavigatorProfileBuilder<T> withWaypoints(final LinkedHashSet<T> waypoints) {
        this.waypoints = waypoints;
        return this;
    }

    public WaypointNavigatorProfileBuilder<T> withFlipType(final SecondJumpType secondJumpType) {
        this.secondJumpType = secondJumpType;
        return this;
    }

    public WaypointNavigatorProfileBuilder<T> withTargetSpeed(final Supplier<Double> targetSpeedSupplier) {
        this.targetSpeedSupplier = targetSpeedSupplier;
        return this;
    }

    public WaypointNavigatorProfileBuilder<T> withMinimumBoostAmount(final double minimumBoostAmount) {
        this.minimumBoostAmount = minimumBoostAmount;
        return this;
    }

    @Override
    public WaypointNavigatorProfile<T> build() {
        return new WaypointNavigatorProfile<>(
                angularVelocityFunction,
                collisionFunction,
                waypoints,
                positionObjectMapper,
                secondJumpType,
                targetSpeedSupplier,
                minimumBoostAmount);
    }
}
