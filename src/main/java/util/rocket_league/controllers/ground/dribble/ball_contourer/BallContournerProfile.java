package util.rocket_league.controllers.ground.dribble.ball_contourer;

import java.util.function.Supplier;

public class BallContournerProfile {
    public final Supplier<Double> hitAngleFunction;
    public final Supplier<Double> minimumBoostAmount;

    BallContournerProfile(
            final Supplier<Double> hitAngleFunction,
            final Supplier<Double> minimumBoostAmount) {
        this.hitAngleFunction = hitAngleFunction;
        this.minimumBoostAmount = minimumBoostAmount;
    }
}
