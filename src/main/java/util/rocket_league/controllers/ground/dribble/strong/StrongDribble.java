package util.rocket_league.controllers.ground.dribble.strong;

import util.data_structure.tupple.Tuple2;
import util.data_structure.tupple.Tuple3;
import util.math.vector.Vector3;
import util.rocket_league.controllers.ground.navigation.navigators.single_destination.DestinationNavigator;
import util.rocket_league.controllers.ground.navigation.navigators.single_destination.DestinationNavigatorProfileBuilder;
import util.rocket_league.dynamic_objects.ball.BallData;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.Behaviour;

import javax.xml.ws.Holder;

public class StrongDribble implements Behaviour<Tuple3<ExtendedCarData, BallData, ControlsOutput>, ControlsOutput> {
    private static final double DESTINATION_DISTANCE = 180;
    private static final double SPEED_CONVERGENCE_RATE = 10;

    private final StrongDribbleProfile strongDribbleProfile;
    private final DestinationNavigator<Holder<Vector3>> destinationNavigator;
    private final Holder<Vector3> destination;
    private final Holder<Double> desiredSpeed;

    public StrongDribble(final StrongDribbleProfile strongDribbleProfile) {
        this.destination = new Holder<>();
        this.desiredSpeed = new Holder<>();
        this.strongDribbleProfile = strongDribbleProfile;
        this.destinationNavigator = new DestinationNavigator<>(new DestinationNavigatorProfileBuilder<Holder<Vector3>>()
                .withDestinationMapper(h -> h.value)
                .withTargetSpeed(() -> desiredSpeed.value)
                .withCollision((c, h) -> false)
                .withAngularVelocity(v -> 3.05)
                .withDestination(destination)
                .withMinimumBoostAmount(strongDribbleProfile.minimumBoostAmount)
                .build());
    }

    @Override
    public ControlsOutput exec(final Tuple3<ExtendedCarData, BallData, ControlsOutput> io) {
        updateDestination(io);
        updateDesiredSpeed(io);
        return destinationNavigator.exec(new Tuple2<>(io.value1, io.value3));
    }

    private void updateDestination(final Tuple3<ExtendedCarData, BallData, ControlsOutput> io) {
        destination.value = io.value2.position.scaled(1, 1, 0);
        final Vector3 destinationForwardOffset = io.value2.velocity
                .scaled(1, 1, 0)
                .scaledToMagnitude(DESTINATION_DISTANCE);
        final Vector3 destinationSidewaysOffset = io.value2.velocity
                .scaled(1, 1, 0)
                .scaledToMagnitude(strongDribbleProfile.steeringOffsetFunction.get())
                .rotate(Vector3.UP_VECTOR.scaled(Math.PI/2));
        destination.value = destination.value
                .plus(destinationForwardOffset)
                .plus(destinationSidewaysOffset);
    }

    private void updateDesiredSpeed(final Tuple3<ExtendedCarData, BallData, ControlsOutput> io) {
        final Vector3 destinationForwardOffset = io.value2.velocity
                .scaled(1, 1, 0)
                .scaledToMagnitude(strongDribbleProfile.throttlingOffsetFunction.get());
        final double distanceFromTarget = io.value2.position
                .minus(io.value1.position)
                .plus(destinationForwardOffset)
                .dotProduct(io.value1.orientation.nose);
        final double baseSpeed = io.value2.velocity.flatten().magnitude();
        final double lookahead = io.value2.velocity.minus(io.value1.velocity).dotProduct(io.value1.orientation.nose);
        desiredSpeed.value = distanceFromTarget * SPEED_CONVERGENCE_RATE + baseSpeed + lookahead;
    }
}
