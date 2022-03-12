package bot.flyve.states.game_started;

import bot.flyve.states.kickoff.Kickoff;
import bot.flyve.states.kickoff.KickoffPosition;
import util.data_structure.tupple.Tuple3;
import util.rocket_league.controllers.ground.steer.orientation.OrientationProfileBuilder;
import util.rocket_league.controllers.ground.steer.orientation.OrientationController;
import util.rocket_league.controllers.ground.throttle.SpeedController;
import util.rocket_league.io.input.DataPacket;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.State;

public class GameStarted extends State<DataPacket, ControlsOutput> {
    private OrientationController orientationController;
    private SpeedController speedController;

    public GameStarted() {
        this.orientationController = new OrientationController(new OrientationProfileBuilder()
                .withMaxAngularVelocity(v -> 3.05)
                .build());
        this.speedController = new SpeedController();
    }

    @Override
    public void start(DataPacket input) {

    }

    @Override
    public ControlsOutput exec(DataPacket input) {
        final ControlsOutput output = new ControlsOutput();
        try {
            orientationController.exec(new Tuple3<>(input.car, output, input.ball.position));
            return speedController.exec(new Tuple3<>(input.car, output, 2300.0));
        }
        catch(final Exception ignored) {

        }

        return output;
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
