package util.rocket_league.controllers.ground.navigation.navigators.single_destination;

import com.sun.javafx.UnmodifiableArrayList;
import util.math.vector.Vector3;
import util.rocket_league.controllers.jump.second.SecondJumpType;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class DestinationProfile<T> {
    public final T destination;
    public final Function<T, Vector3> positionObjectMapper;
    public final BiFunction<ExtendedCarData, T, Boolean>  collisionFunction;
    public final Function<Double, Double> angularVelocityFunction;
    public final Supplier<Double> targetSpeedSupplier;
    public final SecondJumpType secondJumpType;
    public final Supplier<Double> minimumBoostAmountSupplier;

    DestinationProfile(
            final T destination,
            final Function<T, Vector3> positionObjectMapper,
            final BiFunction<ExtendedCarData, T, Boolean> collisionFunction,
            final Function<Double, Double> angularVelocityFunction,
            final Supplier<Double> targetSpeedSupplier,
            final SecondJumpType secondJumpType,
            final Supplier<Double> minimumBoostAmountSupplier) {
        this.destination = destination;
        this.positionObjectMapper = positionObjectMapper;
        this.collisionFunction = collisionFunction;
        this.angularVelocityFunction = angularVelocityFunction;
        this.targetSpeedSupplier = targetSpeedSupplier;
        this.secondJumpType = secondJumpType;
        this.minimumBoostAmountSupplier = minimumBoostAmountSupplier;
    }
}
