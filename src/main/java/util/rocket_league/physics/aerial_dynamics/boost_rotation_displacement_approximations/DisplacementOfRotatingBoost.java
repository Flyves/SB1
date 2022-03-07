package util.rocket_league.physics.aerial_dynamics.boost_rotation_displacement_approximations;

import util.math.vector.Vector3;
import util.rocket_league.Constants;
import util.rocket_league.dynamic_objects.car.Orientation;
import util.rocket_league.physics.aerial_dynamics.AerialInputs;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DisplacementOfRotatingBoost {
    public static List<Vector3> compute(
            final Vector3 initialVelocity,
            final Orientation initialOrientation,
            final Vector3 initialAngularVelocity,
            final AerialInputs aerialInputs,
            final double dt,
            final double finalTime) {
        final List<Vector3> velocities = VelocityOfRotatingBoost
                .compute(initialVelocity, initialOrientation, initialAngularVelocity, aerialInputs, dt, finalTime);

        final List<Vector3> displacements = new ArrayList<>();
        displacements.add(new Vector3());

        for(int i = 1; i < velocities.size(); i++) {
            displacements.add(displacements.get(i-1).plus(velocities.get(i-1).scaled(dt)));
        }

        return displacements;
    }
}
