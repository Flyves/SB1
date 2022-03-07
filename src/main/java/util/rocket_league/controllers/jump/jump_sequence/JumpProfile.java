package util.rocket_league.controllers.jump.jump_sequence;

import util.math.vector.Vector2;
import util.rocket_league.controllers.jump.second.SecondJumpType;

public class JumpProfile {
    public final int initialImpulse;
    public final SecondJumpType secondJumpType;
    public final Vector2 flipDirection;

    public JumpProfile(final int initialImpulse, final SecondJumpType secondJumpType, final Vector2 flipDirection) {
        this.initialImpulse = initialImpulse;
        this.secondJumpType = secondJumpType;
        this.flipDirection = flipDirection;
    }
}
