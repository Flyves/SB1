package util.rocket_league.controllers.ground.throttle;

import util.math.linear_transform.LinearApproximator;
import util.math.vector.Vector2;

/**
 * This class represents a function of the acceleration of a rocket league car with respect to its speed.
 */

public abstract class AccelerationCurve {

    private static LinearApproximator a;
    static {
        a = new LinearApproximator();

        // data taken from Sam Mish's notes
        // https://samuelpmish.github.io/notes/RocketLeague/ground_control/
        a.sample(new Vector2(0, 1600));
        a.sample(new Vector2(1409.999, 160));
        a.sample(new Vector2(1410, 0));
        a.sample(new Vector2(2300, 0));
    }

    /**
     *  Get the acceleration from the car speed.
     * @param speed The speed at which the car is going
     * @return the corresponding acceleration
     */
    public static Double apply(final Double speed) {
        return AccelerationCurve.a.compute(Math.abs(speed));
    }

    /**
     *  Get the speed from the car's acceleration.
     * @param acceleration The radius of the turning circle
     * @return the corresponding speed of the car
     */
    public static Double inverse(final Double acceleration) {
        return AccelerationCurve.a.inverse(acceleration);
    }
}
