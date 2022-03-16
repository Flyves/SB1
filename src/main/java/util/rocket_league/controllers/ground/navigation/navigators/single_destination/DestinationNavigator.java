package util.rocket_league.controllers.ground.navigation.navigators.single_destination;

import util.data_structure.tupple.Tuple2;
import util.rocket_league.controllers.ground.navigation.navigators.Navigator;
import util.rocket_league.controllers.ground.navigation.navigators.single_destination.states.GoToDestination;
import util.rocket_league.controllers.jump.second.SecondJumpType;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.StateMachine;

public class DestinationNavigator<T> implements Navigator {
    private final StateMachine<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput> stateMachine;
    private final GoToDestination<T> goToDestination;
    private boolean isFinished;

    public DestinationNavigator(final DestinationProfile<T> destinationProfile) {
        this.goToDestination = new GoToDestination<>(this, destinationProfile);
        this.stateMachine = new StateMachine<>(goToDestination);
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

    @SuppressWarnings("SameParameterValue")
    void setIsFinished(final boolean isFinished) {
        this.isFinished = isFinished;
    }

    public void forceSecondJump(final SecondJumpType secondJumpType, final ExtendedCarData car) {
        goToDestination.forceSecondJump(secondJumpType, car);
    }

    public boolean isFlipping() {
        return goToDestination.isFlipping();
    }
}
