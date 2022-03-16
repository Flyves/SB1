package util.rocket_league.controllers.ground.navigation.navigators;

import util.data_structure.tupple.Tuple2;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;
import util.state_machine.Behaviour;
import util.state_machine.Finishable;

public interface Navigator extends Behaviour<Tuple2<ExtendedCarData, ControlsOutput>, ControlsOutput>, Finishable {
}
