package bot.flyve.states.kickoff.states;

import util.rocket_league.io.input.DataPacket;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.State;

public class MiddleLeft extends State<DataPacket, ControlsOutput> {

    @Override
    public ControlsOutput exec(DataPacket input) {
        return new ControlsOutput();
    }

    @Override
    public State<DataPacket, ControlsOutput> next(DataPacket input) {
        return this;
    }
}
