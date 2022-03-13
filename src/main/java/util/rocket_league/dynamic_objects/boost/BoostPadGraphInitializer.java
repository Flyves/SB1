package util.rocket_league.dynamic_objects.boost;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.List;

public class BoostPadGraphInitializer {
    /**
     * You should try to keep the greediness between 1 and 10 for useful results.
     * - 1  = "You can skip this boost pad? Skip it".
     * - 10 = "Is this boostpad vaguely going in the right direction? Take it".
     */
    private static final double BOOSTPAD_GREEDINESS = 1.1;

    public static void init(
            final List<BoostPad> boostPadList,
            final SimpleWeightedGraph<Integer, DefaultWeightedEdge> boostPadGraph) {
        // add new vertices
        for(final BoostPad boostPad : boostPadList) {
            boostPadGraph.addVertex(boostPad.boostId);
        }

        // add new edges
        for(final BoostPad boostPad1 : boostPadList) {
            for(final BoostPad boostPad2 : boostPadList) {
                if(boostPad1 == boostPad2) {
                    continue;
                }
                if(boostPad1.location.distanceSquared(boostPad2.location) > 4000 * 4000) {
                    //continue;
                }
                boostPadGraph.addEdge(boostPad1.boostId, boostPad2.boostId);
            }
        }

        // compute graph weights
        boostPadGraph.edgeSet().forEach(edge -> {
            final Integer edgeSourceIndex = boostPadGraph.getEdgeSource(edge);
            final Integer edgeTargetIndex = boostPadGraph.getEdgeTarget(edge);
            final BoostPad edgeSource = BoostPadManager.boostPads.get(edgeSourceIndex);
            final BoostPad edgeTarget = BoostPadManager.boostPads.get(edgeTargetIndex);
            double greedinessFactorCopy = BOOSTPAD_GREEDINESS;
            if(greedinessFactorCopy < 1) {
                greedinessFactorCopy = 1;
            }
            if(greedinessFactorCopy > 10) {
                greedinessFactorCopy = 10;
            }
            final double d2 = Math.pow(edgeSource.location.distanceSquared(edgeTarget.location), greedinessFactorCopy/2);
            boostPadGraph.setEdgeWeight(edge, d2);
        });
    }
}
