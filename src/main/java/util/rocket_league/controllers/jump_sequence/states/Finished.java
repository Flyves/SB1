package util.rocket_league.controllers.jump_sequence.states;

import util.data_structure.tupple.Tuple2;
import util.rocket_league.controllers.Finishable;
import util.rocket_league.controllers.jump_sequence.JumpProfileController;
import util.rocket_league.controllers.jump_sequence.JumpProfileControllerFinisher;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.State;

public class Finished extends State<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput> {

    public Finished(final JumpProfileController jumpProfileController) {
        new JumpProfileControllerFinisher(jumpProfileController).finish();
    }

    @Override
    public ControlsOutput exec(Tuple2<ExtendedCarData, ControlsOutput> io) {
        return io.value2;
    }

    @Override
    public State<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput> next(Tuple2<ExtendedCarData, ControlsOutput> input) {
        return this;
    }
}
