package util.rocket_league.controllers.jump.jump_sequence.states;

import util.data_structure.tupple.Tuple2;
import util.rocket_league.controllers.jump.second.SecondJumpController;
import util.rocket_league.controllers.jump.jump_sequence.JumpProfile;
import util.rocket_league.controllers.jump.jump_sequence.JumpController;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.State;

public class SecondJump extends State<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput> {

    private final JumpController jumpController;
    private final SecondJumpController secondJumpController;

    public SecondJump(
            final JumpController jumpController,
            final ExtendedCarData extendedCarData,
            final JumpProfile jumpProfile) {
        this.jumpController = jumpController;
        this.secondJumpController = new SecondJumpController(extendedCarData, jumpProfile.secondJumpType, jumpProfile.flipDirection);

    }

    @Override
    public ControlsOutput exec(Tuple2<ExtendedCarData, ControlsOutput> io) {
        return secondJumpController.exec(io);
    }

    @Override
    public State<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput> next(Tuple2<ExtendedCarData, ControlsOutput> input) {
        if(secondJumpController.isFinished()) {
            return new Finished(jumpController);
        }
        return this;
    }
}
