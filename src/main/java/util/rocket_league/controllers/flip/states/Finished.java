package util.rocket_league.controllers.flip.states;

import util.data_structure.tupple.Tuple2;
import util.rocket_league.controllers.flip.FlipController;
import util.rocket_league.controllers.flip.FlipControllerFinisher;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;

public class Finished extends BaseState {

    public Finished(FlipController flipController) {
        super(flipController);
        new FlipControllerFinisher(flipController).finish();
    }

    @Override
    public ControlsOutput exec(final Tuple2<ExtendedCarData, ControlsOutput> io) {
        return new ControlsOutput();
    }
}
