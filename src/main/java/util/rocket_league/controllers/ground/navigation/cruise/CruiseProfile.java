package util.rocket_league.controllers.ground.navigation.cruise;

import util.math.vector.Ray3;

import java.util.function.Supplier;

public class CruiseProfile {
    public final Supplier<Ray3> destination;
    public final Supplier<Double> minimumBoostAmount;

    CruiseProfile(final Supplier<Ray3> destination, final Supplier<Double> minimumBoostAmount) {
        this.destination = destination;
        this.minimumBoostAmount = minimumBoostAmount;
    }
}
