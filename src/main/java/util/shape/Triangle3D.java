package util.shape;

import util.data_structure.bvh.shape.Sphere;
import util.math.vector.Vector3;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Triangle3D implements Serializable {
    public final Vector3 point0;
    public final Vector3 point1;
    public final Vector3 point2;
    public final Vector3 normal;
    public final Vector3 centerPoint;

    public Triangle3D() {
        final Vector3 defaultVertex = new Vector3();
        this.point0 = defaultVertex;
        this.point1 = defaultVertex;
        this.point2 = defaultVertex;
        this.normal = defaultVertex;
        this.centerPoint = defaultVertex;
    }

    public Triangle3D(final Vector3 point0, final Vector3 point1, final Vector3 point2) {
        this.point0 = point0;
        this.point1 = point1;
        this.point2 = point2;
        this.normal = computeNormal();
        this.centerPoint = computeCenterPoint();

    }

    public Vector3 getNormal() {
        return normal;
    }

    public Vector3 getCenterPosition() {
        return centerPoint;
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
}
