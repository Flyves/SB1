package util.rocket_league.controllers.ground.navigation.destination;

import util.data_structure.tupple.Tuple2;
import util.math.vector.Vector3;
import util.rocket_league.controllers.ground.navigation.destination.states.GoToDestination;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.Behaviour;
import util.state_machine.Finishable;
import util.state_machine.StateMachine;

public class DestinationNavigator implements Behaviour<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput>, Finishable {
    private final StateMachine<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput> stateMachine;
    private final DestinationProfile destinationProfile;
    private boolean isFinished;

    public DestinationNavigator(final DestinationProfile destinationProfile) {
        this.stateMachine = new StateMachine<>(new GoToDestination(this, destinationProfile));
        this.destinationProfile = destinationProfile;
        this.isFinished = false;
    }

    @Override
    public ControlsOutput exec(Tuple2<ExtendedCarData, ControlsOutput> io) {
        return stateMachine.exec(io);
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    public Vector3 getDestination() {
        return destinationProfile.destination;
    }

    void setIsFinished(final boolean isFinished) {
        this.isFinished = isFinished;
    }
}
