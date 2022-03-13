package util.rocket_league.controllers.ground.navigation.destination;

import util.math.vector.Vector3;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;

import java.util.function.BiFunction;
import java.util.function.Function;

public class DestinationProfile<T> {
    public final T destination;
    public final Function<T, Vector3> positionObjectMapper;
    public final BiFunction<ExtendedCarData, T, Boolean>  collisionFunction;
    public final Function<Double, Double> angularVelocityFunction;

    DestinationProfile(
            final T destination,
            final Function<T, Vector3> positionObjectMapper,
            final BiFunction<ExtendedCarData, T, Boolean> collisionFunction,
            final Function<Double, Double> angularVelocityFunction) {
        this.destination = destination;
        this.positionObjectMapper = positionObjectMapper;
        this.collisionFunction = collisionFunction;
        this.angularVelocityFunction = angularVelocityFunction;
    }
}
