package util.rocket_league.physics.aerial_dynamics.boost_rotation_displacement_approximations;

import util.math.vector.Vector3;
import util.rocket_league.Constants;
import util.rocket_league.dynamic_objects.car.Orientation;
import util.rocket_league.physics.aerial_dynamics.AerialInputs;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class VelocityOfRotatingBoost {
    public static List<Vector3> compute(
            final Vector3 initialVelocity,
            final Orientation initialOrientation,
            final Vector3 initialAngularVelocity,
            final AerialInputs aerialInputs,
            final double dt,
            final double finalTime) {
        final List<Vector3> deltaVelocities = DeltaVelocityOfRotatingBoost
                .compute(initialOrientation, initialAngularVelocity, aerialInputs, dt, finalTime);

        final List<Vector3> velocities = new ArrayList<>();

        for(int i = 0; i < deltaVelocities.size(); i++) {
            final Vector3 velocityFromGravity = Constants.GRAVITY_VECTOR.scaled(i*dt);
            velocities.add(deltaVelocities.get(i).plus(initialVelocity).plus(velocityFromGravity).capped(Constants.CAR_MAX_SPEED));
        }

        return velocities;
    }
}
