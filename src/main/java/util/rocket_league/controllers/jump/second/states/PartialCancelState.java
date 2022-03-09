package util.rocket_league.controllers.jump.second.states;

import util.data_structure.tupple.Tuple2;
import util.math.vector.Vector2;
import util.rocket_league.controllers.jump.second.SecondJumpController;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;

public class PartialCancelState extends BaseState {
    private final Vector2 flipOrientation;

    public PartialCancelState(final SecondJumpController secondJumpController, final Vector2 flipOrientation) {
        super(secondJumpController);
        this.flipOrientation = flipOrientation;
    }

    @Override
    public ControlsOutput exec(Tuple2<ExtendedCarData, ControlsOutput> io) {
        if(frameCount() == 0) {
            io.value2.isJumping = true;
            io.value2.pitch = (float)-flipOrientation.y;
            io.value2.yaw = 0;
            io.value2.roll = (float)flipOrientation.x;
        }
        else if(frameCount() > 0 && frameCount() < 50) {
            io.value2.pitch = (float)flipOrientation.y;
            io.value2.roll = (float)flipOrientation.x;
        }
        return io.value2;
    }
}