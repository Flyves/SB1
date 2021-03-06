package util.rocket_league.controllers.jump.first.states;

import util.data_structure.tupple.Tuple2;
import util.rocket_league.controllers.jump.first.GroundJumpController;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.State;

public abstract class BaseState extends State<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput> {
    private final GroundJumpController groundJumpController;
    private int frameCount;

    public BaseState(final GroundJumpController groundJumpController) {
        this.groundJumpController = groundJumpController;
        this.frameCount = 0;
    }

    @Override
    public State<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput> next(Tuple2<ExtendedCarData, ControlsOutput> io) {
        frameCount++;
        return this;
    }

    public int frameCount() {
        return frameCount;
    }

    public void setFrameCount(final int count) {
        frameCount = count;
    }
}
