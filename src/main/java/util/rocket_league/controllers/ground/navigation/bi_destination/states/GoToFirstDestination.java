package util.rocket_league.controllers.ground.navigation.bi_destination.states;

import util.data_structure.tupple.Tuple2;
import util.data_structure.tupple.Tuple3;
import util.rocket_league.controllers.ground.navigation.bi_destination.BiDestinationNavigator;
import util.rocket_league.controllers.ground.navigation.bi_destination.BiDestinationNavigatorFinisher;
import util.rocket_league.controllers.ground.navigation.bi_destination.BiDestinationProfile;
import util.rocket_league.controllers.ground.steer.orientation.OrientationController;
import util.rocket_league.controllers.ground.steer.orientation.OrientationProfileBuilder;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.State;

public class GoToFirstDestination extends State<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput> {
    private final BiDestinationNavigator biDestinationNavigator;
    private final BiDestinationProfile biDestinationProfile;
    private final OrientationController orientationController;

    public GoToFirstDestination(
            final BiDestinationNavigator biDestinationNavigator,
            final BiDestinationProfile biDestinationProfile) {
        this.biDestinationNavigator = biDestinationNavigator;
        this.biDestinationProfile = biDestinationProfile;
        this.orientationController = new OrientationController(new OrientationProfileBuilder()
                .withMaxAngularVelocity(biDestinationProfile.angularVelocityFunction)
                .build());
    }

    @Override
    public ControlsOutput exec(final Tuple2<ExtendedCarData, ControlsOutput> io) {
        return orientationController.exec(new Tuple3<>(io.value1, io.value2, biDestinationProfile.firstDestination));
    }

    @Override
    public State<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput> next(final Tuple2<ExtendedCarData, ControlsOutput> io) {
        if(firstDestinationReached(io)) {
            new BiDestinationNavigatorFinisher(biDestinationNavigator).reachFirstDestination();
            return new TurnTowardSecondDestination(biDestinationNavigator, biDestinationProfile);
        }
        return this;
    }

    private boolean firstDestinationReached(Tuple2<ExtendedCarData, ControlsOutput> io) {
        return biDestinationProfile.firstDestination.distanceSquared(io.value1.position) < 100 * 100;
    }
}
