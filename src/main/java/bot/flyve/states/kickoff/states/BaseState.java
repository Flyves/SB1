package bot.flyve.states.kickoff.states;

import util.data_structure.tupple.Tuple2;
import util.rocket_league.controllers.ground.navigation.boostpad.BoostPadNavigator;
import util.rocket_league.controllers.ground.navigation.boostpad.BoostPadNavigatorProfileBuilder;
import util.rocket_league.controllers.jump.second.SecondJumpType;
import util.rocket_league.dynamic_objects.boost.BoostPadManager;
import util.rocket_league.io.input.DataPacket;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.State;

public class BaseState extends State<DataPacket, ControlsOutput> {
    private BoostPadNavigator boostPadNavigator;

    public BaseState() {

    }

    @Override
    public ControlsOutput exec(DataPacket input) {
        if(boostPadNavigator == null || boostPadNavigator.isFinished()) {
            final int randomIndex = (int)(Math.random() * BoostPadManager.boostPads.size());
            this.boostPadNavigator = new BoostPadNavigator(new BoostPadNavigatorProfileBuilder()
                    .withMinimumBoostAmount(40)
                    .withFlipType(SecondJumpType.FLIP)
                    .withAngularVelocity(v -> 3.05)
                    .withBoostPadSource(BoostPadManager.boostPads.stream().reduce((b1, b2) -> b1.location.distance(input.car.position) < b2.location.distance(input.car.position) ? b1 : b2).get())
                    .withBoostPadTarget(BoostPadManager.boostPads.get(randomIndex))
                    .withTargetSpeed(() -> 2300.0)
                    .build());
        }
        return boostPadNavigator.exec(new Tuple2<>(input.car, new ControlsOutput()));
    }

    @Override
    public State<DataPacket, ControlsOutput> next(DataPacket input) {
        return this;
    }
}
