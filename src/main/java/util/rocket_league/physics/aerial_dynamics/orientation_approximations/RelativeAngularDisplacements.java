package util.rocket_league.physics.aerial_dynamics.orientation_approximations;

import util.math.vector.Vector3;
import util.rocket_league.Constants;
import util.rocket_league.dynamic_objects.car.Orientation;
import util.rocket_league.physics.aerial_dynamics.AerialInputs;
import util.rocket_league.physics.aerial_dynamics.orientation_equations.RelativeAngularVelocity;

import java.util.ArrayList;
import java.util.List;

public class RelativeAngularDisplacements {
    public static List<Orientation> compute(final Vector3 localInitialAngularVelocity, final AerialInputs aerialInputs, final double dt, final double finalTime) {
        // we need to compute both the orientation and the velocity over time to make a decent approximation
        final List<Orientation> localOrientationList = new ArrayList<>();
        final List<Vector3> localAngularVelocityList = new ArrayList<>();

        // initial parameters
        localOrientationList.add(new Orientation());
        localAngularVelocityList.add(localInitialAngularVelocity);

        // loop to fill the lists
        final int amountOfIterations = (int)(Constants.BOT_REFRESH_RATE * finalTime);
        for(int i = 1; i < amountOfIterations; i++) {
            // updating the orientation list first
            final Orientation previousOrientation = localOrientationList.get(i-1);
            final Vector3 previousLocalAngularVelocity = localAngularVelocityList.get(i-1);
            localOrientationList.add(previousOrientation.rotate(previousLocalAngularVelocity.scaled(dt)));

            // then updating the angular velocity list to compute the next orientation on the next frame
            final Vector3 currentLocalAngularVelocity = previousLocalAngularVelocity
                    .rotateToOrientationReferenceFrom(previousOrientation);
            final Vector3 nextLocalAngularVelocity = RelativeAngularVelocity
                    .compute(currentLocalAngularVelocity, aerialInputs, dt);
            localAngularVelocityList.add(nextLocalAngularVelocity
                    .rotateFromOrientationReferenceTo(previousOrientation).capped(Constants.CAR_MAX_SPIN));
        }

        return localOrientationList;
    }
}
