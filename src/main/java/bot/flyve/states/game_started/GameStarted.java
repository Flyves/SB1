package bot.flyve.states.game_started;

import bot.flyve.states.kickoff.KickoffPosition;
import bot.flyve.states.round_over.RoundOver;
import util.data_structure.tupple.Tuple2;
import util.data_structure.tupple.Tuple3;
import util.math.vector.Ray3;
import util.rocket_league.controllers.ground.dribble.ball_contourer.BallContourner;
import util.rocket_league.controllers.ground.dribble.ball_contourer.BallContournerProfileBuilder;
import util.rocket_league.controllers.ground.dribble.strong.StrongDribble;
import util.rocket_league.controllers.ground.dribble.strong.StrongDribble2;
import util.rocket_league.controllers.ground.dribble.strong.StrongDribbleProfileBuilder;
import util.rocket_league.controllers.ground.navigation.cruise.CruiseController;
import util.rocket_league.controllers.ground.navigation.cruise.CruiseProfileBuilder;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.input.DataPacket;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.State;

import javax.xml.ws.Holder;


public class GameStarted extends State<DataPacket, ControlsOutput> {
    public final StrongDribble2 strongDribble2;
    public final Holder<Ray3> destination;

    public GameStarted() {
        this.destination = new Holder<>(new Ray3());
        this.strongDribble2 = new StrongDribble2(new StrongDribbleProfileBuilder()
                .withMinimumBoostAmount(() -> 100d)
                .build());
    }

    @Override
    public void start(DataPacket input) {

    }

    @Override
    public ControlsOutput exec(DataPacket input) {
        return strongDribble2.exec(new Tuple3<>(input.car, input.ball, new ControlsOutput()));
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
