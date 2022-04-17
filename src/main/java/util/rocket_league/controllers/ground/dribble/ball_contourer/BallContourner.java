package util.rocket_league.controllers.ground.dribble.ball_contourer;

import util.data_structure.tupple.Tuple2;
import util.data_structure.tupple.Tuple3;
import util.math.vector.Vector3;
import util.rocket_league.Constants;
import util.rocket_league.controllers.ground.navigation.waypoint.single_destination.DestinationNavigator;
import util.rocket_league.controllers.ground.navigation.waypoint.single_destination.DestinationNavigatorProfileBuilder;
import util.rocket_league.dynamic_objects.ball.BallData;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.Behaviour;

import javax.xml.ws.Holder;

public class BallContourner implements Behaviour<Tuple3<ExtendedCarData, BallData, ControlsOutput>, ControlsOutput> {
    private static final double WAYPOINT_ORIENTOR_DISTANCE = 150;
    private static final double DISTANCE_FROM_BALL = 0;
    private static final double SPEED_CONVERGENCE_RATE = 10;

    private final BallContournerProfile ballContournerProfile;
    private final DestinationNavigator<Holder<Vector3>> destinationNavigator;
    private final Holder<Vector3> destination;
    private final Holder<Double> desiredSpeed;

    public BallContourner(final BallContournerProfile ballContournerProfile) {
        this.destination = new Holder<>();
        this.desiredSpeed = new Holder<>();
        this.ballContournerProfile = ballContournerProfile;
        this.destinationNavigator = new DestinationNavigator<>(new DestinationNavigatorProfileBuilder<Holder<Vector3>>()
                .withDestinationMapper(h -> h.value)
                .withTargetSpeed(() -> desiredSpeed.value)
                .withCollision((c, h) -> false)
                .withAngularVelocity(v -> 3.05)
                .withDestination(destination)
                .withMinimumBoostAmount(ballContournerProfile.minimumBoostAmount)
                .build());
    }

    @Override
    public ControlsOutput exec(final Tuple3<ExtendedCarData, BallData, ControlsOutput> io) {
        updateDestination(io);
        updateDesiredSpeed(io);
        return destinationNavigator.exec(new Tuple2<>(io.value1, io.value3));
    }

    private void updateDestination(final Tuple3<ExtendedCarData, BallData, ControlsOutput> io) {
        final Vector3 destinationForwardOffset = io.value2.velocity
                .scaled(1, 1, 0)
                .scaledToMagnitude(WAYPOINT_ORIENTOR_DISTANCE);
        destination.value = destination(io)
                .plus(destinationForwardOffset);
    }

    private void updateDesiredSpeed(final Tuple3<ExtendedCarData, BallData, ControlsOutput> io) {
        final double distanceFromTarget = destination(io)
                .minus(io.value1.position)
                .dotProduct(io.value1.orientation.nose);
        final double baseSpeed = io.value2.velocity.flatten().magnitude();
        final double lookahead = io.value2.velocity.minus(io.value1.velocity).dotProduct(io.value1.orientation.nose);
        desiredSpeed.value = distanceFromTarget * SPEED_CONVERGENCE_RATE + baseSpeed + lookahead;
    }

    private Vector3 destination(final Tuple3<ExtendedCarData, BallData, ControlsOutput> io) {
        final Vector3 flatBallPosition = io.value2.position.scaled(1, 1, 0);
        final double angle = -ballContournerProfile.hitAngleFunction.get() + Math.atan2(io.value2.velocity.y, io.value2.velocity.x);
        final Vector3 offset = Vector3.X_VECTOR.rotate(Vector3.Z_VECTOR.scaled(angle)).scaled(findBallOffset(io));
        return flatBallPosition.plus(offset);
    }

    private double findBallOffset(final Tuple3<ExtendedCarData, BallData, ControlsOutput> io) {
        final Vector3 offsetOnCar = io.value1.carHitBox.closestPointOnSurface(io.value2.position);
        final Vector3 offsetOnBall = io.value2.position.plus(offsetOnCar.minus(io.value2.position).scaledToMagnitude(Constants.BALL_RADIUS));
        final double carOffsetDistance = offsetOnCar.minus(io.value1.position).flatten().magnitude();
        final double ballOffsetDistance = offsetOnBall.minus(io.value2.position).flatten().magnitude();
        return carOffsetDistance + ballOffsetDistance + DISTANCE_FROM_BALL;
    }
}
