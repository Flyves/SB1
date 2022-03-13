package util.rocket_league.controllers.ground.navigation.path_generator;

public class NoSuchPlayfieldCollisionException extends RuntimeException {
    public NoSuchPlayfieldCollisionException() {
        super("Couldn't find any colliding triangle!");
    }
}
