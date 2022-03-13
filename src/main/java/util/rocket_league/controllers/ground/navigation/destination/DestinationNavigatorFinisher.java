package util.rocket_league.controllers.ground.navigation.destination;

public class DestinationNavigatorFinisher<T> {
    private final DestinationNavigator<T> destinationNavigator;

    public DestinationNavigatorFinisher(final DestinationNavigator<T> destinationNavigator) {
        this.destinationNavigator = destinationNavigator;
    }

    public void finish() {
        destinationNavigator.setIsFinished(true);
    }
}
