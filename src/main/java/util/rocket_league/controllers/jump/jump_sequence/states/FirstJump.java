package util.rocket_league.controllers.jump.jump_sequence.states;

import util.data_structure.tupple.Tuple2;
import util.rocket_league.controllers.jump.first.GroundJumpController;
import util.rocket_league.controllers.jump.first.GroundJumpControllerHandler;
import util.rocket_league.controllers.jump.jump_sequence.JumpProfile;
import util.rocket_league.controllers.jump.jump_sequence.JumpController;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.State;

public class FirstJump extends State<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput> {

    private GroundJumpController groundJumpController;
    private final JumpController jumpController;
    private final JumpProfile jumpProfile;

    public FirstJump(final JumpController jumpController, final JumpProfile jumpProfile) {
        this.jumpController = jumpController;
        this.groundJumpController = new GroundJumpController(jumpProfile.initialImpulse);
        this.jumpProfile = jumpProfile;
    }

    @Override
    public ControlsOutput exec(Tuple2<ExtendedCarData, ControlsOutput> io) {
        try {
            return groundJumpController.exec(io);
        }
        catch (final Exception e) {
            final GroundJumpControllerHandler handler = new GroundJumpControllerHandler(groundJumpController);
            handler.finish();
            return io.value2;
        }
    }

    @Override
    public State<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput> next(Tuple2<ExtendedCarData, ControlsOutput> io) {
        if(groundJumpController.isFinished()) {
            return new SecondJump(jumpController, jumpProfile);
        }
        return this;
    }
}
