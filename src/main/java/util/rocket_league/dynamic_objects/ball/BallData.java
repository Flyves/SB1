package util.rocket_league.dynamic_objects.ball;


import rlbot.flat.BallInfo;
import rlbot.flat.Physics;
import rlbot.flat.PredictionSlice;
import util.rocket_league.Constants;
import util.math.vector.Vector3;

/**
 * Basic information about the getNativeBallPrediction.
 *
 * This class is here for your convenience, it is NOT part of the framework. You can change it as much
 * as you want, or delete it.
 */
public class BallData {
    public final Vector3 position;
    public final Vector3 velocity;
    public final Vector3 spin;

    public BallData(final BallInfo ball) {
        this.position = new Vector3(ball.physics().location());
        this.velocity = new Vector3(ball.physics().velocity());
        this.spin = new Vector3(ball.physics().angularVelocity());
    }

    public BallData(final Vector3 position, final Vector3 velocity, final Vector3 angularVelocity) {
        this.position = position;
        this.velocity = velocity;
        this.spin = angularVelocity;
    }

    public BallData(final PredictionSlice predictionSlice) {
        this.position = new Vector3(predictionSlice.physics().location());
        this.velocity = new Vector3(predictionSlice.physics().velocity());
        this.spin = new Vector3(predictionSlice.physics().angularVelocity());
    }

    public final Vector3 surfaceVelocity(final Vector3 normal) {
        return spin.crossProduct(normal).scaled(Constants.BALL_RADIUS);
    }
}
