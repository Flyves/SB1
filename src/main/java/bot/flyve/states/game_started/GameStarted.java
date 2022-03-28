package bot.flyve.states.game_started;

import bot.flyve.states.kickoff.KickoffPosition;
import bot.flyve.states.round_over.RoundOver;
import util.data_structure.tupple.Tuple2;
import util.math.vector.Vector3;
import util.rocket_league.controllers.ground.navigation.navigators.multiple_destination.WaypointNavigator;
import util.rocket_league.controllers.ground.navigation.navigators.multiple_destination.WaypointNavigatorProfileBuilder;
import util.rocket_league.io.input.DataPacket;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.State;

import java.util.LinkedHashSet;

public class GameStarted extends State<DataPacket, ControlsOutput> {
    public final WaypointNavigator<Vector3> waypointNavigator;

    public GameStarted() {
        final LinkedHashSet<Vector3> linkedHashSet = new LinkedHashSet<>();
        for(int i = 0; i < 100; i++) {
            linkedHashSet.add(new Vector3((Math.random()-0.5)*4000, (Math.random()-0.5)*8000, 0));
        }

        this.waypointNavigator = new WaypointNavigator<>(new WaypointNavigatorProfileBuilder<Vector3>()
                .withTargetSpeed(() -> 1200.0)
                .withAngularVelocity(v -> 3.05)
                .withMinimumBoostAmount(() -> 100.0)
                .withWaypoints(linkedHashSet)
                .build());
    }

    @Override
    public void start(DataPacket input) {

    }

    @Override
    public ControlsOutput exec(DataPacket input) {
        return waypointNavigator.exec(new Tuple2<>(input.car, new ControlsOutput()));
    }

    @Override
    public void stop(DataPacket input) {

    }

    @Override
    public State<DataPacket, ControlsOutput> next(final DataPacket input) {
        if(!input.isRoundActive || !KickoffPosition.kickoffFinished(input)) {
            return new RoundOver();
        }
        return this;
    }
}
