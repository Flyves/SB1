package util.rocket_league.controllers.jump.second.states;

import util.data_structure.tupple.Tuple2;
import util.rocket_league.controllers.jump.second.SecondJumpController;
import util.rocket_league.controllers.jump.second.SecondJumpLossException;
import util.rocket_league.controllers.jump.second.SecondJumpWithWheelContactException;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.State;

public abstract class BaseState extends State<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput> {
    private final SecondJumpController secondJumpController;
    private int frameCount;

    public BaseState(final SecondJumpController secondJumpController) {
        this.secondJumpController = secondJumpController;
        this.frameCount = 0;
    }

    @Override
    public State<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput> next(Tuple2<ExtendedCarData, ControlsOutput> io) {
        frameCount++;
        if(io.value1.hasWheelContact) {
            return new Finished(secondJumpController);
        }
        return this;
    }

    public static void validateCanSecondJump(final ExtendedCarData car) {
        if(car.hasWheelContact) throw new SecondJumpWithWheelContactException();
        if(!car.hasSecondJump) throw new SecondJumpLossException();
    }

    public int frameCount() {
        return frameCount;
    }
}
