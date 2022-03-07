package bot.flyve.states;

import bot.flyve.states.kickoff.Kickoff;
import bot.flyve.states.kickoff.KickoffPosition;
import bot.flyve.states.kickoff_done.KickoffDone;
import util.rocket_league.io.input.DataPacket;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.State;
import util.state_machine.StateMachine;

public class PlayTheGame extends State<DataPacket, ControlsOutput> {

    private StateMachine<DataPacket, ControlsOutput> stateMachine;

    @Override
    public void start(final DataPacket input) {
        if(KickoffPosition.hasToGoToKickoff(input)) {
            stateMachine = new StateMachine<>(new Kickoff());
        }
        else {
            stateMachine = new StateMachine<>(new KickoffDone());
        }
    }

    @Override
    public ControlsOutput exec(DataPacket input) {
        return stateMachine.exec(input);
    }

    @Override
    public State<DataPacket, ControlsOutput> next(DataPacket input) {
        return this;
    }
}
