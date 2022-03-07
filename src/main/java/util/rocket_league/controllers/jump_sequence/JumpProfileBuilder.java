package util.rocket_league.controllers.jump_sequence;

import util.math.vector.Vector2;
import util.rocket_league.controllers.flip.FlipController;
import util.rocket_league.controllers.flip.FlipType;

public class JumpProfileBuilder {
    private int initialImpulse;
    private FlipType flipType;
    private Vector2 flipDirection;

    public JumpProfileBuilder() {
        this.initialImpulse = 0;
        this.flipType = FlipType.NONE;
        this.flipDirection = FlipController.DEFAULT_FLIP_DIRECTION;
    }

    public JumpProfileBuilder withInitialImpulse(final int initialImpulseImpulse) {
        this.initialImpulse = initialImpulseImpulse;
        return this;
    }

    public JumpProfileBuilder withFlipType(final FlipType flipType) {
        this.flipType = flipType;
        return this;
    }

    public JumpProfileBuilder withFlipDirection(final Vector2 flipDirection) {
        this.flipDirection = flipDirection;
        return this;
    }

    public JumpProfile build() {
        return new JumpProfile(initialImpulse, flipType, flipDirection);
    }
}
