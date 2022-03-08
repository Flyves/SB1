package util.rocket_league.controllers.ground;

public class GroundDrivingInMidAirException extends RuntimeException {
    public GroundDrivingInMidAirException() {
        super("Driving in mid air is not allowed!");
    }
}
