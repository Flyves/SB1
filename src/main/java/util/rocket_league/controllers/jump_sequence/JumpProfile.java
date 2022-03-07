package util.rocket_league.controllers.jump_sequence;

import util.math.vector.Vector2;
import util.rocket_league.controllers.flip.FlipType;

public class JumpProfile {
    public final int initialImpulse;
    public final FlipType flipType;
    public final Vector2 flipDirection;

    public JumpProfile(final int initialImpulse, final FlipType flipType, final Vector2 flipDirection) {
        this.initialImpulse = initialImpulse;
        this.flipType = flipType;
        this.flipDirection = flipDirection;
    }
}
