package bot.flyve.states.game_started;

import bot.flyve.states.kickoff.KickoffPosition;
import bot.flyve.states.round_over.RoundOver;
import util.data_structure.tupple.Tuple3;
import util.math.vector.Ray3;
import util.math.vector.Vector3;
import util.rocket_league.controllers.ground.dribble.strong.StrongDribble;
import util.rocket_league.controllers.ground.dribble.strong.StrongDribbleProfileBuilder;
import util.rocket_league.io.input.DataPacket;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.State;

import javax.xml.ws.Holder;


public class GameStarted extends State<DataPacket, ControlsOutput> {
    public final StrongDribble strongDribble;
    public final Holder<Vector3> offset;

    public GameStarted() {
        this.offset = new Holder<>(new Vector3());
        this.strongDribble = new StrongDribble(new StrongDribbleProfileBuilder()
                .withOffset(() -> offset.value)
                .withMinimumBoostAmount(() -> 100d)
                .build());
    }

    @Override
    public void start(DataPacket input) {

    }

    @Override
    public ControlsOutput exec(DataPacket input) {
        offset.value = input.car.position.scaledToMagnitude(20);
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
