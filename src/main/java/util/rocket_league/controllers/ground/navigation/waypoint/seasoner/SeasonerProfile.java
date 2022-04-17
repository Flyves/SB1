package util.rocket_league.controllers.ground.navigation.waypoint.seasoner;

import util.math.vector.Vector3;
import util.rocket_league.controllers.jump.second.SecondJumpType;

import java.util.function.Supplier;

public class SeasonerProfile {
    public final Supplier<Vector3> destinationSupplier;
    public final Supplier<Double> targetSpeedSupplier;
    public final Supplier<Double> minimumBoostAmountSupplier;
    public final SecondJumpType secondJumpType;

    SeasonerProfile(
            final Supplier<Vector3> destinationSupplier,
            final Supplier<Double> targetSpeedSupplier,
            final Supplier<Double> minimumBoostAmountSupplier,
            final SecondJumpType secondJumpType) {
        this.destinationSupplier = destinationSupplier;
        this.targetSpeedSupplier = targetSpeedSupplier;
        this.secondJumpType = secondJumpType;
        this.minimumBoostAmountSupplier = minimumBoostAmountSupplier;
    }
}
