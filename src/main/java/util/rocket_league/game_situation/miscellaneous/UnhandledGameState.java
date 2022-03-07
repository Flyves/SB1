package util.rocket_league.game_situation.miscellaneous;

import util.rocket_league.game_situation.GameSituation;
import util.timer.FrameTimer;

public class UnhandledGameState extends GameSituation {

    public UnhandledGameState() {
        super(new FrameTimer(0));
    }

    @Override
    public void loadGameState() {}
}
