package util.rocket_league.controllers.flip;

public class SecondJumpWithWheelContactException extends RuntimeException {
    public SecondJumpWithWheelContactException() {
        super("It's impossible to flip or 2nd jump on the ground!");
    }
}
