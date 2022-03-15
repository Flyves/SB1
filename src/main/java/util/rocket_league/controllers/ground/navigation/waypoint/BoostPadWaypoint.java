package util.rocket_league.controllers.ground.navigation.waypoint;

import util.math.vector.Ray3;
import util.math.vector.Vector3;
import util.rocket_league.dynamic_objects.boost.BoostPad;
import util.rocket_league.dynamic_objects.boost.BoostPadManager;

public class BoostPadWaypoint extends PlayfieldWaypoint {
    public BoostPadWaypoint(final BoostPad boostPad) {
        super(new Ray3(boostPad.location, Vector3.UP_VECTOR), ((car, boostPosition) ->
                !BoostPadManager.boostPads.get(boostPad.boostId).isActive));
    }
}
