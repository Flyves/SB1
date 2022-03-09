package util.data_structure.bvh.shape;

import util.data_structure.bvh.bounding_volume_hierarchy.AxisAlignedBoundingBox;
import util.data_structure.bvh.math.Vector3;

import java.io.Serializable;

/**
 * A triangle, represented in 3 dimensional space.
 */
public class Triangle3 implements Serializable {
    public final Vector3 point0;
    public final Vector3 point1;
    public final Vector3 point2;
    public final Vector3 normal;
    public final Vector3 centerPoint;

    public Triangle3() {
        final Vector3 defaultVertex = new Vector3();
        this.point0 = defaultVertex;
        this.point1 = defaultVertex;
        this.point2 = defaultVertex;
        this.normal = defaultVertex;
        this.centerPoint = defaultVertex;
    }

    public Triangle3(final Vector3 point0, final Vector3 point1, final Vector3 point2) {
        this.point0 = point0;
        this.point1 = point1;
        this.point2 = point2;
        this.normal = computeNormal();
        this.centerPoint = computeCenterPoint();

    }

    private Vector3 computeNormal() {
        final Vector3 vectoredEdge0 = point0.minus(point1);
        final Vector3 vectoredEdge1 = point1.minus(point2);
        final Vector3 notNormalizedNormal = vectoredEdge0.crossProduct(vectoredEdge1);

        return notNormalizedNormal.normalized();
    }

    private Vector3 computeCenterPoint() {
        return point0.plus(point1).plus(point2)
                .scaled(0.333333333333);
    }

    public AxisAlignedBoundingBox toAabb() {
        return new AxisAlignedBoundingBox(
                new Vector3(
                        min(point0.x, point1.x, point2.x),
                        min(point0.y, point1.y, point2.y),
                        min(point0.z, point1.z, point2.z)),
                new Vector3(
                        max(point0.x, point1.x, point2.x),
                        max(point0.y, point1.y, point2.y),
                        max(point0.z, point1.z, point2.z)));
    }

    private double min(double x, double y, double z) {
        return Math.min(x, Math.min(y, z));
    }

    private double max(double x, double y, double z) {
        return Math.max(x, Math.max(y, z));
    }
}
