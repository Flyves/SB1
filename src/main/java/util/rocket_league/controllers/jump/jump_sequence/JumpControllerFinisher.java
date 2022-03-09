package util.rocket_league.controllers.jump.jump_sequence;

public class JumpControllerFinisher {

    private final JumpController jumpController;

    public JumpControllerFinisher(final JumpController jumpController) {
        this.jumpController = jumpController;
    }

    public void finish() {
        jumpController.setIsFinished();
    }
}
