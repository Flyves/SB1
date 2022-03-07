package util.rocket_league.controllers.jump.second;

public class SecondJumpLossException extends RuntimeException {
    public SecondJumpLossException() {
        super("Cannot perform 2nd jump if it has been lost!");
    }
}
