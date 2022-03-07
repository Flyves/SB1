package util.rocket_league.controllers.jump.second.states;

import util.data_structure.tupple.Tuple2;
import util.rocket_league.controllers.jump.second.SecondJumpController;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;

public class CancelState extends BaseState {
    public CancelState(final SecondJumpController secondJumpController) {
        super(secondJumpController);
    }

    @Override
    public ControlsOutput exec(Tuple2<ExtendedCarData, ControlsOutput> io) {
        if(frameCount() == 0) {
            io.value2.isJumping = true;
            io.value2.pitch = 0;
            io.value2.yaw = -1;
            io.value2.roll = 1;
        }
        if(frameCount() == 1) {
            io.value2.pitch = 0;
            io.value2.yaw = 1;
            io.value2.roll = -1;
        }

        return io.value2;
    }
}
