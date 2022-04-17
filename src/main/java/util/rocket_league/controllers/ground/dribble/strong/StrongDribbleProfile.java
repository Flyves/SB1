package util.rocket_league.controllers.ground.dribble.strong;

import util.math.vector.Vector3;

import java.util.function.Supplier;

public class StrongDribbleProfile {
    public final Supplier<Vector3> offsetFunction;
    public final Supplier<Double> minimumBoostAmount;

    StrongDribbleProfile(
            final Supplier<Vector3> offsetFunction,
            final Supplier<Double> minimumBoostAmount) {
        this.offsetFunction = offsetFunction;
        this.minimumBoostAmount = minimumBoostAmount;
    }
}
