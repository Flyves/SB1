package util.rocket_league.controllers.ground.navigation.boostpad;

import util.data_structure.builder.Builder;
import util.rocket_league.Constants;
import util.rocket_league.controllers.ground.steer.angular_velocity.GroundSteering;
import util.rocket_league.controllers.jump.second.SecondJumpType;
import util.rocket_league.dynamic_objects.boost.BoostPad;

import java.util.function.Function;
import java.util.function.Supplier;

public class BoostPadNavigatorProfileBuilder implements Builder<BoostPadNavigatorProfile> {
    private BoostPad boostPadSource;
    private BoostPad boostPadTarget;
    private Function<Double, Double> angularVelocityFunction;
    private SecondJumpType secondJumpType;
    private Supplier<Double> targetSpeedSupplier;
    private double minimumBoostAmount;

    public BoostPadNavigatorProfileBuilder() {
        this.boostPadSource = null;
        this.boostPadTarget = null;
        this.angularVelocityFunction = GroundSteering::findMaxAngularVelocity;
        this.secondJumpType = SecondJumpType.NONE;
        this.targetSpeedSupplier = () -> Constants.CAR_MAX_SPEED;
        this.minimumBoostAmount = 0;
    }

    public BoostPadNavigatorProfileBuilder withMinimumBoostAmount(final double minimumBoostAmount) {
        this.minimumBoostAmount = minimumBoostAmount;
        return this;
    }

    public BoostPadNavigatorProfileBuilder withFlipType(final SecondJumpType secondJumpType) {
        this.secondJumpType = secondJumpType;
        return this;
    }

    /**
     *  Use this building method to set the dynamic angular velocity expected when turning on the first destination.
     * @param angularVelocityFunction a function to map the velocity of the car to the angular velocity expected when turning
     * @return the builder object
     */
    public BoostPadNavigatorProfileBuilder withAngularVelocity(final Function<Double, Double> angularVelocityFunction) {
        this.angularVelocityFunction = angularVelocityFunction;
        return this;
    }

    public BoostPadNavigatorProfileBuilder withBoostPadSource(final BoostPad boostPadSource) {
        this.boostPadSource = boostPadSource;
        return this;
    }

    public BoostPadNavigatorProfileBuilder withBoostPadTarget(final BoostPad boostPadTarget) {
        this.boostPadTarget = boostPadTarget;
        return this;
    }

    public BoostPadNavigatorProfileBuilder withTargetSpeed(final Supplier<Double> targetSpeedSupplier) {
        this.targetSpeedSupplier = targetSpeedSupplier;
        return this;
    }

    @Override
    public BoostPadNavigatorProfile build() {
        if(boostPadSource == null || boostPadTarget == null) {
            throw new UnableToConstructBoostPadPathException();
        }
        return new BoostPadNavigatorProfile(
                boostPadSource,
                boostPadTarget,
                angularVelocityFunction,
                secondJumpType,
                targetSpeedSupplier,
                minimumBoostAmount);
    }
}
