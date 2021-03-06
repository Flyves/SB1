package bot;

import bot.debug.DebugRenderer;
import bot.flyve.SB1;
import rlbot.ControllerState;
import rlbot.flat.GameTickPacket;
import util.math.statistics.StatisticalData;
import util.renderers.IndexedRenderer;
import util.renderers.RenderTasks;
import util.rocket_league.io.input.DataPacket;
import util.rocket_league.io.input.DataPacketParameters;
import util.rocket_league.io.output.ControlsOutput;
import util.rocket_league.keyboard_command_listener.HICommandListener;
import util.state_machine.Behaviour;

import java.util.LinkedList;
import java.util.List;

public class Bot implements rlbot.Bot {

    private final int playerIndex;
    private final Behaviour<DataPacket, ControlsOutput> botBehaviour;

    public final List<DataPacket> pastInputs;
    public final List<List<ControlsOutput>> pastOutputs;

    private final StatisticalData<Double> statisticalData;

    public Bot(SB1 rocketLeagueBot, int playerIndex) {
        this.botBehaviour = rocketLeagueBot;
        this.playerIndex = playerIndex;
        this.statisticalData = new StatisticalData<>(500);

        this.pastInputs = new LinkedList<>();
        this.pastOutputs = new LinkedList<>();
        RenderTasks.init();
    }

    @Override
    public int getIndex() {
        return this.playerIndex;
    }

    @Override
    public ControllerState processInput(GameTickPacket packet) {
        if(dataLooksFishy(packet)) {
            return new ControlsOutput();
        }
        final DataPacketParameters inputParameters = new DataPacketParameters(packet, playerIndex, pastInputs, pastOutputs);
        final DataPacket input = new DataPacket(inputParameters);
        final ControlsOutput output = runBotLogic(input);

        DebugRenderer.render(output);

        if(HICommandListener.instance.isBotControlling()) {
            return output;
        }
        return HICommandListener.instance.asControlsOutput();
    }

    @Override
    public void retire() {
        System.out.println("Bot #" + playerIndex + " died");
        RenderTasks.close();
    }

    public static IndexedRenderer getNewIndexedRenderer() {
        return new IndexedRenderer(0);
    }

    private boolean dataLooksFishy(GameTickPacket packet) {
        return packet.playersLength() <= playerIndex
                || packet.ball() == null;
    }

    private ControlsOutput runBotLogic(DataPacket input) {
        processDefaultInputs(input);

        final ControlsOutput controlsOutput = botBehaviour.exec(input);

        RenderTasks.render();
        RenderTasks.clearTaskBuffer();

        // yikes plz fix
        {
            // TODO : remove this, and find the output of any car in the field by implementing the OutputFinder class
            // quick fix to remember the outputs of the bot.
            // caution: WE ONLY KNOW THE OUTPUT OF OURSELVES WITH THIS!!
            pastOutputs.get(pastOutputs.size() - 1).set(playerIndex, controlsOutput);
        }

        return controlsOutput;
    }

    private void processDefaultInputs(DataPacket input) {

    }
}
