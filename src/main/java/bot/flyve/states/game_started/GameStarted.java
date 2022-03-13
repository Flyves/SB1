package bot.flyve.states.game_started;

import bot.flyve.states.kickoff.Kickoff;
import bot.flyve.states.kickoff.KickoffPosition;
import util.data_structure.tupple.Tuple2;
import util.math.vector.Vector3;
import util.rocket_league.Constants;
import util.rocket_league.controllers.ground.navigation.path_generator.WaypointPathGenerator;
import util.rocket_league.controllers.ground.navigation.waypoint.WaypointNavigator;
import util.rocket_league.controllers.ground.navigation.waypoint.WaypointNavigatorProfileBuilder;
import util.rocket_league.controllers.jump.second.SecondJumpType;
import util.rocket_league.dynamic_objects.ball.BallData;
import util.rocket_league.dynamic_objects.boost.BoostPadManager;
import util.rocket_league.io.input.DataPacket;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.State;

import javax.xml.ws.Holder;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

public class GameStarted extends State<DataPacket, ControlsOutput> {
    private WaypointNavigator<Holder<Vector3>> waypointNavigator;
    private Holder<Vector3> destination;
    private Holder<Double> speedHolder;

    public GameStarted() {
        destination = new Holder<>(new Vector3());
        speedHolder = new Holder<>(0d);
    }

    @Override
    public void start(DataPacket input) {

    }

    @Override
    public ControlsOutput exec(DataPacket input) {
        /*
        final List<BallData> bouncyBalls = input.ballPrediction.stream()
                .filter(ballData -> {
                    final int index = input.ballPrediction.indexOf(ballData);
                    if(index >= input.ballPrediction.size()-1) {
                        return false;
                    }
                    final int nextIndex = index + 1;
                    final BallData nextBallData = input.ballPrediction.get(nextIndex);
                    return nextBallData.velocity.minus(ballData.velocity).scaled(Constants.BALL_PREDICTION_REFRESH_RATE)
                            .minus(Constants.GRAVITY_VECTOR)
                            .magnitudeSquared() > 100 * 100;
                })
                .collect(Collectors.toList());


        if(!bouncyBalls.isEmpty()) {
            destination.value = bouncyBalls.get(0).position.minus(new Vector3(0, 0, Constants.BALL_RADIUS));
            speedHolder.value = input.car.position.minus(bouncyBalls.get(0).position).magnitude()
                    / (input.ballPrediction.indexOf(bouncyBalls.get(0))/Constants.BALL_PREDICTION_REFRESH_RATE);
        }

        if(!bouncyBalls.isEmpty() && (waypointNavigator == null || waypointNavigator.isFinished())) {
            final LinkedHashSet<Holder<Vector3>> linkedHashSet = new LinkedHashSet<>();
            linkedHashSet.add(destination);
            this.waypointNavigator = new WaypointNavigator<>(new WaypointNavigatorProfileBuilder<Holder<Vector3>>()
                    .withDestinationMapper(h -> h.value)
                    .withWaypoints(linkedHashSet)
                    .withTargetSpeed(() -> speedHolder.value)
                    .withAngularVelocity(v -> 3.05)
                    .withFlipType(SecondJumpType.FLIP)
                    .build());
        }
        if(waypointNavigator != null && !bouncyBalls.isEmpty()) {
            return waypointNavigator.exec(new Tuple2<>(input.car, new ControlsOutput()));
        }*/
        WaypointPathGenerator.generateGroundPathBetween(input.humanCars.get(0).position, new Vector3(), 100);
        return new ControlsOutput();
    }

    @Override
    public void stop(DataPacket input) {

    }

    @Override
    public State<DataPacket, ControlsOutput> next(DataPacket input) {
        if(isGameDone(input)) {
            return new Kickoff();
        }
        return this;
    }

    private boolean isGameDone(DataPacket input) {
        return !KickoffPosition.kickoffFinished(input);
    }
}
