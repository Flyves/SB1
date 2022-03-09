package util.rocket_league.controllers.ground.navigation.destination;

public class DestinationNavigatorFinisher {
    private final DestinationNavigator destinationNavigator;

    public DestinationNavigatorFinisher(final DestinationNavigator destinationNavigator) {
        this.destinationNavigator = destinationNavigator;
    }

    public void finish() {
        destinationNavigator.setIsFinished(true);
    }
}
