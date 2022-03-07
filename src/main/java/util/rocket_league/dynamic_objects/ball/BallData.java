package util.rocket_league.dynamic_objects.ball;


import rlbot.flat.BallInfo;
import rlbot.flat.Physics;
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
    public final double time;   // shouldn't be here

    public BallData(final Physics physics, final double time) {
        this.position = new Vector3(physics.location());
        this.velocity = new Vector3(physics.velocity());
        this.spin = new Vector3(physics.angularVelocity());
        this.time = time;
    }

    public BallData(final BallInfo ball) {
        this.position = new Vector3(ball.physics().location());
        this.velocity = new Vector3(ball.physics().velocity());
        this.spin = new Vector3(ball.physics().angularVelocity());
        this.time = 0;
    }

    public BallData(final Vector3 position, final Vector3 velocity, final Vector3 spin, final double time) {
        this.position = position;
        this.velocity = velocity;
        this.spin = spin;
        this.time = time;
    }

    public BallData(BallData ball, Vector3 newVelocity, Vector3 newAngularVelocity) {
        this.position = ball.position;
        this.velocity = newVelocity;
        this.spin = newAngularVelocity;
        this.time = ball.time;
    }

    public final Vector3 surfaceVelocity(final Vector3 normal) {
        return spin.crossProduct(normal).scaled(Constants.BALL_RADIUS);
    }
}
