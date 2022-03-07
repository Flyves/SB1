package util.rocket_league.controllers.flip;

public class FlipControllerFinisher {

    private final FlipController flipController;

    public FlipControllerFinisher(final FlipController flipController) {
        this.flipController = flipController;
    }

    public void finish() {
        flipController.setIsFinished(true);
    }
}
