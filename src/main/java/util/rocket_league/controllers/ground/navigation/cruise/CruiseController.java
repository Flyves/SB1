package util.rocket_league.controllers.ground.navigation.cruise;

import util.data_structure.tupple.Tuple2;
import util.math.vector.Ray3;
import util.math.vector.Vector3;
import util.rocket_league.Constants;
import util.rocket_league.controllers.ground.navigation.waypoint.single_destination.DestinationNavigator;
import util.rocket_league.controllers.ground.navigation.waypoint.single_destination.DestinationNavigatorProfileBuilder;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.Behaviour;

import javax.xml.ws.Holder;

public class CruiseController implements Behaviour<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput> {
    private static final double SPEED_CONVERGENCE_RATE = 10;

    private final CruiseProfile cruiseProfile;
    private Ray3 previousDestination;
    private Ray3 destinationVelocity;
    private final Holder<Double> desiredSpeed;
    private final Holder<Vector3> anchorPosition;
    private final DestinationNavigator<Holder<Vector3>> destinationNavigator;

    public CruiseController(final CruiseProfile cruiseProfile) {
        this.cruiseProfile = cruiseProfile;
        this.previousDestination = new Ray3();
        this.destinationVelocity = new Ray3();
        this.desiredSpeed = new Holder<>(0d);
        this.anchorPosition = new Holder<>();
        this.destinationNavigator = new DestinationNavigator<>(new DestinationNavigatorProfileBuilder<Holder<Vector3>>()
                .withDestinationMapper(h -> h.value)
                .withTargetSpeed(() -> desiredSpeed.value)
                .withCollision((c, h) -> false)
                .withAngularVelocity(v -> 3.05)
                .withDestination(anchorPosition)
                .withMinimumBoostAmount(cruiseProfile.minimumBoostAmount)
                .build());
    }

    @Override
    public ControlsOutput exec(final Tuple2<ExtendedCarData, ControlsOutput> io) {
        final Ray3 destination = cruiseProfile.destination.get();
        updateDesiredSpeed(io, destination);
        updateDestinationInfo(destination);
        return destinationNavigator.exec(io);
    }

    private void updateDesiredSpeed(final Tuple2<ExtendedCarData, ControlsOutput> io, final Ray3 destination) {
        final double desiredDisplacement = destination.offset.minus(io.value1.position).dotProduct(io.value1.orientation.nose);
        final double destinationSpeed = destinationVelocity.offset.magnitude();
        final double lookahead = destinationVelocity.offset.minus(io.value1.velocity).dotProduct(io.value1.orientation.nose);
        desiredSpeed.value = desiredDisplacement * SPEED_CONVERGENCE_RATE + destinationSpeed + lookahead;
        System.out.println(desiredDisplacement);
    }

    private void updateDestinationInfo(final Ray3 newDestination) {
        destinationVelocity = newDestination.minus(previousDestination).scaled(Constants.BOT_REFRESH_RATE);
        previousDestination = newDestination;
        anchorPosition.value = newDestination.offset.plus(newDestination.direction);
    }
}
