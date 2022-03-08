package bot.flyve;

import bot.flyve.states.PlayTheGame;
import util.rocket_league.io.input.DataPacket;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.Behaviour;
import util.state_machine.StateMachine;

public class SB1 implements Behaviour<DataPacket, ControlsOutput> {

    private final StateMachine<DataPacket, ControlsOutput> stateMachine;

    public SB1() {
        stateMachine = new StateMachine<>(new PlayTheGame());
    }

    @Override
    public ControlsOutput exec(DataPacket input) {
        return stateMachine.exec(input);
    }
}
