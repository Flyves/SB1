package util.rocket_league.controllers.ground.dribble.strong;

import util.data_structure.builder.Builder;
import util.math.vector.Vector3;

import java.util.function.Supplier;

public class StrongDribbleProfileBuilder implements Builder<StrongDribbleProfile> {
    private Supplier<Vector3> offsetFunction;
    private Supplier<Double> minimumBoostAmount;

    public StrongDribbleProfileBuilder() {
        offsetFunction = Vector3::new;
        minimumBoostAmount = () -> 0d;
    }

    public StrongDribbleProfileBuilder withOffset(final Supplier<Vector3> offsetFunction) {
        this.offsetFunction = offsetFunction;
        return this;
    }

    public StrongDribbleProfileBuilder withMinimumBoostAmount(final Supplier<Double> minimumBoostAmountSupplier) {
        this.minimumBoostAmount = minimumBoostAmountSupplier;
        return this;
    }

    @Override
    public StrongDribbleProfile build() {
        return new StrongDribbleProfile(offsetFunction, minimumBoostAmount);
    }
}
