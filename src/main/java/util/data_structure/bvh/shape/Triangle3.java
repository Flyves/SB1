package util.data_structure.bvh.shape;

import util.data_structure.bvh.bounding_volume_hierarchy.AxisAlignedBoundingBox;
import util.data_structure.bvh.math.Vector3;
import util.shape.Triangle3D;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public Triangle3D toShapeTriangle3D() {
        final util.math.vector.Vector3 p0 = point0.toMathVector3();
        final util.math.vector.Vector3 p1 = point1.toMathVector3();
        final util.math.vector.Vector3 p2 = point2.toMathVector3();
        return new Triangle3D(p0, p1, p2);
    }

    public Set<Sphere> asEdgeCollisionSpheres() {
        final Set<Sphere> collisionSpheres = new HashSet<>();
        collisionSpheres.add(new Sphere(point0.plus(point1).scaled(0.5), point0.distance(point1)*0.5));
        collisionSpheres.add(new Sphere(point0.plus(point2).scaled(0.5), point0.distance(point2)*0.5));
        collisionSpheres.add(new Sphere(point1.plus(point2).scaled(0.5), point1.distance(point2)*0.5));
        return collisionSpheres;
    }

    public List<Vector3> asVertexList() {
        final List<Vector3> vertices = new ArrayList<>();
        vertices.add(point0);
        vertices.add(point1);
        vertices.add(point2);
        return vertices;
    }

    @Override
    public boolean equals(final Object that) {
        if(!(that instanceof Triangle3)) return false;
        final Triangle3 thatTriangle = (Triangle3) that;
        if(thatTriangle.point0.distanceSquared(point0) > 0.01) return false;
        if(thatTriangle.point1.distanceSquared(point1) > 0.01) return false;
        return thatTriangle.point2.distanceSquared(point2) <= 0.01;
    }
}
