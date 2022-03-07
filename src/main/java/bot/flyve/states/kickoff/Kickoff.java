package bot.flyve.states.kickoff;

import bot.flyve.states.kickoff_done.KickoffDone;
import util.data_structure.tupple.Tuple2;
import util.math.vector.Vector2;
import util.rocket_league.controllers.flip.FlipType;
import util.rocket_league.controllers.jump_sequence.JumpProfile;
import util.rocket_league.controllers.jump_sequence.JumpProfileBuilder;
import util.rocket_league.controllers.jump_sequence.JumpProfileController;
import util.rocket_league.io.input.DataPacket;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.State;

public class Kickoff extends State<DataPacket, ControlsOutput> {

    private boolean hasToGoToKickoff;
    private JumpProfileController jumpProfileController;

    @Override
    public void start(DataPacket input) {
        hasToGoToKickoff = KickoffPosition.hasToGoToKickoff(input);
        final JumpProfile jumpProfile = new JumpProfileBuilder()
                .withFlipType(FlipType.CANCEL)
                .withInitialImpulse(350)
                .withFlipDirection(new Vector2(1, 0))
                .build();
        jumpProfileController = new JumpProfileController(input.car, jumpProfile);
    }

    @Override
    public ControlsOutput exec(DataPacket input) {
        return jumpProfileController.exec(new Tuple2<>(input.car, new ControlsOutput()));
    }

    @Override
    public State<DataPacket, ControlsOutput> next(DataPacket input) {
        if(isKickoffDone(input)) {
            return new KickoffDone();
        }
        return this;
    }

    private boolean isKickoffDone(DataPacket input) {
        return !hasToGoToKickoff || KickoffPosition.kickoffFinished(input);
    }
}
