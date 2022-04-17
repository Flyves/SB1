package util.rocket_league.controllers.ground.dribble.ball_contourer;

import util.data_structure.builder.Builder;

import java.util.function.Supplier;

public class BallContournerProfileBuilder implements Builder<BallContournerProfile> {
    private Supplier<Double> hitAngleFunction;
    private Supplier<Double> minimumBoostAmount;

    public BallContournerProfileBuilder() {
        hitAngleFunction = () -> 0d;
        minimumBoostAmount = () -> 0d;
    }

    public BallContournerProfileBuilder withHitAngle(final Supplier<Double> hitAngleFunction) {
        this.hitAngleFunction = hitAngleFunction;
        return this;
    }

    public BallContournerProfileBuilder withMinimumBoostAmount(final Supplier<Double> minimumBoostAmountSupplier) {
        this.minimumBoostAmount = minimumBoostAmountSupplier;
        return this;
    }

    @Override
    public BallContournerProfile build() {
        return new BallContournerProfile(hitAngleFunction, minimumBoostAmount);
    }
}
