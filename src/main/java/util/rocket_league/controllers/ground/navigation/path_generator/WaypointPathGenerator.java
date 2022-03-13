package util.rocket_league.controllers.ground.navigation.path_generator;

import org.jgrapht.alg.shortestpath.AStarShortestPath;
import util.data_structure.bvh.shape.Sphere;
import util.data_structure.bvh.shape.Triangle3;
import util.math.vector.Ray3;
import util.math.vector.Vector3;
import util.renderers.RenderTasks;
import util.renderers.ShapeRenderer;
import util.rocket_league.io.input.DataPacket;

import java.awt.*;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

public class WaypointPathGenerator {
    public static LinkedHashSet<Vector3> generateGroundPathBetween(final Vector3 source, final Vector3 target, final double radiiSearch) {
        final Optional<Triangle3> sourceWaypointOpt = getClosestTriangleOnMap(source, radiiSearch);
        if(!sourceWaypointOpt.isPresent()) throw new NoSuchPlayfieldCollisionException();
        final Optional<Triangle3> targetWaypointOpt = getClosestTriangleOnMap(target, radiiSearch);
        if(!targetWaypointOpt.isPresent()) throw new NoSuchPlayfieldCollisionException();

        final Triangle3 sourceWaypoint = sourceWaypointOpt.get();
        final Triangle3 targetWaypoint = targetWaypointOpt.get();

        long x1 = System.nanoTime();
        DataPacket.PLAYFIELD_GRAPH.getGraph().ifPresent(graph -> {
            final List<Triangle3> path = new AStarShortestPath<>(graph, (t1, t2) -> t1.centerPoint.distance(t2.centerPoint))
                    .getPath(sourceWaypoint, targetWaypoint)
                    .getVertexList();
            RenderTasks.append(r -> {
                final ShapeRenderer shapeRenderer = new ShapeRenderer(r);
                path.forEach(triangle -> shapeRenderer.renderTriangle(triangle.toShapeTriangle3D(), Color.cyan));
            });
        });
        long x2 = System.nanoTime();
        System.out.println((x2 - x1)/1000000.0);
        return null;
    }

    private static Optional<Triangle3> getClosestTriangleOnMap(final Vector3 position, final double radiiSearch) {
        final List<Triangle3> fromCollision = DataPacket.PLAYFIELD_BVH.query(new Sphere(
                new util.data_structure.bvh.math.Vector3(position),
                radiiSearch));
        if(!DataPacket.PLAYFIELD_GRAPH.isLoaded()) {
            return Optional.empty();
        }
        return fromCollision.stream()
                .map(triangle -> DataPacket.PLAYFIELD_GRAPH.getGraph().get().vertexSet().stream().filter(triangle::equals).findFirst().get())
                .reduce((triangle1, triangle2) -> position.distanceSquared(triangle1.toShapeTriangle3D()) < position.distanceSquared(triangle2.toShapeTriangle3D()) ? triangle1 : triangle2);
    }
}
