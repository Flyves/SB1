package util.rocket_league.controllers.flip.states;

import util.data_structure.tupple.Tuple2;
import util.rocket_league.controllers.flip.FlipController;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;

public class SpeedState extends BaseState {
    public SpeedState(final FlipController flipController) {
        super(flipController);
    }

    @Override
    public ControlsOutput exec(Tuple2<ExtendedCarData, ControlsOutput> io) {
        return io.value2;
    }
}
