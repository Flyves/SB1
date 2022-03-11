package bot.flyve.states.kickoff.states;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import util.data_structure.tupple.Tuple2;
import util.data_structure.tupple.Tuple3;
import util.math.vector.Vector2;
import util.math.vector.Vector3;
import util.rocket_league.controllers.ground.navigation.DestinationCollisionDetection;
import util.rocket_league.controllers.ground.navigation.waypoint.WaypointNavigator;
import util.rocket_league.controllers.ground.navigation.waypoint.WaypointNavigatorProfileBuilder;
import util.rocket_league.controllers.ground.throttle.SpeedController;
import util.rocket_league.controllers.jump.jump_sequence.JumpController;
import util.rocket_league.controllers.jump.jump_sequence.JumpProfileBuilder;
import util.rocket_league.controllers.jump.second.SecondJumpType;
import util.rocket_league.dynamic_objects.boost.BoostPad;
import util.rocket_league.dynamic_objects.boost.BoostPadManager;
import util.rocket_league.io.input.DataPacket;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.State;

import java.util.ArrayList;
import java.util.List;

public class BaseState extends State<DataPacket, ControlsOutput> {
    private final WaypointNavigator waypointNavigator;
    private final SpeedController speedController;
    private JumpController jumpController;

    public BaseState() {
        waypointNavigator = new WaypointNavigator(new WaypointNavigatorProfileBuilder()
                .withCollision((car, destination) -> {
                    if(DestinationCollisionDetection.DEFAULT_COLLISION_DETECTION_FUNCTION.apply(car, destination)) {
                        return true;
                    }
                    final BoostPad foundBoostPad = BoostPadManager.boostPads.stream().filter(boostPad -> boostPad.location.equals(destination)).findFirst().orElse(null);
                    if(foundBoostPad == null) {
                        return false;
                    }
                    return !foundBoostPad.isActive;
                })
                .withAngularVelocity(v -> 3.0)
                .build());
        speedController = new SpeedController();
        jumpController = new JumpController(new JumpProfileBuilder().build());
    }

    @Override
    public void start(final DataPacket input) {
        //waypointNavigator.remainingWaypoints().add(input.ball.position);
    }

    @Override
    public ControlsOutput exec(DataPacket input) {
        // generating a new path everytime we're done driving
        if(waypointNavigator.isFinished()) {
            int firstPadIndex = 6;
            int secondPadIndex = 23;
            final List<DefaultWeightedEdge> edges = new DijkstraShortestPath<>(
                    BoostPadManager.boostPadGraphWrapper.getGraph(),
                    BoostPadManager.boostPads.get(firstPadIndex),
                    BoostPadManager.boostPads.get(secondPadIndex))
                    .getPathEdgeList();
            waypointNavigator.remainingWaypoints().add(BoostPadManager.boostPads.get(firstPadIndex).location);
            edges.forEach(edge -> {
                final BoostPad target = BoostPadManager.boostPadGraphWrapper.getGraph().getEdgeTarget(edge);
                waypointNavigator.remainingWaypoints().add(target.location);
            });
        }

        // computing the output
        final ControlsOutput output = new ControlsOutput();
        try {
            waypointNavigator.exec(new Tuple2<>(input.car, output));    // steering control
            speedController.exec(new Tuple3<>(input.car, output, 2300.0)); // throttle control
        }
        catch (Exception e) {

        }
        output.isBoosting = !input.car.isSupersonic;        // boost control
        if(waypointNavigator.activeWaypoint().isPresent()) { // jump and flip control
            final Vector3 activeWaypoint = waypointNavigator.activeWaypoint().get();
            if(activeWaypoint.distance(input.car.position) > 2500 && jumpController.isFinished()
                && input.car.groundSpeedForward > 800
                && activeWaypoint.minus(input.car.position).angle(input.car.velocity) < 0.03
                && !input.car.isSupersonic) {
                jumpController = new JumpController(new JumpProfileBuilder()
                        .withInitialImpulse(400)
                        .withSecondJumpType(SecondJumpType.FLIP)
                        .build());
            }
            // boost control on waypoints and while jumping
            if(!jumpController.isFinished() || activeWaypoint.minus(input.car.position).angle(input.car.velocity) > 0.4) {
                output.isBoosting = false;
            }
        }
        return jumpController.exec(new Tuple2<>(input.car, output));
    }

    @Override
    public State<DataPacket, ControlsOutput> next(DataPacket input) {
        return this;
    }
}
