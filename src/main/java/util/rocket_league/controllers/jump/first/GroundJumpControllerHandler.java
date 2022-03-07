package util.rocket_league.controllers.jump.first;

public class GroundJumpControllerHandler {

    private final GroundJumpController groundJumpController;

    public GroundJumpControllerHandler(final GroundJumpController groundJumpController) {
        this.groundJumpController = groundJumpController;
    }

    public void applyHoldingImpulseForOneFrame() {
        groundJumpController.appliedHoldingImpulseForOneFrame();
    }

    public boolean getNeedsHoldingState() {
        return groundJumpController.needsHoldingState();
    }

    public void finish() {
        groundJumpController.setIsFinished(true);
    }

    public double getAppliedImpulse() {
        return groundJumpController.accumulatedImpulse;
    }

    public double getDesiredImpulse() {
        return groundJumpController.desiredImpulse;
    }

    public void applyInitialImpulse() {
        groundJumpController.accumulatedImpulse = GroundJumpController.INITIAL_JUMP_IMPULSE;
    }
}
