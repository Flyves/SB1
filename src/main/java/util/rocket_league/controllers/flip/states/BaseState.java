package util.rocket_league.controllers.flip.states;

import util.data_structure.tupple.Tuple2;
import util.rocket_league.controllers.flip.FlipController;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.State;

public abstract class BaseState extends State<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput> {
    private final FlipController flipController;
    private int frameCount;

    public BaseState(final FlipController flipController) {
        this.flipController = flipController;
        this.frameCount = 0;
    }

    @Override
    public State<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput> next(Tuple2<ExtendedCarData, ControlsOutput> io) {
        frameCount++;
        if(io.value1.hasWheelContact) {
            return new Finished(flipController);
        }
        return this;
    }

    public int frameCount() {
        return frameCount;
    }
}
