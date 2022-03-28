package bot.flyve.states.round_over;

import bot.flyve.states.kickoff.Kickoff;
import bot.flyve.states.kickoff.KickoffPosition;
import util.rocket_league.io.input.DataPacket;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.State;

public class RoundOver extends State<DataPacket, ControlsOutput> {
    public RoundOver() {
    }

    @Override
    public ControlsOutput exec(DataPacket input) {
        return new ControlsOutput();
    }

    @Override
    public State<DataPacket, ControlsOutput> next(DataPacket input) {
        if(isWaitingForGameStart(input)) {
            return new Kickoff();
        }
        return this;
    }

    private boolean isWaitingForGameStart(DataPacket input) {
        return !KickoffPosition.kickoffFinished(input);
    }
}
