package util.rocket_league.controllers.ground.navigation.destination;

import util.math.vector.Vector3;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;

import java.util.function.BiFunction;
import java.util.function.Function;

public class DestinationProfile {
    public final Vector3 destination;
    public final BiFunction<ExtendedCarData, Vector3, Boolean>  collisionFunction;
    public final Function<Double, Double> angularVelocityFunction;

    public DestinationProfile(
            final Vector3 destination,
            final BiFunction<ExtendedCarData, Vector3, Boolean> collisionFunction,
            final Function<Double, Double> angularVelocityFunction) {
        this.destination = destination;
        this.collisionFunction = collisionFunction;
        this.angularVelocityFunction = angularVelocityFunction;
    }
}
