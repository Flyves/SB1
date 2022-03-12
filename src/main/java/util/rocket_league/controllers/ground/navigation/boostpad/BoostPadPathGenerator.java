package util.rocket_league.controllers.ground.navigation.boostpad;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import util.math.vector.Vector3;
import util.rocket_league.dynamic_objects.boost.BoostPad;
import util.rocket_league.dynamic_objects.boost.BoostPadManager;

import javax.xml.ws.Holder;
import java.util.LinkedHashSet;
import java.util.List;

public class BoostPadPathGenerator {
    public static LinkedHashSet<Vector3> generatePath(final BoostPad boostPadSource, final BoostPad boostPadTarget) {
        final LinkedHashSet<Vector3> path = new LinkedHashSet<>();

        final List<DefaultWeightedEdge> edges = new DijkstraShortestPath<>(
                BoostPadManager.boostPadGraphWrapper.getGraph(),
                boostPadSource,
                boostPadTarget)
                .getPathEdgeList();
        // add source and remember it
        path.add(boostPadSource.location);
        Holder<Vector3> nextPosition = new Holder<>(boostPadSource.location);
        // fill the rest of the path
        edges.forEach(edge -> {
            final BoostPad source = BoostPadManager.boostPadGraphWrapper.getGraph().getEdgeSource(edge);
            final BoostPad target = BoostPadManager.boostPadGraphWrapper.getGraph().getEdgeTarget(edge);
            // if the edge's source is the previous location we just added, then the edge's target is our next location.
            // we can therefore add the edge's target, like we would expect.
            if(nextPosition.value.equals(source.location)) {
                nextPosition.value = target.location;
                path.add(nextPosition.value);
            }
            // else, we should add the source instead.
            else {
                nextPosition.value = source.location;
                path.add(nextPosition.value);
            }
        });

        return path;
    }
}
