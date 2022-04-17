package util.rocket_league.controllers.ground.dribble.strong;

import java.util.function.Supplier;

public class StrongDribbleProfile {
    public final Supplier<Double> steeringOffsetFunction;
    public final Supplier<Double> throttlingOffsetFunction;
    public final Supplier<Double> minimumBoostAmount;

    StrongDribbleProfile(
            final Supplier<Double> steeringOffsetFunction,
            final Supplier<Double> throttlingOffsetFunction,
            final Supplier<Double> minimumBoostAmount) {
        this.steeringOffsetFunction = steeringOffsetFunction;
        this.throttlingOffsetFunction = throttlingOffsetFunction;
        this.minimumBoostAmount = minimumBoostAmount;
    }
}
