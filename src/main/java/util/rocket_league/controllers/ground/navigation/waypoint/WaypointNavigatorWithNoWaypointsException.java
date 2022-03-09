package util.rocket_league.controllers.ground.navigation.waypoint;

public class WaypointNavigatorWithNoWaypointsException extends RuntimeException {
    public WaypointNavigatorWithNoWaypointsException() {
        super("It's impossible to navigate without waypoints!");
    }
}
