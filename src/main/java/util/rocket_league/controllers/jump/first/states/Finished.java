package util.rocket_league.controllers.jump.first.states;

import util.data_structure.tupple.Tuple2;
import util.rocket_league.controllers.jump.first.GroundJumpController;
import util.rocket_league.controllers.jump.first.GroundJumpControllerHandler;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;

public class Finished extends BaseState {
    private final GroundJumpController groundJumpController;
    private int amountOfFramesOfHolding;

    public Finished(GroundJumpController groundJumpController, final int frameCount) {
        super(groundJumpController);

        this.groundJumpController = groundJumpController;
        this.amountOfFramesOfHolding = 0;
        this.setFrameCount(frameCount);
    }

    @Override
    public void start(final Tuple2<ExtendedCarData, ControlsOutput> io) {
        amountOfFramesOfHolding = frameCount();
    }

    @Override
    public ControlsOutput exec(final Tuple2<ExtendedCarData, ControlsOutput> io) {
        if(amountOfFramesOfHolding == 0) {
            new GroundJumpControllerHandler(groundJumpController).finish();
        }
        else if(amountOfFramesOfHolding == 1 && frameCount() > 5) {
            new GroundJumpControllerHandler(groundJumpController).finish();
        }
        else if(amountOfFramesOfHolding == 2 && frameCount() > 3) {
            new GroundJumpControllerHandler(groundJumpController).finish();
        }
        else if(amountOfFramesOfHolding > 2) {
            new GroundJumpControllerHandler(groundJumpController).finish();
        }
        return new ControlsOutput();
    }
}
