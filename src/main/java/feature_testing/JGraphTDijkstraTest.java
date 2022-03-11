package feature_testing;

import org.jgrapht.Graph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.Multigraph;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.List;

public class JGraphTDijkstraTest {
    public static void main(final String [] args) {
        final SimpleWeightedGraph<String, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        graph.addVertex("v1");
        graph.addVertex("v2");
        graph.addVertex("v3");
        graph.addVertex("v4");
        graph.setEdgeWeight(graph.addEdge("v1", "v2"), 3);
        graph.setEdgeWeight(graph.addEdge("v1", "v3"), 4);
        graph.setEdgeWeight(graph.addEdge("v2", "v4"), 1000);
        graph.setEdgeWeight(graph.addEdge("v3", "v4"), 20);

        DijkstraShortestPath<String, DefaultWeightedEdge> dijkstraShortestPath =
                new DijkstraShortestPath<>(graph, "v1", "v4");
        List<DefaultWeightedEdge> shortestPath = dijkstraShortestPath.getPathEdgeList();
    }
}
