package util.rocket_league.controllers.ground.navigation.boostpad;

public class UnableToConstructBoostPadPathException extends RuntimeException {
    public UnableToConstructBoostPadPathException() {
        super("Cannot construct a path without source and target boost pads!");
    }
}
