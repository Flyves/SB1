package util.rocket_league.controllers.jump.jump_sequence;

public class JumpProfileControllerFinisher {

    private final JumpProfileController jumpProfileController;

    public JumpProfileControllerFinisher(final JumpProfileController jumpProfileController) {
        this.jumpProfileController = jumpProfileController;
    }

    public void finish() {
        jumpProfileController.setIsFinished();
    }
}
