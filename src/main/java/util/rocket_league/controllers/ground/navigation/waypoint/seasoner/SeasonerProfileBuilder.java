package util.rocket_league.controllers.ground.navigation.waypoint.seasoner;

import util.data_structure.builder.Builder;
import util.math.vector.Vector3;
import util.rocket_league.Constants;
import util.rocket_league.controllers.jump.second.SecondJumpType;

import java.util.function.Supplier;

public class SeasonerProfileBuilder implements Builder<SeasonerProfile> {
    private Supplier<Vector3> destinationSupplier;
    private SecondJumpType secondJumpType;
    private Supplier<Double> targetSpeedSupplier;
    private Supplier<Double> minimumBoostAmountSupplier;

    public SeasonerProfileBuilder() {
        this.secondJumpType = SecondJumpType.NONE;
        this.targetSpeedSupplier = () -> Constants.CAR_MAX_SPEED;
        this.minimumBoostAmountSupplier = () -> 0d;
    }

    public SeasonerProfileBuilder withDestination(final Supplier<Vector3> destinationSupplier) {
        this.destinationSupplier = destinationSupplier;
        return this;
    }

    public SeasonerProfileBuilder withFlipType(final SecondJumpType secondJumpType) {
        this.secondJumpType = secondJumpType;
        return this;
    }

    /**
     *  Use this building method to set the target cruising speed the car is expected to go at.
     * @param targetSpeedSupplier a supplier to provide the desired speed
     * @return the builder object
     */
    public SeasonerProfileBuilder withTargetSpeed(final Supplier<Double> targetSpeedSupplier) {
        this.targetSpeedSupplier = targetSpeedSupplier;
        return this;
    }

    public SeasonerProfileBuilder withMinimumBoostAmount(final Supplier<Double> minimumBoostAmountSupplier) {
        this.minimumBoostAmountSupplier = minimumBoostAmountSupplier;
        return this;
    }

    @Override
    public SeasonerProfile build() {
        return new SeasonerProfile(
                destinationSupplier,
                targetSpeedSupplier,
                minimumBoostAmountSupplier,
                secondJumpType);
    }
}
