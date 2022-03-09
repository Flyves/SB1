package util.data_structure.bvh.shape;

import util.data_structure.bvh.bounding_volume_hierarchy.AxisAlignedBoundingBox;
import util.data_structure.bvh.math.Vector3;

import java.io.Serializable;

public class Sphere implements Serializable {

    public final Vector3 center;
    public final double radii;

    public Sphere(final Vector3 center, final double radii) {
        this.center = center;
        this.radii = Math.abs(radii);
    }

    public boolean contains(final Vector3 point) {
        return center.minus(point).magnitudeSquared() < sq(radii);
    }

    private double sq(double x) {
        return x*x;
    }

    public AxisAlignedBoundingBox toAabb() {
        return new AxisAlignedBoundingBox(
                center.minus(new Vector3(radii, radii, radii)),
                center.plus(new Vector3(radii, radii, radii)));
    }
}
