package bot.flyve.states.kickoff_done;

import util.rocket_league.io.input.DataPacket;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.State;

public class KickoffDone extends State<DataPacket, ControlsOutput> {

    @Override
    public void start(DataPacket input) {

    }

    @Override
    public ControlsOutput exec(DataPacket input) {
        return new ControlsOutput();
    }

    @Override
    public void stop(DataPacket input) {

    }

    @Override
    public State<DataPacket, ControlsOutput> next(DataPacket input) {
        return this;
    }
}
