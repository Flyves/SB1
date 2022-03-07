package util.rocket_league.controllers.jump_sequence.states;

import util.data_structure.tupple.Tuple2;
import util.rocket_league.controllers.jump.GroundJumpController;
import util.rocket_league.controllers.jump_sequence.JumpProfile;
import util.rocket_league.controllers.jump_sequence.JumpProfileController;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.State;

public class FirstJump extends State<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput> {

    private GroundJumpController groundJumpController;
    private final JumpProfileController jumpProfileController;
    private final JumpProfile jumpProfile;

    public FirstJump(
            final JumpProfileController jumpProfileController,
            final ExtendedCarData extendedCarData,
            final JumpProfile jumpProfile) {
        this.jumpProfileController = jumpProfileController;
        this.groundJumpController = new GroundJumpController(extendedCarData, jumpProfile.initialImpulse);
        this.jumpProfile = jumpProfile;
    }

    @Override
    public ControlsOutput exec(Tuple2<ExtendedCarData, ControlsOutput> io) {
        return groundJumpController.exec(io);
    }

    @Override
    public State<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput> next(Tuple2<ExtendedCarData, ControlsOutput> io) {
        if(groundJumpController.isFinished()) {
            return new SecondJump(jumpProfileController, io.value1, jumpProfile);
        }
        return this;
    }
}
