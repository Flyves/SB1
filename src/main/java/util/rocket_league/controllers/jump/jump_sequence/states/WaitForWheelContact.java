package util.rocket_league.controllers.jump.jump_sequence.states;

import util.data_structure.tupple.Tuple2;
import util.rocket_league.controllers.jump.jump_sequence.JumpProfile;
import util.rocket_league.controllers.jump.jump_sequence.JumpProfileController;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.State;

public class WaitForWheelContact extends State<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput> {

    private final JumpProfileController jumpProfileController;
    private final JumpProfile jumpProfile;

    public WaitForWheelContact(final JumpProfileController jumpProfileController, final JumpProfile jumpProfile) {
        this.jumpProfileController = jumpProfileController;
        this.jumpProfile = jumpProfile;
    }

    @Override
    public ControlsOutput exec(Tuple2<ExtendedCarData, ControlsOutput> io) {
        return io.value2;
    }

    @Override
    public State<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput> next(Tuple2<ExtendedCarData, ControlsOutput> io) {
        if(io.value1.hasWheelContact) {
            return new FirstJump(jumpProfileController, io.value1, jumpProfile);
        }
        return this;
    }
}
