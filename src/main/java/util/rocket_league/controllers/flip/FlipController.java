package util.rocket_league.controllers.flip;

import util.data_structure.tupple.Tuple2;
import util.math.vector.Vector2;
import util.rocket_league.controllers.Finishable;
import util.rocket_league.controllers.flip.states.*;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.Behaviour;
import util.state_machine.StateMachine;

public class FlipController implements Behaviour<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput>, Finishable {

    public static final Vector2 DEFAULT_FLIP_DIRECTION = new Vector2(0, 1);

    private final StateMachine<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput> stateMachine;
    private boolean isFinished;

    public FlipController(final ExtendedCarData extendedCarData, final FlipType flipType) {
        this(extendedCarData, flipType, DEFAULT_FLIP_DIRECTION);
    }

    public FlipController(final ExtendedCarData extendedCarData, final FlipType flipType, final Vector2 flipOrientation) {
        if(extendedCarData.hasWheelContact) throw new SecondJumpWithWheelContactException();
        switch (flipType) {
            case NORMAL: this.stateMachine = new StateMachine<>(new NormalState(this, flipOrientation));
            break;
            case HALF: this.stateMachine = new StateMachine<>(new HalfState(this));
            break;
            case CANCEL: this.stateMachine = new StateMachine<>(new CancelState(this));
            break;
            case PARTIAL_CANCEL: this.stateMachine = new StateMachine<>(new PartialCancelState(this, flipOrientation));
            break;
            case WAVE_DASH: this.stateMachine = new StateMachine<>(new WaveDashState(this, flipOrientation));
            break;
            case DOUBLE_WAVE_DASH: this.stateMachine = new StateMachine<>(new DoubleWaveDashState(this, flipOrientation));
            break;
            case SECOND_JUMP: this.stateMachine = new StateMachine<>(new SecondJump(this));
            break;
            default: this.stateMachine = new StateMachine<>(new Finished(this));
            break;
        }
        this.isFinished = false;
    }

    @Override
    public ControlsOutput exec(final Tuple2<ExtendedCarData, ControlsOutput> io) {
        return stateMachine.exec(io);
    }

    void setIsFinished(final boolean isFinished) {
        this.isFinished = isFinished;
    }

    @Override
    public boolean isFinished() {
        return this.isFinished;
    }
}
