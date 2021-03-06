package util.rocket_league.controllers.jump.first.states;

import util.data_structure.tupple.Tuple2;
import util.rocket_league.controllers.jump.first.GroundJumpController;
import util.rocket_league.controllers.jump.first.GroundJumpControllerHandler;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.State;

public class HoldJumpButton extends BaseState {
    private final GroundJumpController groundJumpController;

    public HoldJumpButton(GroundJumpController groundJumpController, final int frameCount) {
        super(groundJumpController);
        this.groundJumpController = groundJumpController;
        this.setFrameCount(frameCount);
    }

    @Override
    public ControlsOutput exec(Tuple2<ExtendedCarData, ControlsOutput> io) {
        io.value2.isJumping = true;
        return io.value2;
    }

    @Override
    public State<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput> next(Tuple2<ExtendedCarData, ControlsOutput> io) {
        super.next(io);
        final GroundJumpControllerHandler groundJumpControllerHandler = new GroundJumpControllerHandler(groundJumpController);
        groundJumpControllerHandler.applyHoldingImpulseForOneFrame();
        if(groundJumpControllerHandler.getAppliedImpulse() >= groundJumpControllerHandler.getDesiredImpulse()) {
            return new Finished(groundJumpController, frameCount());
        }
        return this;
    }
}
