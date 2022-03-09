package util.rocket_league.controllers.ground.steer.angular_velocity;

public class GroundSteering {
    public static Double computeControlsOutput(final Double desiredAngularVelocity, final Double speed) {
        return desiredAngularVelocity/findMaxSpin(speed);
    }

    public static Double findMaxSpin(final Double speed) {
        final double maxRadius = TurnRadius.apply(speed);
        return speed/maxRadius;
    }
}
