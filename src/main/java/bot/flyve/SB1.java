package bot.flyve;

import bot.flyve.states.PlayTheGame;
import util.rocket_league.io.input.DataPacket;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.Behaviour;
import util.state_machine.StateMachine;

public class SB1 extends StateMachine<DataPacket, ControlsOutput> {
    public SB1() {
        super(new PlayTheGame());
    }
}
