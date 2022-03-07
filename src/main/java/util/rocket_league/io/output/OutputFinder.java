package util.rocket_league.io.output;

import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.input.DataPacket;

public class OutputFinder {

    public static ControlsOutput findOutputFromCarPhysics(final ExtendedCarData car, final DataPacket dataPacket) {
        final ControlsOutput output = new ControlsOutput();

        // TODO : use physics and data from the framework to find accurately the output of any car on the field

        return output;
    }
}
