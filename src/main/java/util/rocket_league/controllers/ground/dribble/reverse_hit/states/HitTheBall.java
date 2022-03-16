package util.rocket_league.controllers.ground.dribble.reverse_hit.states;

import util.data_structure.tupple.Tuple2;
import util.data_structure.tupple.Tuple3;
import util.rocket_league.controllers.ground.navigation.navigators.multiple_destination.WaypointNavigator;
import util.rocket_league.controllers.ground.navigation.navigators.multiple_destination.WaypointNavigatorProfileBuilder;
import util.rocket_league.controllers.ground.navigation.waypoints.DefaultPlayfieldWaypoint;
import util.rocket_league.controllers.ground.navigation.waypoints.Waypoint;
import util.rocket_league.dynamic_objects.ball.BallData;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.State;

import javax.xml.ws.Holder;
import java.util.LinkedHashSet;

public class HitTheBall extends State<Tuple3<ExtendedCarData, BallData, ControlsOutput>, ControlsOutput> {
    private final WaypointNavigator<Holder<Waypoint>> waypointNavigator;
    private final Holder<Waypoint> waypointHolder;

    public HitTheBall() {
        this.waypointHolder = new Holder<>();
        final LinkedHashSet<Holder<Waypoint>> waypoints = new LinkedHashSet<>();
        waypoints.add(waypointHolder);
        this.waypointNavigator = new WaypointNavigator<>(new WaypointNavigatorProfileBuilder<Holder<Waypoint>>()
                .withDestinationMapper(h -> h.value.position)
                .withCollision((car, h) -> h.value.collisionFunction.apply(car))
                .withWaypoints(waypoints)
                .withAngularVelocity(c -> 3.05)
                .withTargetSpeed(() -> 1200d)
                .withMinimumBoostAmount(() -> 100d)
                .build());
    }

    @Override
    public ControlsOutput exec(Tuple3<ExtendedCarData, BallData, ControlsOutput> io) {
        waypointHolder.value = new DefaultPlayfieldWaypoint(io.value2.position.plus(io.value1.position.minus(io.value2.position).scaledToMagnitude(200)));
        return waypointNavigator.exec(new Tuple2<>(io.value1, io.value3));
    }

    @Override
    public State<Tuple3<ExtendedCarData, BallData, ControlsOutput>, ControlsOutput> next(Tuple3<ExtendedCarData, BallData, ControlsOutput> input) {
        if(waypointNavigator.isFinished()) {
            return new GoToOppositeSideOfBallDirection();
        }
        return this;
    }
}
