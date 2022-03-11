package util.rocket_league.controllers.jump.jump_sequence;

import util.data_structure.tupple.Tuple2;
import util.rocket_league.controllers.jump.jump_sequence.states.Finished;
import util.rocket_league.controllers.jump.jump_sequence.states.WaitForWheelContact;
import util.state_machine.Finishable;
import util.rocket_league.controllers.jump.jump_sequence.states.FirstJump;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.Behaviour;
import util.state_machine.Startable;
import util.state_machine.StateMachine;

// TODO: add the possibility to delay the second jump by adding a functional in the jump profile that returns true when it's time to execute it

public class JumpController implements Behaviour<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput>,
        Startable<Tuple2<ExtendedCarData, ControlsOutput>>, Finishable {

    private StateMachine<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput> stateMachine;
    private final JumpProfile jumpProfile;
    private boolean hasExecutedAtLeastOnce;
    private boolean isFinished;

    public JumpController(final JumpProfile jumpProfile) {
        this.stateMachine = new StateMachine<>(new WaitForWheelContact(this, jumpProfile));
        this.jumpProfile = jumpProfile;
        this.hasExecutedAtLeastOnce = false;
        this.isFinished = false;
    }

    @Override
    public void start(Tuple2<ExtendedCarData, ControlsOutput> io) {
        // skip the execution of WaitForWheelContact if we already have contact
        if(io.value1.hasWheelContact) {
            if(jumpProfile.initialImpulse > 0) {
                this.stateMachine = new StateMachine<>(new FirstJump(this, jumpProfile));
            }
            else {
                this.stateMachine = new StateMachine<>(new Finished(this));
            }
        }
    }

    @Override
    public ControlsOutput exec(Tuple2<ExtendedCarData, ControlsOutput> io) {
        // rigged code to execute stuff only once and still make sure we have access to a fresh CarData object
        if(!hasExecutedAtLeastOnce) {
            this.start(io);
            hasExecutedAtLeastOnce = true;
        }
        return stateMachine.exec(io);
    }

    void setIsFinished() {
        this.isFinished = true;
    }

    @Override
    public boolean isFinished() {
        return this.isFinished;
    }
}
