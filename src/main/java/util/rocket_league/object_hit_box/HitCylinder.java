package util.rocket_league.object_hit_box;

import util.math.vector.Ray3;

public class HitCylinder {

    private Ray3 shapeDescriptor;
    double radii;

    public HitCylinder(Ray3 shapeDescriptor, double radii) {
        this.shapeDescriptor = shapeDescriptor;
        this.radii = radii;
    }


}
