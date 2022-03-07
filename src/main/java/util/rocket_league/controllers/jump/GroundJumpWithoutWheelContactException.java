package util.rocket_league.controllers.jump;

public class GroundJumpWithoutWheelContactException extends RuntimeException {
    public GroundJumpWithoutWheelContactException() {
        super("It's impossible to 1st jump in the air!");
    }
}
