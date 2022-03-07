package util.rocket_league.controllers.jump.second.states;

import util.data_structure.tupple.Tuple2;
import util.math.vector.Vector2;
import util.rocket_league.controllers.jump.second.SecondJumpController;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;

public class WaveDashState extends BaseState {
    private final Vector2 flipOrientation;

    public WaveDashState(final SecondJumpController secondJumpController, final Vector2 flipOrientation) {
        super(secondJumpController);
        this.flipOrientation = flipOrientation;
    }

    @Override
    public ControlsOutput exec(Tuple2<ExtendedCarData, ControlsOutput> io) {
        return io.value2;
    }
}
