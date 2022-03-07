package util.rocket_league.controllers.flip.states;

import util.data_structure.tupple.Tuple2;
import util.math.vector.Vector2;
import util.rocket_league.controllers.flip.FlipController;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;

public class CancelState extends BaseState {
    public CancelState(final FlipController flipController) {
        super(flipController);
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
