package util.rocket_league.game_situation.situations.ball_hit;

import rlbot.gamestate.*;
import util.rocket_league.game_situation.GameSituation;
import util.timer.FrameTimer;

public class WallHit1 extends GameSituation {

    public WallHit1() {
        super(new FrameTimer(4*30));
    }

    @Override
    public void loadGameState() {
        GameState gameState = getCurrentGameState();
        gameState.withBallState(new BallState(new PhysicsState().withLocation(new DesiredVector3(3000f, 0f, 1000f))
                .withAngularVelocity(new DesiredVector3(0f, 0f, 0f))
                .withRotation(new DesiredRotation(0f, 0f, 0f))
                .withVelocity(new DesiredVector3(1000f, 0f, 0f))));

        applyGameState(gameState);
    }
}
