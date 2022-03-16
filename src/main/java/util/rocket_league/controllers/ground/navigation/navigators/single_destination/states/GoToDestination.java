package util.rocket_league.controllers.ground.navigation.navigators.single_destination.states;

import util.data_structure.tupple.Tuple2;
import util.data_structure.tupple.Tuple3;
import util.math.vector.Vector3;
import util.rocket_league.controllers.ground.navigation.navigators.seasoner.NavigationSeasoner;
import util.rocket_league.controllers.ground.navigation.navigators.seasoner.SeasonerProfileBuilder;
import util.rocket_league.controllers.ground.navigation.navigators.single_destination.DestinationNavigator;
import util.rocket_league.controllers.ground.navigation.navigators.single_destination.DestinationNavigatorFinisher;
import util.rocket_league.controllers.ground.navigation.navigators.single_destination.DestinationProfile;
import util.rocket_league.controllers.ground.steer.orientation.OrientationController;
import util.rocket_league.controllers.ground.steer.orientation.OrientationProfileBuilder;
import util.rocket_league.controllers.ground.throttle.SpeedController;
import util.rocket_league.controllers.jump.second.SecondJumpType;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.State;

public class GoToDestination<T> extends State<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput> {
    private final DestinationNavigator<T> destinationNavigator;
    private final DestinationProfile<T> destinationProfile;
    private final OrientationController orientationController;
    private final SpeedController speedController;
    private final NavigationSeasoner navigationSeasoner;

    public GoToDestination(
            final DestinationNavigator<T> destinationNavigator,
            final DestinationProfile<T> destinationProfile) {
        this.destinationNavigator = destinationNavigator;
        this.destinationProfile = destinationProfile;
        this.orientationController = new OrientationController(new OrientationProfileBuilder()
                .withMaxAngularVelocity(destinationProfile.angularVelocityFunction)
                .build());
        this.speedController = new SpeedController();
        this.navigationSeasoner = new NavigationSeasoner(new SeasonerProfileBuilder()
                .withDestination(() -> destinationProfile.positionObjectMapper.apply(destinationProfile.destination))
                .withFlipType(destinationProfile.secondJumpType)
                .withTargetSpeed(destinationProfile.targetSpeedSupplier)
                .withMinimumBoostAmount(destinationProfile.minimumBoostAmountSupplier)
                .build());
    }

    @Override
    public ControlsOutput exec(final Tuple2<ExtendedCarData, ControlsOutput> io) {
        final Vector3 destination = destinationProfile.positionObjectMapper.apply(destinationProfile.destination);
        final double targetSpeed = destinationProfile.targetSpeedSupplier.get();
        driveOnGround(io, destination, targetSpeed);
        return navigationSeasoner.exec(io);
    }

    private void driveOnGround(Tuple2<ExtendedCarData, ControlsOutput> io, Vector3 destination, double targetSpeed) {
        try {
            orientationController.exec(new Tuple3<>(io.value1, io.value2, destination));
            speedController.exec(new Tuple3<>(io.value1, io.value2, targetSpeed));
        }
        catch (final Exception ignored) {}
    }

    @Override
    public State<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput> next(final Tuple2<ExtendedCarData, ControlsOutput> io) {
        if(firstDestinationReached(io)) {
            new DestinationNavigatorFinisher<>(destinationNavigator).finish();
        }
        return this;
    }

    private boolean firstDestinationReached(Tuple2<ExtendedCarData, ControlsOutput> io) {
        return destinationProfile.collisionFunction.apply(io.value1, destinationProfile.destination);
    }

    public void forceSecondJump(final SecondJumpType secondJumpType, final ExtendedCarData car) {
        navigationSeasoner.forceSecondJump(secondJumpType, car);
    }

    public boolean isFlipping() {
        return navigationSeasoner.isFlipping();
    }
}