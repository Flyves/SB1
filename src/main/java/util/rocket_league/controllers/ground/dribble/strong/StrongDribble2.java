package util.rocket_league.controllers.ground.dribble.strong;

import util.data_structure.tupple.Tuple2;
import util.data_structure.tupple.Tuple3;
import util.math.vector.Ray3;
import util.math.vector.Vector3;
import util.rocket_league.controllers.ground.navigation.cruise.CruiseController;
import util.rocket_league.controllers.ground.navigation.cruise.CruiseProfileBuilder;
import util.rocket_league.controllers.ground.navigation.waypoint.single_destination.DestinationNavigator;
import util.rocket_league.controllers.ground.navigation.waypoint.single_destination.DestinationNavigatorProfileBuilder;
import util.rocket_league.dynamic_objects.ball.BallData;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.Behaviour;

import javax.xml.ws.Holder;

public class StrongDribble2 implements Behaviour<Tuple3<ExtendedCarData, BallData, ControlsOutput>, ControlsOutput> {
    private static final double ANCHOR_DISTANCE = 180;

    private final StrongDribbleProfile strongDribbleProfile;
    private final CruiseController cruiseController;
    private final Holder<Ray3> destination;

    public StrongDribble2(final StrongDribbleProfile strongDribbleProfile) {
        this.destination = new Holder<>();
        this.strongDribbleProfile = strongDribbleProfile;
        this.cruiseController = new CruiseController(new CruiseProfileBuilder()
                .withMinimumBoostAmount(strongDribbleProfile.minimumBoostAmount)
                .withDestination(() -> destination.value)
                .build());
    }

    @Override
    public ControlsOutput exec(final Tuple3<ExtendedCarData, BallData, ControlsOutput> io) {
        updateDestination(io);
        return cruiseController.exec(new Tuple2<>(io.value1, io.value3));
    }

    private void updateDestination(final Tuple3<ExtendedCarData, BallData, ControlsOutput> io) {
        destination.value = new Ray3(
                io.value2.position.scaled(1, 1, 0),
                io.value2.velocity.scaled(1, 1, 0).scaledToMagnitude(ANCHOR_DISTANCE));
    }
}
