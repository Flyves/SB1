package feature_fidling;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.List;

public class JGraphTDijkstra {
    public static void main(final String [] args) {
        final SimpleWeightedGraph<String, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        graph.addVertex("v1");
        graph.addVertex("v2");
        graph.addVertex("v3");
        graph.addVertex("v4");
        graph.setEdgeWeight(graph.addEdge("v1", "v2"), 10);
        graph.setEdgeWeight(graph.addEdge("v1", "v3"), 5);
        graph.setEdgeWeight(graph.addEdge("v2", "v4"), 1000);
        graph.setEdgeWeight(graph.addEdge("v3", "v4"), 30);

        GraphPath<String, DefaultWeightedEdge> dijkstraShortestPath =
                new DijkstraShortestPath<>(graph).getPath("v1", "v4");
        List<String> shortestPath = dijkstraShortestPath.getVertexList();
        System.out.println(shortestPath);
    }
}
