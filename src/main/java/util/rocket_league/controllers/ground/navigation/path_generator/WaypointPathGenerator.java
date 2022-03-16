package util.rocket_league.controllers.ground.navigation.path_generator;

import util.data_structure.bvh.shape.Sphere;
import util.data_structure.bvh.shape.Triangle3;
import util.math.vector.Ray3;
import util.math.vector.Vector3;
import util.rocket_league.controllers.ground.navigation.waypoints.DefaultPlayfieldWaypoint;
import util.rocket_league.controllers.ground.navigation.waypoints.PlayfieldSurfaceWaypoint;
import util.rocket_league.controllers.ground.navigation.waypoints.Waypoint;
import util.rocket_league.io.input.DataPacket;
import util.shape.Triangle3D;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

public class WaypointPathGenerator {
    public static LinkedHashSet<Waypoint> generateGroundPathBetween(final Vector3 source, final Vector3 target, final double radiiSearch) {
        final Optional<Ray3> sourceWaypointOpt = getClosestSurfaceWaypoint(source, radiiSearch);
        if(!sourceWaypointOpt.isPresent()) throw new NoSuchPlayfieldCollisionException();
        final Optional<Ray3> targetWaypointOpt = getClosestSurfaceWaypoint(target, radiiSearch);
        if(!targetWaypointOpt.isPresent()) throw new NoSuchPlayfieldCollisionException();

        final Ray3 sourceWaypoint = sourceWaypointOpt.get();
        final Ray3 targetWaypoint = targetWaypointOpt.get();

        // add source waypoint
        final LinkedHashSet<Waypoint> waypoints = new LinkedHashSet<>();
        waypoints.add(new PlayfieldSurfaceWaypoint(sourceWaypoint));

        // add in-between waypoints
        if(needsAdditionalWaypoint(sourceWaypoint, targetWaypoint)) {
            final Waypoint intermediateWaypoint = computeIntermediateWaypointToCrossMapBorder(sourceWaypoint, targetWaypoint);
            waypoints.add(intermediateWaypoint);
        }

        // add target waypoint
        waypoints.add(new PlayfieldSurfaceWaypoint(targetWaypoint));
        return waypoints;
    }

    private static Waypoint computeIntermediateWaypointToCrossMapBorder(Ray3 sourceWaypoint, Ray3 targetWaypoint) {
        final Vector3 targetToSource = sourceWaypoint.offset.minus(targetWaypoint.offset);
        final Vector3 aVec = targetToSource.flatten(targetWaypoint.direction);
        final Vector3 bVec = targetToSource.projectOnto(sourceWaypoint.direction);
        final Vector3 cVec = targetToSource.projectOnto(targetWaypoint.direction);
        final double b2 = bVec.magnitudeSquared();
        final double b = Math.sqrt(b2);
        final double c = cVec.magnitude();
        final double e2 = aVec.magnitudeSquared() - b2;
        final double h2 = squared(b + c) + e2;
        final double h = Math.sqrt(h2);
        final double j = h*(b / (b + c));
        final double f2 = squared(j) - b2;
        final double f = Math.sqrt(f2);
        final Vector3 fVec = targetToSource.projectOnto(sourceWaypoint.direction.crossProduct(targetWaypoint.direction))
                .scaledToMagnitude(f);
        final Vector3 outsideOfMapIntermediateWaypoint = targetWaypoint.offset.plus(bVec).plus(fVec);
        final Vector3 offsetDirection = sourceWaypoint.direction.plus(targetWaypoint.direction).normalized();
        final double querySphereRadii = 100;
        final Vector3 notExactlyOnMapIntermediatePoint = outsideOfMapIntermediateWaypoint.plus(offsetDirection.scaled(querySphereRadii));
        return new DefaultPlayfieldWaypoint(notExactlyOnMapIntermediatePoint);
    }

    private static double squared(final double x) {
        return x*x;
    }

    private static boolean needsAdditionalWaypoint(final Ray3 sourceWaypoint, final Ray3 targetWaypoint) {
        return angleBetweenNormals(sourceWaypoint, targetWaypoint) > Math.PI * 0.125;
    }

    private static double angleBetweenNormals(final Ray3 sourceWaypoint, final Ray3 targetWaypoint) {
        return sourceWaypoint.direction.angle(targetWaypoint.direction);
    }

    private static Optional<Ray3> getClosestSurfaceWaypoint(final Vector3 position, final double radiiSearch) {
        final List<Triangle3> collisionTriangles = DataPacket.PLAYFIELD_BVH.query(new Sphere(
                new util.data_structure.bvh.math.Vector3(position),
                radiiSearch));
        final Optional<Triangle3D> triangleOpt = collisionTriangles.stream()
                .map(Triangle3::toShapeTriangle3D)
                .reduce((t1, t2) -> position.distanceSquared(t1) < position.distanceSquared(t2) ? t1 : t2);
        return triangleOpt
                .map(t -> new Ray3(position.projectOnto(t), t.getNormal()));
    }
}
