package util.rocket_league.controllers.jump_sequence;

import util.data_structure.tupple.Tuple2;
import util.rocket_league.controllers.Finishable;
import util.rocket_league.controllers.jump_sequence.states.FirstJump;
import util.rocket_league.controllers.jump_sequence.states.WaitForWheelContact;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.Behaviour;
import util.state_machine.StateMachine;

public class JumpProfileController implements Behaviour<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput>, Finishable {

    private final StateMachine<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput> stateMachine;
    private boolean isFinished;

    public JumpProfileController(final ExtendedCarData extendedCarData, final JumpProfile jumpProfile) {
        if(extendedCarData.hasWheelContact) {
            this.stateMachine = new StateMachine<>(new FirstJump(this, extendedCarData, jumpProfile));
        }
        else {
            this.stateMachine = new StateMachine<>(new WaitForWheelContact(this, jumpProfile));
        }
        this.isFinished = false;
    }

    @Override
    public ControlsOutput exec(Tuple2<ExtendedCarData, ControlsOutput> io) {
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
