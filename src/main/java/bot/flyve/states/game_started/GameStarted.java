package bot.flyve.states.game_started;

import bot.flyve.states.kickoff.Kickoff;
import bot.flyve.states.kickoff.KickoffPosition;
import util.rocket_league.io.input.DataPacket;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.State;

public class GameStarted extends State<DataPacket, ControlsOutput> {

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
        if(!KickoffPosition.kickoffFinished(input)) {
            return new Kickoff();
        }
        return this;
    }
}
