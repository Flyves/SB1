package util.rocket_league.physics.aerial_dynamics.boost_rotation_displacement_approximations;

import util.math.vector.Vector3;
import util.rocket_league.Constants;
import util.rocket_league.dynamic_objects.car.Orientation;
import util.rocket_league.physics.aerial_dynamics.AerialInputs;
import util.rocket_league.physics.aerial_dynamics.orientation_approximations.RelativeAngularDisplacements;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AccelerationsOfRotatingBoost {
    public static List<Vector3> compute(final Orientation initialOrientation, final Vector3 initialAngularVelocity, final AerialInputs aerialInputs, final double dt, final double finalTime) {
        final Vector3 localInitialAngularVelocity = initialAngularVelocity.rotateToOrientationReferenceFrom(initialOrientation);
        final Vector3 initialAngularDisplacement = initialOrientation.asAngularDisplacement();

        final List<Orientation> relativeAngularDisplacements = RelativeAngularDisplacements.compute(localInitialAngularVelocity, aerialInputs, dt, finalTime);

        final List<Vector3> accelerations = new ArrayList<>();

        for(int i = 0; i < relativeAngularDisplacements.size(); i++) {
            accelerations.add(relativeAngularDisplacements.get(i).noseVector.rotate(initialAngularDisplacement).scaled(Constants.ACCELERATION_DUE_TO_BOOST_IN_AIR));
        }

        return accelerations;
    }
}
