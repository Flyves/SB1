package util.rocket_league.io.input;

import rlbot.flat.GameTickPacket;
import rlbot.render.Renderer;
import util.rocket_league.io.output.ControlsOutput;

import java.util.List;
import java.util.Optional;


public class DataPacketParameters {

    public final GameTickPacket request;
    public final int playerIndex;
    public final List<DataPacket> pastInputs;
    public final List<ControlsOutput> pastOutputs;

    public DataPacketParameters(GameTickPacket request, int playerIndex, List<DataPacket> pastInputs, List<ControlsOutput> pastOutputs) {
        this.request = request;
        this.playerIndex = playerIndex;
        this.pastInputs = pastInputs;
        this.pastOutputs = pastOutputs;
    }

}
