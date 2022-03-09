package util.rocket_league.controllers.ground.navigation.bi_destination;

public class BiDestinationNavigatorFinisher {
    private final BiDestinationNavigator biDestinationNavigator;

    public BiDestinationNavigatorFinisher(final BiDestinationNavigator biDestinationNavigator) {
        this.biDestinationNavigator = biDestinationNavigator;
    }

    public void finish() {
        biDestinationNavigator.setIsFinished(true);
    }

    public void reachFirstDestination() {
        biDestinationNavigator.setHasReachedFirstDestination(true);
    }
}
