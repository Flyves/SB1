package util.rocket_league.controllers.ground.navigation.waypoint;

import util.data_structure.tupple.Tuple2;
import util.math.vector.Vector3;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;
import util.rocket_league.io.output.ControlsOutput;

class BoostHandler {
    static boolean shouldTryToBoost(
            final Tuple2<ExtendedCarData, ControlsOutput> io,
            final double maxSpeed,
            final double minimumBoostAmount) {
        return !io.value1.isSupersonic
                && io.value1.groundSpeedForward <= maxSpeed - 10
                && minimumBoostAmount < 100;
    }

    public static boolean shouldPreventBoostToKeepTheFlow(
            final Tuple2<ExtendedCarData, ControlsOutput> io,
            final Vector3 activeWaypoint,
            final boolean isJumpControllerFinished,
            final double minimumBoostAmount) {
        return !isJumpControllerFinished
                || activeWaypoint.minus(io.value1.position).angle(io.value1.velocity) > 0.4
                || io.value1.boost <= minimumBoostAmount;
    }
}
