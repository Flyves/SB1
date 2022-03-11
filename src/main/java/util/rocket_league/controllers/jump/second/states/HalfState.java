package util.rocket_league.controllers.jump.second.states;

import util.data_structure.tupple.Tuple2;
import util.rocket_league.controllers.jump.second.SecondJumpController;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;

public class HalfState extends BaseState {
    public HalfState(final SecondJumpController secondJumpController) {
        super(secondJumpController);
    }

    @Override
    public ControlsOutput exec(Tuple2<ExtendedCarData, ControlsOutput> io) {
        final ControlsOutput output = new ControlsOutput();
        if(frameCount() == 0) {
            BaseState.validateCanSecondJump(io.value1);
            output.isJumping = true;
            output.pitch = 1;
            output.yaw = 0;
            output.roll = 0;
        }
        if(frameCount() > 13 && frameCount() < 60) {
            output.pitch = -1;
        }
        if(frameCount() > 25 && frameCount() < 60) {
            output.roll = 1;
            output.yaw = -0.4f;
        }
        return output;
    }
}
