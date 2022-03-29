package util.rocket_league.controllers.ground.dribble.strong;

import util.data_structure.builder.Builder;

import java.util.function.Supplier;

public class StrongDribbleProfileBuilder implements Builder<StrongDribbleProfile> {
    private Supplier<Double> steeringOffsetFunction;
    private Supplier<Double> throttlingOffsetFunction;
    private Supplier<Double> minimumBoostAmount;

    public StrongDribbleProfileBuilder() {
        steeringOffsetFunction = () -> 0d;
        throttlingOffsetFunction = () -> 0d;
        minimumBoostAmount = () -> 0d;
    }

    public StrongDribbleProfileBuilder withSteeringOffset(final Supplier<Double> steeringOffsetFunction) {
        this.steeringOffsetFunction = steeringOffsetFunction;
        return this;
    }

    public StrongDribbleProfileBuilder withThrottlingOffset(final Supplier<Double> throttlingOffsetFunction) {
        this.throttlingOffsetFunction = throttlingOffsetFunction;
        return this;
    }

    public StrongDribbleProfileBuilder withMinimumBoostAmount(final Supplier<Double> minimumBoostAmountSupplier) {
        this.minimumBoostAmount = minimumBoostAmountSupplier;
        return this;
    }

    @Override
    public StrongDribbleProfile build() {
        return new StrongDribbleProfile(steeringOffsetFunction, throttlingOffsetFunction, minimumBoostAmount);
    }
}
