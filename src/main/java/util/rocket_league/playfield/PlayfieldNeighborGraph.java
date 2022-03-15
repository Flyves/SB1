package util.rocket_league.playfield;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import util.data_structure.bvh.math.Vector3;
import util.file.ObjectStreaming;

import javax.xml.ws.Holder;
import java.io.File;
import java.util.Optional;

public class PlayfieldNeighborGraph {
    final Holder<SimpleWeightedGraph<Vector3, DefaultWeightedEdge>> graphHolder;

    public PlayfieldNeighborGraph() {
        graphHolder = backgroundLoadPlayfieldGraph();
    }

    private static Holder<SimpleWeightedGraph<Vector3, DefaultWeightedEdge>> backgroundLoadPlayfieldGraph() {
        final Holder<SimpleWeightedGraph<Vector3, DefaultWeightedEdge>> bgLoadedGraph = new Holder<>();
        new Thread(() -> bgLoadedGraph.value = ObjectStreaming.load(new File("src\\main\\resources\\graphs\\standard_playfield_neighbor_graph.obs")))
                .start();
        return bgLoadedGraph;
    }

    public Optional<SimpleWeightedGraph<Vector3, DefaultWeightedEdge>> getGraph() {
        return Optional.ofNullable(graphHolder.value);
    }

    public boolean isLoaded() {
        return graphHolder.value != null;
    }
}
