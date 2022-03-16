package bot.flyve.states.kickoff.states;

import util.math.vector.Vector3;
import util.rocket_league.io.input.DataPacket;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class Middle extends BaseState {
    @Override
    public void start(final DataPacket input) {
        List<Vector3> waypointList = new ArrayList<>(generateBasicBoostPath(input));
        if(waypointList.size() >= 3) {
            waypointList.remove(2);
        }
        waypoints = new LinkedHashSet<>(waypointList);
        super.start(input);
    }
}
