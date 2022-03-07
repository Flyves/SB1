package bot;

import bot.flyve.SB1;
import rlbot.manager.BotManager;
import rlbot.pyinterop.SocketServer;

public class PythonInterface extends SocketServer {

    public PythonInterface(int port, BotManager botManager) {
        super(port, botManager);
    }

    protected rlbot.Bot initBot(int index, String botType, int team) {
        return new Bot(new SB1(), index);
    }
}
