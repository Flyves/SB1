package bot.flyve.states.kickoff;

import bot.flyve.states.game_started.GameStarted;
import bot.flyve.states.kickoff.states.*;
import util.data_structure.tupple.Tuple2;
import util.math.statistics.StatisticalData;
import util.math.vector.Vector3;
import util.rocket_league.controllers.ground.navigation.bi_destination.BiDestinationNavigator;
import util.rocket_league.controllers.ground.navigation.bi_destination.BiDestinationProfileBuilder;
import util.rocket_league.io.input.DataPacket;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.State;
import util.state_machine.StateMachine;

public class Kickoff extends State<DataPacket, ControlsOutput> {

    private StateMachine<DataPacket, ControlsOutput> stateMachine;

    private BiDestinationNavigator biDestinationNavigator;

    StatisticalData<Double> statisticalData = new StatisticalData<>(60);

    @Override
    public void start(DataPacket input) {
        final KickoffPosition kickoffPosition = KickoffPosition.findPosition(input.car);
        State<DataPacket, ControlsOutput> startingState;
        switch (kickoffPosition) {
            case FAR_LEFT: startingState = new FarLeft();
            break;
            case FAR_RIGHT: startingState = new FarRight();
            break;
            case MIDDLE_LEFT: startingState = new MiddleLeft();
            break;
            case MIDDLE_RIGHT: startingState = new MiddleRight();
            break;
            case MIDDLE: startingState = new Middle();
            break;
            default: startingState = null;
        }
        stateMachine = new StateMachine<>(startingState);
        biDestinationNavigator = new BiDestinationNavigator(new BiDestinationProfileBuilder()
                .withFirstDestination(new Vector3())
                .withSecondDestination(new Vector3(0, 3000, 0))
                .withAngularVelocity(v -> 3.0)
                .build());
    }

    @Override
    public ControlsOutput exec(DataPacket input) {
        //return stateMachine.exec(input);
        final ControlsOutput controlsOutput = new ControlsOutput();
        controlsOutput.throttle = 1f;
        return biDestinationNavigator.exec(new Tuple2<>(input.car, controlsOutput));
    }

    @Override
    public State<DataPacket, ControlsOutput> next(DataPacket input) {
        if(isKickoffDone(input)) {
            return new GameStarted();
        }
        return this;
    }

    private boolean isKickoffDone(DataPacket input) {
        return KickoffPosition.kickoffFinished(input);
    }
}
