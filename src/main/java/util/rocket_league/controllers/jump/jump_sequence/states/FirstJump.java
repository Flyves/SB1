package util.rocket_league.controllers.jump.jump_sequence.states;

import util.data_structure.tupple.Tuple2;
import util.rocket_league.controllers.jump.first.GroundJumpController;
import util.rocket_league.controllers.jump.jump_sequence.JumpProfile;
import util.rocket_league.controllers.jump.jump_sequence.JumpController;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.State;

public class FirstJump extends State<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput> {

    private GroundJumpController groundJumpController;
    private final JumpController jumpController;
    private final JumpProfile jumpProfile;

    public FirstJump(
            final JumpController jumpController,
            final ExtendedCarData extendedCarData,
            final JumpProfile jumpProfile) {
        this.jumpController = jumpController;
        this.groundJumpController = new GroundJumpController(extendedCarData, jumpProfile.initialImpulse);
        this.jumpProfile = jumpProfile;
    }

    @Override
    public ControlsOutput exec(Tuple2<ExtendedCarData, ControlsOutput> io) {
        return groundJumpController.exec(io);
    }

    @Override
    public State<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput> next(Tuple2<ExtendedCarData, ControlsOutput> io) {
        if(groundJumpController.isFinished()) {
            try {
                return new SecondJump(jumpController, io.value1, jumpProfile);
            }
            catch (final Exception e) {
                try {
                    return new FirstJump(jumpController, io.value1, jumpProfile);
                }
                catch (final Exception e2) {
                    return new Finished(jumpController);
                }
            }
        }
        return this;
    }
}
