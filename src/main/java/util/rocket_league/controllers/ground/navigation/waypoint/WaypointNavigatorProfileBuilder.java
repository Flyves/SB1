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

public class WaypointNavigatorProfileBuilder implements Builder<WaypointNavigatorProfile> {
    private BiFunction<ExtendedCarData, Vector3, Boolean> collisionFunction;
    private Function<Double, Double> angularVelocityFunction;
    private LinkedHashSet<Vector3> waypoints;
    private SecondJumpType secondJumpType;
    private Supplier<Double> targetSpeedSupplier;
    private double minimumBoostAmount;

    public WaypointNavigatorProfileBuilder() {
        this.collisionFunction = DestinationCollisionDetection.DEFAULT_COLLISION_DETECTION_FUNCTION;
        this.angularVelocityFunction = GroundSteering::findMaxAngularVelocity;
        this.waypoints = new LinkedHashSet<>();
        this.secondJumpType = SecondJumpType.NONE;
        this.targetSpeedSupplier = () -> Constants.CAR_MAX_SPEED;
        this.minimumBoostAmount = 0;
    }

    /**
     *  Use this building method to set the collision condition that indicates we reached the destination.
     * @param collisionFunction a function that indicates whether we reached the destination or not
     * @return the builder object
     */
    public WaypointNavigatorProfileBuilder withCollision(final BiFunction<ExtendedCarData, Vector3, Boolean> collisionFunction) {
        this.collisionFunction = collisionFunction;
        return this;
    }

    /**
     *  Use this building method to set the dynamic angular velocity expected when turning on the first destination.
     * @param angularVelocityFunction a function to map the velocity of the car to the angular velocity expected when turning
     * @return the builder object
     */
    public WaypointNavigatorProfileBuilder withAngularVelocity(final Function<Double, Double> angularVelocityFunction) {
        this.angularVelocityFunction = angularVelocityFunction;
        return this;
    }

    public WaypointNavigatorProfileBuilder withWaypoints(final LinkedHashSet<Vector3> waypoints) {
        this.waypoints = waypoints;
        return this;
    }

    public WaypointNavigatorProfileBuilder withFlipType(final SecondJumpType secondJumpType) {
        this.secondJumpType = secondJumpType;
        return this;
    }

    public WaypointNavigatorProfileBuilder withTargetSpeed(final Supplier<Double> targetSpeedSupplier) {
        this.targetSpeedSupplier = targetSpeedSupplier;
        return this;
    }

    public WaypointNavigatorProfileBuilder withMinimumBoostAmount(final double minimumBoostAmount) {
        this.minimumBoostAmount = minimumBoostAmount;
        return this;
    }

    @Override
    public WaypointNavigatorProfile build() {
        return new WaypointNavigatorProfile(
                angularVelocityFunction,
                collisionFunction,
                waypoints,
                secondJumpType,
                targetSpeedSupplier,
                minimumBoostAmount);
    }
}
