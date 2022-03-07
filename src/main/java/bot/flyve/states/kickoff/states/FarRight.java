package bot.flyve.states.kickoff.states;

import util.rocket_league.io.input.DataPacket;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.State;

public class FarRight extends State<DataPacket, ControlsOutput> {

    @Override
    public ControlsOutput exec(DataPacket input) {
        return null;
    }

    @Override
    public State<DataPacket, ControlsOutput> next(DataPacket input) {
        return null;
    }
}
