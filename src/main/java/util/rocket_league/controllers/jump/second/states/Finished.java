package util.rocket_league.controllers.jump.second.states;

import util.data_structure.tupple.Tuple2;
import util.rocket_league.controllers.jump.second.SecondJumpController;
import util.rocket_league.controllers.jump.second.SecondJumpControllerFinisher;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;

public class Finished extends BaseState {
    private final SecondJumpController secondJumpController;

    public Finished(SecondJumpController secondJumpController) {
        super(secondJumpController);
        this.secondJumpController = secondJumpController;
        // TODO : put the finisher in the exec function? Question mark?
    }

    @Override
    public ControlsOutput exec(final Tuple2<ExtendedCarData, ControlsOutput> io) {
        new SecondJumpControllerFinisher(secondJumpController).finish();
        return new ControlsOutput();
    }
}
