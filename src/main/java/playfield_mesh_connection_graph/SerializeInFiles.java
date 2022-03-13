package playfield_mesh_connection_graph;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import util.data_structure.bvh.shape.Sphere;
import util.data_structure.bvh.shape.Triangle3;
import util.file.ObjectStreaming;
import util.rocket_league.playfield.Standard;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SerializeInFiles {
    public static void main(final String[] args) {
        final List<Long> timeStamps = new ArrayList<>();


        System.out.println("Computing graph...");
        timeStamps.add(System.currentTimeMillis());

        final SimpleWeightedGraph<Triangle3, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        Standard.BVH.getLeaves().forEach(triangle -> {
            // find the neighbors
            final Set<Sphere> collisionSpheres = triangle.asEdgeCollisionSpheres();
            final Set<Triangle3> potentialNeighbors = new HashSet<>();
            collisionSpheres.forEach(collisionSphere -> potentialNeighbors.addAll(Standard.BVH.query(collisionSphere)));
            final Set<Triangle3> neighbors = potentialNeighbors.stream()
                    .filter(potentialNeighbor -> SerializeInFiles.validateNeighbor(triangle, potentialNeighbor))
                    .collect(Collectors.toSet());

            // add the vertices and the edges
            graph.addVertex(triangle);
            neighbors.forEach(neighbor -> {
                graph.addVertex(neighbor);
                final DefaultWeightedEdge edge1 = graph.addEdge(triangle, neighbor);
                final DefaultWeightedEdge edge2 = graph.addEdge(neighbor, triangle);
                final double d = triangle.centerPoint.distance(neighbor.centerPoint);
                if(edge1 != null) graph.setEdgeWeight(edge1, d);
                if(edge2 != null) graph.setEdgeWeight(edge2, d);
            });
        });


        System.out.println("Saving...");
        timeStamps.add(System.currentTimeMillis());

        ObjectStreaming.save(graph, new File("src\\main\\resources\\graphs\\standard_playfield_neighbor_graph.obs"));


        System.out.println("Loading...");
        timeStamps.add(System.currentTimeMillis());

        final SimpleWeightedGraph<Triangle3, DefaultWeightedEdge> savedGraph = ObjectStreaming.load(new File("src\\main\\resources\\graphs\\standard_playfield_neighbor_graph.obs"));


        timeStamps.add(System.currentTimeMillis());

        System.out.println("Done.\n\n" +
                savedGraph.vertexSet().size() + " vertices\n" +
                savedGraph.edgeSet().size() + " edges\n\n" +
                (timeStamps.get(1) - timeStamps.get(0)) + "ms for computing\n" +
                (timeStamps.get(2) - timeStamps.get(1)) + "ms for saving\n" +
                (timeStamps.get(3) - timeStamps.get(2)) + "ms for loading\n");
    }

    private static boolean validateNeighbor(final Triangle3 graphVertex, final Triangle3 potentialNeighbor) {
        if(graphVertex.equals(potentialNeighbor)) return false;
        return graphVertex.asVertexList().stream()
                .map(v -> v.toMathVector3().distanceSquared(potentialNeighbor.toShapeTriangle3D()))
                .filter(d2 -> d2 < 0.1)
                .count() >= 2;
    }
}
