package util.rocket_league.controllers.ground.dribble.reverse_hit.states;

import util.data_structure.tupple.Tuple2;
import util.data_structure.tupple.Tuple3;
import util.math.vector.Vector3;
import util.rocket_league.controllers.ground.navigation.navigators.multiple_destination.*;
import util.rocket_league.controllers.ground.navigation.waypoints.DefaultPlayfieldWaypoint;
import util.rocket_league.controllers.ground.navigation.waypoints.Waypoint;
import util.rocket_league.dynamic_objects.ball.BallData;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.State;

import javax.xml.ws.Holder;

public class GoToOppositeSideOfBallDirection extends State<Tuple3<ExtendedCarData, BallData, ControlsOutput>, ControlsOutput> {
    private final WaypointNavigator<Waypoint> waypointNavigator;
    private final Holder<Double> speedHolder;

    public GoToOppositeSideOfBallDirection() {
        speedHolder = new Holder<>(500d);
        this.waypointNavigator = new WaypointNavigator<>(new WaypointNavigatorProfileBuilder<Waypoint>()
                .withDestinationMapper(waypoint -> waypoint.position)
                .withCollision((car, waypoint) -> waypoint.collisionFunction.apply(car))
                .withAngularVelocity(c -> 3.05)
                .withTargetSpeed(() -> speedHolder.value)
                .withMinimumBoostAmount(() -> 100d)
                .build());
    }

    @Override
    public ControlsOutput exec(Tuple3<ExtendedCarData, BallData, ControlsOutput> input) {
        new DefaultPlayfieldWaypoint(new Vector3());
        return waypointNavigator.exec(new Tuple2<>(input.value1, input.value3));
    }

    @Override
    public State<Tuple3<ExtendedCarData, BallData, ControlsOutput>, ControlsOutput> next(Tuple3<ExtendedCarData, BallData, ControlsOutput> input) {
        return new HitTheBall();
    }
}
