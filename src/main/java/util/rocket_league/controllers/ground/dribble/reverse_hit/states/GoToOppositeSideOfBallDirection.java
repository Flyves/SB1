package util.rocket_league.controllers.ground.dribble.reverse_hit.states;

import util.data_structure.tupple.Tuple2;
import util.data_structure.tupple.Tuple3;
import util.math.vector.Vector3;
import util.rocket_league.controllers.ground.navigation.waypoint.multiple_destination.*;
import util.rocket_league.controllers.ground.navigation.waypoint.waypoints.DefaultPlayfieldWaypoint;
import util.rocket_league.controllers.ground.navigation.waypoint.waypoints.Waypoint;
import util.rocket_league.controllers.jump.second.SecondJumpType;
import util.rocket_league.dynamic_objects.ball.BallData;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.State;

import javax.xml.ws.Holder;
import java.util.LinkedHashSet;

public class GoToOppositeSideOfBallDirection extends State<Tuple3<ExtendedCarData, BallData, ControlsOutput>, ControlsOutput> {
    private final WaypointNavigator<Holder<Waypoint>> waypointNavigator;
    private final Holder<Waypoint> firstWaypointHolder;
    private final Holder<Waypoint> secondWaypointHolder;

    public GoToOppositeSideOfBallDirection() {
        this.firstWaypointHolder = new Holder<>();
        this.secondWaypointHolder = new Holder<>();
        final LinkedHashSet<Holder<Waypoint>> waypoints = new LinkedHashSet<>();
        waypoints.add(firstWaypointHolder);
        //waypoints.add(secondWaypointHolder);
        this.waypointNavigator = new WaypointNavigator<>(new WaypointNavigatorProfileBuilder<Holder<Waypoint>>()
                .withDestinationMapper(h -> h.value.position)
                .withCollision((car, h) -> h.value.collisionFunction.apply(car))
                .withFlipType(SecondJumpType.FLIP)
                .withWaypoints(waypoints)
                .withAngularVelocity(c -> 3.05)
                .withMinimumBoostAmount(() -> 100d)
                .build());
    }

    @Override
    public ControlsOutput exec(Tuple3<ExtendedCarData, BallData, ControlsOutput> io) {
        final Vector3 sidewaysComponent = io.value2.velocity.scaled(1, 1, 0).rotate(new Vector3(0, 0, 1)).scaledToMagnitude(700).scaled(1, 1, 0);
        firstWaypointHolder.value = new DefaultPlayfieldWaypoint(io.value2.position.plus(sidewaysComponent).scaled(1, 1, 0));
        secondWaypointHolder.value = new DefaultPlayfieldWaypoint(io.value2.position.plus(io.value2.velocity.scaled(1, 1, 0).scaledToMagnitude(700)).plus(sidewaysComponent).scaled(1, 1, 0));
        return waypointNavigator.exec(new Tuple2<>(io.value1, io.value3));
    }

    @Override
    public State<Tuple3<ExtendedCarData, BallData, ControlsOutput>, ControlsOutput> next(Tuple3<ExtendedCarData, BallData, ControlsOutput> input) {
        if(waypointNavigator.isFinished()) {
            return new HitTheBall();
        }
        return this;
    }
}
