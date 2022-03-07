package util.rocket_league.physics.aerial_dynamics;

import util.math.vector.Vector3;

import java.util.function.Function;

public class AerialDampening {

    /** This result is based on a few samples and the trendline algorithm from excel.
     */
    // The scalars are in radians/(seconds * frames), this is why they are scaled by 60 (the sampling was done @60fps).
    private static final Vector3 VECTOR_VALUE = new Vector3(-0.07723, -0.04966, -0.03244).scaled(60);

    // The scaling function of the dampening scalar was based on the dampening matrix "D" from Samuel Mish's notes:
    // https://samuelpmish.github.io/notes/RocketLeague/aerial_control/
    public static Function<AerialInputs, Vector3> getRelativeDampeningAccelerationScalarFunction() {

        return aerialInputs -> {
            final Vector3 cappedInputs = AerialInputs.asCappedVector(aerialInputs);
            return VECTOR_VALUE.scaled(1, 1 - Math.abs(cappedInputs.y), 1 - Math.abs(cappedInputs.z));
        };
    }
}
