package util.rocket_league.controllers.jump.second;

public class JumpNotImplementedYetException extends RuntimeException {
    public JumpNotImplementedYetException() {
        super("Cannot perform this type of flip! It hasn't been programmed yet!");
    }
}
