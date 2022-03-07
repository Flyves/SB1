package util.rocket_league.controllers.jump.second;

public class SecondJumpControllerFinisher {

    private final SecondJumpController secondJumpController;

    public SecondJumpControllerFinisher(final SecondJumpController secondJumpController) {
        this.secondJumpController = secondJumpController;
    }

    public void finish() {
        secondJumpController.setIsFinished(true);
    }
}
