package util.rocket_league.controllers.ground.throttle;

public abstract class GroundThrottling {
    public static Double computeControlsOutput(final Double desiredAcceleration, final Double speed) {
        // we get access to analog adjustments if we are accelerating in the same direction we're going
        if(speed * desiredAcceleration > 0) {
            return desiredAcceleration / AccelerationCurve.apply(speed);
        }
        // if we're trying to decelerate less than 525 uu/s, let the coasting deceleration do its job
        else if(Math.abs(desiredAcceleration) < 525) {
            return 0d;
        }
        // else, we can decelerate a lot by reverse-throttling
        else if(speed > 0) {
            return -1d;
        }
        return 1d;
    }

    public static Double findMaxAcceleration(final Double speed) {
        return AccelerationCurve.apply(speed);
    }
}
