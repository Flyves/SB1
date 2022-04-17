package util.rocket_league.controllers.ground.navigation.waypoint.seasoner;

import util.data_structure.tupple.Tuple2;
import util.math.vector.Vector3;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;

class JumpCondition {
    public static boolean shouldJump(
            final Tuple2<ExtendedCarData, ControlsOutput> io,
            final Vector3 activeWaypoint,
            final boolean isJumpControllerFinished) {
        return io.value1.hasWheelContact
                && activeWaypoint.distance(io.value1.position) > 2200
                && isJumpControllerFinished
                && io.value1.groundSpeedForward > 800
                && activeWaypoint.minus(io.value1.position).angle(io.value1.velocity) < 0.02
                && activeWaypoint.minus(io.value1.position).angle(io.value1.orientation.nose) < 0.02
                && !io.value1.isSupersonic;
    }
}
