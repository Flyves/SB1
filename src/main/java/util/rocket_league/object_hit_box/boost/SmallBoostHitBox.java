package util.rocket_league.object_hit_box.boost;

import util.rocket_league.object_hit_box.HitCylinder;
import util.math.vector.Ray3;
import util.math.vector.Vector3;

public class SmallBoostHitBox extends HitCylinder {

    public static final double HEIGHT = 165;
    public static final double RADII = 144;

    public SmallBoostHitBox(Vector3 position) {
        super(new Ray3(position, new Vector3(0, 0, HEIGHT)), RADII);
    }
}
