package util.rocket_league.controllers.jump.jump_sequence;

import util.math.vector.Vector2;
import util.rocket_league.controllers.jump.first.GroundJumpController;
import util.rocket_league.controllers.jump.jump_sequence.states.FirstJump;
import util.rocket_league.controllers.jump.second.SecondJumpController;
import util.rocket_league.controllers.jump.second.SecondJumpType;

public class JumpProfileBuilder {
    private double initialImpulse;
    private SecondJumpType secondJumpType;
    private Vector2 flipDirection;

    public JumpProfileBuilder() {
        this.initialImpulse = 0;
        this.secondJumpType = SecondJumpType.NONE;
        this.flipDirection = SecondJumpController.DEFAULT_FLIP_DIRECTION;
    }

    public JumpProfileBuilder withInitialImpulse(final double initialImpulseImpulse) {
        this.initialImpulse = initialImpulseImpulse;
        return this;
    }

    public JumpProfileBuilder withSecondJumpType(final SecondJumpType secondJumpType) {
        this.secondJumpType = secondJumpType;
        return this;
    }

    public JumpProfileBuilder withFlipDirection(final Vector2 flipDirection) {
        this.flipDirection = flipDirection;
        return this;
    }

    public JumpProfile build() {
        return new JumpProfile(initialImpulse, secondJumpType, flipDirection);
    }
}
