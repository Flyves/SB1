package util.rocket_league.controllers.flip.states;

import util.data_structure.tupple.Tuple2;
import util.math.vector.Vector2;
import util.rocket_league.controllers.flip.FlipController;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;

public class DoubleWaveDashState extends BaseState {
    private final Vector2 flipOrientation;

    public DoubleWaveDashState(final FlipController flipController, final Vector2 flipOrientation) {
        super(flipController);
        this.flipOrientation = flipOrientation;
    }

    @Override
    public ControlsOutput exec(Tuple2<ExtendedCarData, ControlsOutput> io) {
        return io.value2;
    }
}
