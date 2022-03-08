package util.rocket_league.controllers.ground.navigation.bi_destination;

import util.math.vector.Vector3;

import java.util.function.Function;

public class BiDestinationProfile {
    public final Vector3 firstDestination;
    public final Vector3 secondDestination;
    public final Function<Double, Double> angularVelocityFunction;

    public BiDestinationProfile(
            final Vector3 firstDestination,
            final Vector3 secondDestination,
            final Function<Double, Double> angularVelocityFunction) {
        this.firstDestination = firstDestination;
        this.secondDestination = secondDestination;
        this.angularVelocityFunction = angularVelocityFunction;
    }
}
