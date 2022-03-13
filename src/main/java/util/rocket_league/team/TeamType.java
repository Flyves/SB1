package util.rocket_league.team;

import util.math.vector.Vector3;

public enum TeamType {
    BLUE,
    ORANGE;

    public static Vector3 fieldDirection(final TeamType teamType) {
        return teamType == TeamType.BLUE ? new Vector3(0, -1, 0) : new Vector3(0, 1, 0);
    }
}
