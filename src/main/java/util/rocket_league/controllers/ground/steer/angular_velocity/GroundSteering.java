package util.rocket_league.controllers.ground.steer.angular_velocity;

public class GroundSteering {
    public static Double computeControlsOutput(final Double desiredAngularVelocity, final Double speed) {
        return desiredAngularVelocity/findMaxAngularVelocity(speed);
    }

    public static Double findMaxAngularVelocity(final Double speed) {
        final double maxRadius = TurnRadius.apply(speed);
        return speed/maxRadius;
    }
}
