package util.rocket_league.controllers.ground.navigation.navigators.seasoner;

import util.data_structure.tupple.Tuple2;
import util.math.vector.Vector3;
import util.rocket_league.controllers.ground.navigation.navigators.Navigator;
import util.rocket_league.controllers.jump.jump_sequence.JumpController;
import util.rocket_league.controllers.jump.jump_sequence.JumpProfileBuilder;
import util.rocket_league.controllers.jump.second.SecondJumpType;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;

public class NavigationSeasoner implements Navigator {
    private final SeasonerProfile seasonerProfile;
    private JumpController jumpController;

    public NavigationSeasoner(final SeasonerProfile seasonerProfile) {
        this.seasonerProfile = seasonerProfile;
        this.jumpController = new JumpController(new JumpProfileBuilder().build());
    }

    @Override
    public ControlsOutput exec(Tuple2<ExtendedCarData, ControlsOutput> io) {
        handleJump(io, seasonerProfile.destinationSupplier.get());
        handleBoost(io, getTargetDrivingSpeed(), seasonerProfile.destinationSupplier.get());
        return io.value2;
    }

    private void handleJump(final Tuple2<ExtendedCarData, ControlsOutput> io, final Vector3 destination) {
        if(shouldJump(io, destination)) {
            jumpController = getNewJumpController(io, destination);
        }
        jumpController.exec(new Tuple2<>(io.value1, io.value2));
    }

    private void handleBoost(final Tuple2<ExtendedCarData, ControlsOutput> io, final double maxSpeed, final Vector3 destination) {
        io.value2.isBoosting = shouldTryToBoost(io, maxSpeed);
        if(shouldPreventBoost(io, destination)) {
            io.value2.isBoosting = false;
        }
    }

    private Double getTargetDrivingSpeed() {
        return seasonerProfile.targetSpeedSupplier.get();
    }

    private boolean shouldTryToBoost(final Tuple2<ExtendedCarData, ControlsOutput> io, final double maxSpeed) {
        return BoostHandler.shouldTryToBoost(io, maxSpeed, seasonerProfile.minimumBoostAmountSupplier.get());
    }

    private boolean shouldPreventBoost(final Tuple2<ExtendedCarData, ControlsOutput> io, final Vector3 destination) {
        return BoostHandler.shouldPreventBoostToKeepTheFlow(
                io,
                destination,
                jumpController.isFinished(),
                seasonerProfile.minimumBoostAmountSupplier.get());
    }

    private boolean shouldJump(final Tuple2<ExtendedCarData, ControlsOutput> io, final Vector3 destination) {
        return JumpCondition.shouldJump(
                io,
                destination,
                jumpController.isFinished());
    }

    private JumpController getNewJumpController(final Tuple2<ExtendedCarData, ControlsOutput> io, final Vector3 destination) {
        return JumpControllerGenerator.generateTheRightJumpController(
                seasonerProfile.secondJumpType,
                io.value1,
                destination);
    }

    public void forceSecondJump(final SecondJumpType secondJumpType, final ExtendedCarData car) {
        jumpController = JumpControllerGenerator.generateTheRightJumpController(
                secondJumpType,
                car,
                seasonerProfile.destinationSupplier.get());
    }

    public boolean isFlipping() {
        return jumpController != null && !jumpController.isFinished();
    }

    @Override
    public boolean isFinished() {
        return jumpController.isFinished();
    }
}
