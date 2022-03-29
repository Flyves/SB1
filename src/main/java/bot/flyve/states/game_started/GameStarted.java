package bot.flyve.states.game_started;

import bot.flyve.states.kickoff.KickoffPosition;
import bot.flyve.states.round_over.RoundOver;
import util.data_structure.tupple.Tuple2;
import util.data_structure.tupple.Tuple3;
import util.rocket_league.controllers.ground.dribble.strong.StrongDribble;
import util.rocket_league.controllers.ground.dribble.strong.StrongDribbleProfileBuilder;
import util.rocket_league.io.input.DataPacket;
import util.rocket_league.io.output.ControlsOutput;
import util.rocket_league.keyboard_command_listener.HICommandListener;
import util.state_machine.State;

import javax.xml.ws.Holder;


public class GameStarted extends State<DataPacket, ControlsOutput> {
    public final StrongDribble strongDribble;
    public final Holder<Double> offsetLeftRight;
    public final Holder<Double> offsetFrontBack;

    public GameStarted() {
        this.offsetLeftRight = new Holder<>();
        this.offsetFrontBack = new Holder<>();
        this.strongDribble = new StrongDribble(new StrongDribbleProfileBuilder()
                .withSteeringOffset(() -> offsetLeftRight.value)
                .withThrottlingOffset(() -> offsetFrontBack.value)
                .withMinimumBoostAmount(() -> 100d)
                .build());
    }

    @Override
    public void start(DataPacket input) {

    }

    @Override
    public ControlsOutput exec(DataPacket input) {
        offsetLeftRight.value = (double) HICommandListener.instance.asControlsOutput().steer*-70;
        offsetFrontBack.value = (double) HICommandListener.instance.asControlsOutput().throttle*50;
        return strongDribble.exec(new Tuple3<>(input.car, input.ball, new ControlsOutput()));
    }

    @Override
    public void stop(DataPacket input) {

    }

    @Override
    public State<DataPacket, ControlsOutput> next(final DataPacket input) {
        if(!input.isRoundActive || !KickoffPosition.kickoffFinished(input)) {
            return new RoundOver();
        }
        return this;
    }
}
