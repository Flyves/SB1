package util.rocket_league.controllers.flip.states;

import util.data_structure.tupple.Tuple2;
import util.math.vector.Vector2;
import util.rocket_league.controllers.flip.FlipController;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;

public class NormalState extends BaseState {
    private final Vector2 flipOrientation;

    public NormalState(final FlipController flipController, final Vector2 flipOrientation) {
        super(flipController);
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

        return io.value2;
    }
}
