package util.rocket_league.controllers.ground.navigation.destination;

import util.math.vector.Vector3;

import java.util.function.Function;

public class DestinationProfile {
    public final Vector3 firstDestination;
    public final Function<Double, Double> angularVelocityFunction;

    public DestinationProfile(
            final Vector3 firstDestination,
            final Function<Double, Double> angularVelocityFunction) {
        this.firstDestination = firstDestination;
        this.angularVelocityFunction = angularVelocityFunction;
    }
}
