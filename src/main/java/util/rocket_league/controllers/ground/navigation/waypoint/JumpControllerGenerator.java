package util.rocket_league.controllers.ground.navigation.waypoint;

import util.math.vector.Vector2;
import util.math.vector.Vector3;
import util.rocket_league.controllers.jump.jump_sequence.JumpController;
import util.rocket_league.controllers.jump.jump_sequence.JumpProfileBuilder;
import util.rocket_league.controllers.jump.second.SecondJumpType;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;

class JumpControllerGenerator {
    static JumpController generateTheRightJumpController(
            final SecondJumpType secondJumpType,
            final ExtendedCarData car,
            final Vector3 activeWaypoint) {
        final Vector2 flipDirection = activeWaypoint.minus(car.position)
                .rotate(car.orientation.asAngularDisplacement().scaled(-1)).flatten().normalized().flip();
        switch (secondJumpType) {
            case FLIP: return new JumpController(new JumpProfileBuilder()
                    .withInitialImpulse(450)
                    .withSecondJumpType(SecondJumpType.FLIP)
                    .withFlipDirection(flipDirection)
                    .build());
            case PARTIAL_CANCEL: return new JumpController(new JumpProfileBuilder()
                    .withInitialImpulse(400)
                    .withSecondJumpType(SecondJumpType.PARTIAL_CANCEL)
                    .withFlipDirection(flipDirection)
                    .build());
            case WAVE_DASH: return new JumpController(new JumpProfileBuilder()
                    .withInitialImpulse(0)
                    .withSecondJumpType(SecondJumpType.WAVE_DASH)
                    .withFlipDirection(flipDirection)
                    .build());
            case DOUBLE_WAVE_DASH: return new JumpController(new JumpProfileBuilder()
                    .withInitialImpulse(0)
                    .withSecondJumpType(SecondJumpType.DOUBLE_WAVE_DASH)
                    .withFlipDirection(flipDirection)
                    .build());
            default: return new JumpController(new JumpProfileBuilder().build());
        }
    }
}
