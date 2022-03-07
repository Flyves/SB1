package util.rocket_league.physics.aerial_dynamics.boost_rotation_displacement_approximations;

import util.math.vector.Vector3;
import util.rocket_league.dynamic_objects.car.Orientation;
import util.rocket_league.physics.aerial_dynamics.AerialInputs;

import java.util.ArrayList;
import java.util.List;

public class DeltaVelocityOfRotatingBoost {
    public static List<Vector3> compute(final Orientation initialOrientation, final Vector3 initialAngularVelocity, final AerialInputs aerialInputs, final double dt, final double finalTime) {
        final List<Vector3> deltaVelocities = new ArrayList<>();
        deltaVelocities.add(new Vector3());

        final List<Vector3> accelerations = AccelerationsOfRotatingBoost
                .compute(initialOrientation, initialAngularVelocity, aerialInputs, dt, finalTime);

        for(int i = 1; i < accelerations.size(); i++) {
            deltaVelocities.add(deltaVelocities.get(i-1).plus(accelerations.get(i-1).scaled(dt)));
        }

        return deltaVelocities;
    }
}
