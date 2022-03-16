package bot.flyve.states.game_started;

import bot.flyve.states.kickoff.Kickoff;
import bot.flyve.states.kickoff.KickoffPosition;
import util.data_structure.tupple.Tuple3;
import util.rocket_league.controllers.ground.dribble.reverse_hit.ReverseBallHitController;
import util.rocket_league.io.input.DataPacket;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.State;

public class GameStarted extends State<DataPacket, ControlsOutput> {
    private ReverseBallHitController reverseBallHitController;

    public GameStarted() {
        this.reverseBallHitController = new ReverseBallHitController();
    }

    @Override
    public void start(DataPacket input) {

    }

    @Override
    public ControlsOutput exec(DataPacket input) {
        return reverseBallHitController.exec(new Tuple3<>(input.car, input.ball, new ControlsOutput()));
    }

    @Override
    public void stop(DataPacket input) {

    }

    @Override
    public State<DataPacket, ControlsOutput> next(DataPacket input) {
        if(isGameDone(input)) {
            return new Kickoff();
        }
        return this;
    }

    private boolean isGameDone(DataPacket input) {
        return !KickoffPosition.kickoffFinished(input);
    }
}
