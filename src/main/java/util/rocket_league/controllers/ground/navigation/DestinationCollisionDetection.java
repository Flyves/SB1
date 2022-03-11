package util.rocket_league.controllers.ground.navigation;

import util.math.vector.Vector3;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;

import java.util.function.BiFunction;

public class DestinationCollisionDetection {
    public static BiFunction<ExtendedCarData, Vector3, Boolean> DEFAULT_COLLISION_DETECTION_FUNCTION =
            (car, position) -> position.distanceSquared(car.position) < 100 * 100;
}
