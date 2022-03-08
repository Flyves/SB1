package util.rocket_league.controllers.ground.navigation.bi_destination;

import util.data_structure.tupple.Tuple2;
import util.rocket_league.controllers.ground.navigation.bi_destination.states.GoToFirstDestination;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.Behaviour;
import util.state_machine.StateMachine;

public class BiDestinationNavigation implements Behaviour<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput> {
    final BiDestinationProfile biDestinationProfile;

    final StateMachine<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput> stateMachine;

    public BiDestinationNavigation(final BiDestinationProfile biDestinationProfile) {
        this.biDestinationProfile = biDestinationProfile;

        this.stateMachine = new StateMachine<>(new GoToFirstDestination(biDestinationProfile));
    }

    @Override
    public ControlsOutput exec(Tuple2<ExtendedCarData, ControlsOutput> io) {
        return stateMachine.exec(io);
    }
}
