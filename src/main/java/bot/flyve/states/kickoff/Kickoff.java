package bot.flyve.states.kickoff;

import bot.flyve.states.game_started.GameStarted;
import bot.flyve.states.kickoff.states.*;
import util.rocket_league.io.input.DataPacket;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.State;
import util.state_machine.StateMachine;

public class Kickoff extends State<DataPacket, ControlsOutput> {

    private StateMachine<DataPacket, ControlsOutput> stateMachine;

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
    }

    @Override
    public ControlsOutput exec(DataPacket input) {
        //System.out.println(input.humanCars.get(0).hasSecondJump);
        return stateMachine.exec(input);
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
