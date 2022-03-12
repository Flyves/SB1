package util.rocket_league.controllers.ground.navigation.boostpad;

import util.rocket_league.controllers.jump.second.SecondJumpType;
import util.rocket_league.dynamic_objects.boost.BoostPad;

import java.util.function.Function;
import java.util.function.Supplier;

public class BoostPadNavigatorProfile {
    public final BoostPad boostPadSource;
    public final BoostPad boostPadTarget;
    public final Function<Double, Double> angularVelocityFunction;
    public final SecondJumpType secondJumpType;
    public final Supplier<Double> targetSpeedSupplier;
    public final double minimumBoostAmount;

    BoostPadNavigatorProfile(
            final BoostPad boostPadSource,
            final BoostPad boostPadTarget,
            final Function<Double, Double> angularVelocityFunction,
            final SecondJumpType secondJumpType,
            final Supplier<Double> targetSpeedSupplier,
            final double minimumBoostAmount) {
        this.boostPadSource = boostPadSource;
        this.boostPadTarget = boostPadTarget;
        this.angularVelocityFunction = angularVelocityFunction;
        this.secondJumpType = secondJumpType;
        this.targetSpeedSupplier = targetSpeedSupplier;
        this.minimumBoostAmount = minimumBoostAmount;
    }
}
