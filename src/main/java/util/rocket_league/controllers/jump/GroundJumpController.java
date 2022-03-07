package util.rocket_league.controllers.jump;

import util.data_structure.tupple.Tuple2;
import util.rocket_league.Constants;
import util.rocket_league.controllers.Finishable;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.Behaviour;

public class GroundJumpController implements Behaviour<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput>, Finishable {

    private static final int JUMP_IMPULSE_FOR_ONE_SECOND = 1460;
    private static final int INITIAL_JUMP_IMPULSE = 292;
    private static final int MAX_JUMP_IMPULSE = 584;

    private final double desiredImpulse;
    private double accumulatedImpulse;

    private int finishedCounter;

    public GroundJumpController(final ExtendedCarData extendedCarData, double desiredImpulse) {
        if(!extendedCarData.hasWheelContact) throw new GroundJumpWithoutWheelContactException();
        if(desiredImpulse > MAX_JUMP_IMPULSE) {
            desiredImpulse = MAX_JUMP_IMPULSE;
        }
        if(desiredImpulse <= INITIAL_JUMP_IMPULSE) {
            desiredImpulse = INITIAL_JUMP_IMPULSE + JUMP_IMPULSE_FOR_ONE_SECOND * Constants.BOT_REFRESH_TIME_PERIOD;
        }
        this.desiredImpulse = desiredImpulse;
        this.accumulatedImpulse = 0;
        this.finishedCounter = 0;
    }

    @Override
    public ControlsOutput exec(Tuple2<ExtendedCarData, ControlsOutput> io) {
        if(accumulatedImpulse < desiredImpulse) {
            if(accumulatedImpulse < INITIAL_JUMP_IMPULSE) {
                io.value2.isJumping = true;
                accumulatedImpulse = INITIAL_JUMP_IMPULSE;
                return io.value2;
            }
            io.value2.isJumping = true;
            accumulatedImpulse += JUMP_IMPULSE_FOR_ONE_SECOND * Constants.BOT_REFRESH_TIME_PERIOD;
        }
        else {
            finishedCounter++;
        }
        return io.value2;
    }

    @Override
    public boolean isFinished() {
        return finishedCounter > 1;
    }
}
