package util.rocket_league.controllers.ground.navigation.bi_destination.states;

import util.data_structure.tupple.Tuple2;
import util.rocket_league.controllers.ground.navigation.bi_destination.BiDestinationProfile;
import util.rocket_league.controllers.ground.steer.AngularVelocityController;
import util.rocket_league.controllers.ground.throttle.SpeedController;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.State;

public class GoToFirstDestination extends State<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput> {
    final BiDestinationProfile biDestinationProfile;
    final SpeedController speedController;
    final AngularVelocityController angularVelocityController;

    public GoToFirstDestination(final BiDestinationProfile biDestinationProfile) {
        this.biDestinationProfile = biDestinationProfile;
        this.speedController = new SpeedController();
        this.angularVelocityController = new AngularVelocityController();
    }

    @Override
    public ControlsOutput exec(final Tuple2<ExtendedCarData, ControlsOutput> io) {
        return null;
    }

    @Override
    public State<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput> next(final Tuple2<ExtendedCarData, ControlsOutput> input) {
        return this;
    }
}
