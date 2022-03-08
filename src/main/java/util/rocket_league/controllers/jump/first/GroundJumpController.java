package util.rocket_league.controllers.jump.first;

import util.data_structure.tupple.Tuple2;
import util.rocket_league.Constants;
import util.state_machine.Finishable;
import util.rocket_league.controllers.jump.first.states.InitialJump;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.Behaviour;
import util.state_machine.StateMachine;

public class GroundJumpController implements Behaviour<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput>, Finishable {

    // source: https://youtu.be/Y9o8ZPEwwK8?t=472
    private static final int JUMP_IMPULSE_FOR_ONE_SECOND = 1460;
    static final double INITIAL_JUMP_IMPULSE = 300.3333333333; // 292 - 16 + 24.3333333333
    private static final int MAX_JUMP_IMPULSE = 568; // 276 + 292

    final double desiredImpulse;
    double accumulatedImpulse;

    private boolean isFinished;

    final StateMachine<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput> stateMachine;

    public GroundJumpController(final ExtendedCarData extendedCarData, double desiredImpulse) {
        if(!extendedCarData.hasWheelContact) throw new GroundJumpWithoutWheelContactException();
        if(desiredImpulse > MAX_JUMP_IMPULSE) {
            desiredImpulse = MAX_JUMP_IMPULSE;
        }
        if(desiredImpulse < INITIAL_JUMP_IMPULSE) {
            desiredImpulse = INITIAL_JUMP_IMPULSE;
        }
        this.desiredImpulse = desiredImpulse;
        this.accumulatedImpulse = 0;
        this.isFinished = false;

        stateMachine = new StateMachine<>(new InitialJump(this));
    }

    @Override
    public ControlsOutput exec(Tuple2<ExtendedCarData, ControlsOutput> io) {
        return stateMachine.exec(io);
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    void setIsFinished(final boolean isFinished) {
        this.isFinished = isFinished;
    }

    void appliedHoldingImpulseForOneFrame() {
        accumulatedImpulse += JUMP_IMPULSE_FOR_ONE_SECOND * Constants.BOT_REFRESH_TIME_PERIOD;
    }

    boolean needsHoldingState() {
        return accumulatedImpulse < desiredImpulse;
    }
}
