package util.rocket_league.controllers.ground.navigation.cruise;

import util.data_structure.builder.Builder;
import util.math.vector.Ray3;

import java.util.function.Supplier;

public class CruiseProfileBuilder implements Builder<CruiseProfile> {
    private Supplier<Ray3> destination;
    private Supplier<Double> minimumBoostAmount;

    public CruiseProfileBuilder() {
        this.destination = () -> null;
        this.minimumBoostAmount = () -> 100d;
    }

    public CruiseProfileBuilder withDestination(final Supplier<Ray3> destination) {
        this.destination = destination;
        return this;
    }

    public CruiseProfileBuilder withMinimumBoostAmount(final Supplier<Double> minimumBoostAmount) {
        this.minimumBoostAmount = minimumBoostAmount;
        return this;
    }

    @Override
    public CruiseProfile build() {
        return new CruiseProfile(destination, minimumBoostAmount);
    }
}
