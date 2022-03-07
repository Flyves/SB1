package util.rocket_league.physics.aerial_dynamics.orientation_equations;

import util.math.vector.Vector3;
import util.rocket_league.physics.aerial_dynamics.AerialDampening;
import util.rocket_league.physics.aerial_dynamics.AerialInputs;

import java.util.function.Function;

import static util.rocket_league.physics.aerial_dynamics.AerialInputs.asLocalAccelerationWithoutDampening;

public class ApproximateRelativeAngularVelocity {
    public static Vector3 compute(final Vector3 localInitialAngularVelocity, final AerialInputs aerialInputs, final double dt) {
        final Function<AerialInputs, Vector3> dampeningAccelerationFunction = AerialDampening.getRelativeDampeningAccelerationScalarFunction();
        final Vector3 accelerationFromInputsOnly = asLocalAccelerationWithoutDampening(aerialInputs);
        final Vector3 dampeningAcceleration = dampeningAccelerationFunction.apply(aerialInputs);
        final Vector3 accelerationConstant = accelerationFromInputsOnly.scaled(dampeningAcceleration.inverse());
        final Vector3 C1 = localInitialAngularVelocity.plus(accelerationConstant);
        final Vector3 exponentialTerm = C1.scaled(
                fastExp(dampeningAcceleration.x * dt),
                fastExp(dampeningAcceleration.y * dt),
                fastExp(dampeningAcceleration.z * dt));
        return exponentialTerm.minus(accelerationConstant);
    }

    // from https://www.javamex.com/tutorials/math/exp.shtml
    private static double fastExp(double x) {
        x = 1d + x / 256d;
        x *= x; x *= x; x *= x; x *= x;
        x *= x; x *= x; x *= x; x *= x;
        return x;
    }
}
