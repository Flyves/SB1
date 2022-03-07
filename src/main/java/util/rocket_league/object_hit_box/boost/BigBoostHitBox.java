package util.rocket_league.object_hit_box.boost;

import util.rocket_league.object_hit_box.HitCylinder;
import util.math.vector.Ray3;
import util.math.vector.Vector3;

public class BigBoostHitBox extends HitCylinder {

    public static final double HEIGHT = 168;
    public static final double RADII = 208;

    public BigBoostHitBox(Vector3 position) {
        super(new Ray3(position, new Vector3(0, 0, HEIGHT)), RADII);
    }
}
