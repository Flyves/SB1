package util.rocket_league.controllers.ground.navigation.destination.states;

import util.data_structure.tupple.Tuple2;
import util.data_structure.tupple.Tuple3;
import util.rocket_league.controllers.ground.navigation.destination.DestinationNavigator;
import util.rocket_league.controllers.ground.navigation.destination.DestinationNavigatorFinisher;
import util.rocket_league.controllers.ground.navigation.destination.DestinationProfile;
import util.rocket_league.controllers.ground.steer.orientation.OrientationController;
import util.rocket_league.controllers.ground.steer.orientation.OrientationProfileBuilder;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.State;

public class GoToDestination<T> extends State<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput> {
    private final DestinationNavigator<T> destinationNavigator;
    private final DestinationProfile<T> destinationProfile;
    private final OrientationController orientationController;

    public GoToDestination(
            final DestinationNavigator<T> destinationNavigator,
            final DestinationProfile<T> destinationProfile) {
        this.destinationNavigator = destinationNavigator;
        this.destinationProfile = destinationProfile;
        this.orientationController = new OrientationController(new OrientationProfileBuilder()
                .withMaxAngularVelocity(destinationProfile.angularVelocityFunction)
                .build());
    }

    @Override
    public ControlsOutput exec(final Tuple2<ExtendedCarData, ControlsOutput> io) {
        return orientationController.exec(new Tuple3<>(io.value1, io.value2,
                destinationProfile.positionObjectMapper.apply(destinationProfile.destination)));
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
}
