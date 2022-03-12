package bot.flyve.states.kickoff.states;

import util.math.vector.Vector3;
import util.rocket_league.io.input.DataPacket;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public abstract class MiddleSide extends BaseState {
    @Override
    public void start(final DataPacket input) {
        List<Vector3> waypointList = new ArrayList<>(generateBasicBoostPath(input));
        waypointList.remove(0);
        waypointList.remove(1);
        final Vector3 firstPosition = waypointList.get(0).plus(input.car.position.minus(waypointList.get(0))
                .scaled(1.1, 1, 1).scaledToMagnitude(500));
        waypointList.set(0, firstPosition);
        waypoints = new LinkedHashSet<>(waypointList);
        super.start(input);
    }
}
