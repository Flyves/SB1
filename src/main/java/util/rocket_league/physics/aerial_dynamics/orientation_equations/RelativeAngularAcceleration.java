package util.rocket_league.physics.aerial_dynamics.orientation_equations;

import util.math.vector.Vector3;
import util.rocket_league.physics.aerial_dynamics.AerialDampening;
import util.rocket_league.physics.aerial_dynamics.AerialInputs;
import static util.rocket_league.physics.aerial_dynamics.AerialInputs.asLocalAccelerationWithoutDampening;

import java.util.function.Function;

public class RelativeAngularAcceleration {
    public static Function<AerialInputs, Vector3> getFunctionInstance(Vector3 angularVelocity) {
        final Function<AerialInputs, Vector3> localDampeningAccelerationFunction = AerialDampening.getRelativeDampeningAccelerationScalarFunction()
                .andThen(v -> v.scaled(angularVelocity));

        return aerialInputs -> {
            final Vector3 accelerationFromInputsOnly = asLocalAccelerationWithoutDampening(aerialInputs);
            final Vector3 currentDampeningAcceleration = localDampeningAccelerationFunction.apply(aerialInputs);
            return accelerationFromInputsOnly.plus(currentDampeningAcceleration);
        };
    }
}
