package util.rocket_league.physics.aerial_dynamics.orientation_equations;

import util.math.vector.Vector3;
import util.rocket_league.Constants;
import util.rocket_league.physics.aerial_dynamics.AerialInputs;

import java.util.function.Function;


public class CappedRelativeAngularAcceleration {
    public static Function<AerialInputs, Vector3> getFunctionInstance(Vector3 localInitialAngularVelocity) {
        return RelativeAngularAcceleration
                .getFunctionInstance(localInitialAngularVelocity)
                .andThen(angularAcceleration -> {
            final Vector3 updatedAngularVelocity = localInitialAngularVelocity.plus(angularAcceleration.scaled(Constants.BOT_REFRESH_TIME_PERIOD));
            final Vector3 legalUpdatedAngularVelocity = updatedAngularVelocity.capped(Constants.CAR_MAX_SPIN);
            final Vector3 deltaV = legalUpdatedAngularVelocity.minus(localInitialAngularVelocity);
            return deltaV.scaled(Constants.BOT_REFRESH_RATE);
        });
    }
}
