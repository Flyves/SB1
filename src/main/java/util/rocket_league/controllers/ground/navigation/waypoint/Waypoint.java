package util.rocket_league.controllers.ground.navigation.waypoint;

import util.math.vector.Vector3;
import util.rocket_league.dynamic_objects.car.ExtendedCarData;

import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class Waypoint {
    public final Vector3 position;
    public final Function<ExtendedCarData, Boolean> collisionFunction;

    public Waypoint(final Vector3 position, final BiFunction<ExtendedCarData, Vector3, Boolean> collisionFunction) {
        this.position = position;
        this.collisionFunction = car -> collisionFunction.apply(car, position);
    }
}
