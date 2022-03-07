package util.rocket_league.controllers.jump_sequence;

import util.data_structure.tupple.Tuple2;
import util.rocket_league.controllers.Finishable;
import util.rocket_league.controllers.jump_sequence.states.FirstJump;
import util.rocket_league.controllers.jump_sequence.states.WaitForWheelContact;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.input.DataPacket;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.Behaviour;
import util.state_machine.StateMachine;

// TODO: add a very similar class than this, but it lets us delay the second jump to when we raise a flag or something

public class JumpProfileController implements Behaviour<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput>, Finishable {

    private final StateMachine<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput> stateMachine;
    private boolean isFinished;

    public JumpProfileController(final DataPacket input, final JumpProfile jumpProfile) {
        if(input.car.hasWheelContact) {
            this.stateMachine = new StateMachine<>(new FirstJump(this, input.car, jumpProfile));
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