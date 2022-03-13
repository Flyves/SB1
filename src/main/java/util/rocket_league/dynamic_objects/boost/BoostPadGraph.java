package util.rocket_league.dynamic_objects.boost;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.List;

public class BoostPadGraph {
    private final SimpleWeightedGraph<Integer, DefaultWeightedEdge> boostPadGraph;

    public BoostPadGraph() {
        this.boostPadGraph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
    }

    public void init(final List<BoostPad> boostPadList) {
        BoostPadGraphInitializer.init(boostPadList, boostPadGraph);
    }

    public SimpleWeightedGraph<Integer, DefaultWeightedEdge> getGraph() {
        return boostPadGraph;
    }

    public boolean notInitialized() {
        return boostPadGraph.vertexSet().isEmpty();
    }
}
