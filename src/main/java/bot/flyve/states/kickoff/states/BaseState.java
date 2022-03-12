package bot.flyve.states.kickoff.states;

import util.data_structure.tupple.Tuple2;
import util.math.vector.Vector3;
import util.rocket_league.controllers.ground.navigation.boostpad.BoostPadPathGenerator;
import util.rocket_league.controllers.ground.navigation.waypoint.WaypointNavigator;
import util.rocket_league.controllers.ground.navigation.waypoint.WaypointNavigatorProfileBuilder;
import util.rocket_league.controllers.jump.second.SecondJumpType;
import util.rocket_league.dynamic_objects.boost.BoostPad;
import util.rocket_league.dynamic_objects.boost.BoostPadManager;
import util.rocket_league.io.input.DataPacket;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.State;

import java.util.LinkedHashSet;

public abstract class BaseState extends State<DataPacket, ControlsOutput> {
    WaypointNavigator waypointNavigator;
    LinkedHashSet<Vector3> waypoints;

    public BaseState() {}

    @Override
    public void start(final DataPacket input) {
        waypoints.add(input.car.position.minus(input.ball.position).scaled(0, 1, 0).scaledToMagnitude(140));
        this.waypointNavigator = new WaypointNavigator(new WaypointNavigatorProfileBuilder()
                .withAngularVelocity(v -> 3.05)
                .withFlipType(SecondJumpType.FLIP)
                .withWaypoints(waypoints)
                .withTargetSpeed(() -> 2300.0)
                .build());
    }

    LinkedHashSet<Vector3> generateBasicBoostPath(final DataPacket input) {
        final BoostPad closestBoostPadToCar = BoostPadManager.boostPads.stream()
                .reduce((b1, b2) -> b1.location.distanceSquared(input.car.position) < b2.location.distanceSquared(input.car.position) ? b1 : b2)
                .get();
        final Vector3 destinationOnBall = input.ball.position.plus(input.car.position.minus(input.ball.position)
                .scaled(0, 1, 0).scaledToMagnitude(150));
        final BoostPad closestBoostPadToBall = BoostPadManager.boostPads.stream()
                .reduce((b1, b2) -> b1.location.distanceSquared(destinationOnBall) < b2.location.distanceSquared(destinationOnBall) ? b1 : b2)
                .get();
        return BoostPadPathGenerator.generatePath(closestBoostPadToCar, closestBoostPadToBall);
    }

    @Override
    public ControlsOutput exec(DataPacket input) {
        return waypointNavigator.exec(new Tuple2<>(input.car, new ControlsOutput()));
    }

    @Override
    public State<DataPacket, ControlsOutput> next(DataPacket input) {
        return this;
    }
}
