package util.rocket_league.controllers.ground.steer.orientation;

import java.util.function.Function;

public class OrientationProfile {
    public final Function<Double, Double> angularVelocityFunction;

    public OrientationProfile(final Function<Double, Double> angularVelocityFunction) {
        this.angularVelocityFunction = angularVelocityFunction;
    }
}
