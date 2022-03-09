package bot.flyve.states.kickoff;

import bot.flyve.states.game_started.GameStarted;
import bot.flyve.states.kickoff.states.*;
import util.data_structure.tupple.Tuple2;
import util.data_structure.tupple.Tuple3;
import util.math.statistics.StatisticalData;
import util.math.vector.Vector3;
import util.rocket_league.controllers.ground.navigation.destination.DestinationNavigator;
import util.rocket_league.controllers.ground.navigation.destination.DestinationProfileBuilder;
import util.rocket_league.controllers.ground.navigation.waypoint.WaypointNavigator;
import util.rocket_league.controllers.ground.navigation.waypoint.WaypointNavigatorProfileBuilder;
import util.rocket_league.controllers.ground.throttle.SpeedController;
import util.rocket_league.io.input.DataPacket;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.State;
import util.state_machine.StateMachine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Kickoff extends State<DataPacket, ControlsOutput> {

    private StateMachine<DataPacket, ControlsOutput> stateMachine;

    private WaypointNavigator waypointNavigator;
    private SpeedController speedController;

    StatisticalData<Double> statisticalData = new StatisticalData<>(60);

    @Override
    public void start(DataPacket input) {
        final KickoffPosition kickoffPosition = KickoffPosition.findPosition(input.car);
        State<DataPacket, ControlsOutput> startingState;
        switch (kickoffPosition) {
            case FAR_LEFT: startingState = new FarLeft();
            break;
            case FAR_RIGHT: startingState = new FarRight();
            break;
            case MIDDLE_LEFT: startingState = new MiddleLeft();
            break;
            case MIDDLE_RIGHT: startingState = new MiddleRight();
            break;
            case MIDDLE: startingState = new Middle();
            break;
            default: startingState = null;
        }
        stateMachine = new StateMachine<>(startingState);
        waypointNavigator = new WaypointNavigator(new WaypointNavigatorProfileBuilder()
                .withAngularVelocity(v -> 3.0)
                .withWaypoints(Arrays.stream(new Vector3[10])
                        .map(v -> Vector3.random())
                        .map(v -> v.minus(new Vector3(0.5, 0.5, 0)))
                        .map(v -> v.scaled(2))
                        .map(v -> v.scaled(3000, 4000, 0))
                        .collect(Collectors.toList()))
                .build());
        speedController = new SpeedController();
    }

    @Override
    public ControlsOutput exec(DataPacket input) {
        //return stateMachine.exec(input);
        final ControlsOutput controlsOutput = new ControlsOutput();
        if(!waypointNavigator.isFinished()) speedController.exec(new Tuple3<>(input.car, controlsOutput, 1410.0));
        return waypointNavigator.exec(new Tuple2<>(input.car, controlsOutput));
    }

    @Override
    public State<DataPacket, ControlsOutput> next(DataPacket input) {
        if(isKickoffDone(input)) {
            return new GameStarted();
        }
        return this;
    }

    private boolean isKickoffDone(DataPacket input) {
        return KickoffPosition.kickoffFinished(input);
    }
}
