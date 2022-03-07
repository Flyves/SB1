package util.rocket_league.controllers.jump_sequence.states;

import util.data_structure.tupple.Tuple2;
import util.rocket_league.controllers.flip.FlipController;
import util.rocket_league.controllers.jump_sequence.JumpProfile;
import util.rocket_league.controllers.jump_sequence.JumpProfileController;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.State;

public class SecondJump extends State<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput> {

    private final JumpProfileController jumpProfileController;
    private final FlipController flipController;

    public SecondJump(
            final JumpProfileController jumpProfileController,
            final ExtendedCarData extendedCarData,
            final JumpProfile jumpProfile) {
        this.jumpProfileController = jumpProfileController;
        this.flipController = new FlipController(extendedCarData, jumpProfile.flipType, jumpProfile.flipDirection);

    }

    @Override
    public ControlsOutput exec(Tuple2<ExtendedCarData, ControlsOutput> io) {
        return flipController.exec(io);
    }

    @Override
    public State<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput> next(Tuple2<ExtendedCarData, ControlsOutput> input) {
        if(flipController.isFinished()) {
            return new Finished(jumpProfileController);
        }
        return this;
    }
}
