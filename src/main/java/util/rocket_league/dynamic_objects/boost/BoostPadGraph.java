package util.rocket_league.dynamic_objects.boost;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.List;

public class BoostPadGraph {
    private SimpleWeightedGraph<BoostPad, DefaultWeightedEdge> boostPadGraph;

    public BoostPadGraph() {
        this.boostPadGraph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
    }

    public void update(final List<BoostPad> boostPadList, final double greedinessFactor) {
        // clear the graph
        boostPadGraph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        // add new vertices
        for(final BoostPad boostPad : boostPadList) {
            // don't add taken pads
            if (!boostPad.isActive) {
                //continue;
            }
            boostPadGraph.addVertex(boostPad);
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
                boostPadGraph.addEdge(boostPad1, boostPad2);
            }
        }

        // compute graph weights
        boostPadGraph.edgeSet().forEach(edge -> {
            final BoostPad edgeSource = boostPadGraph.getEdgeSource(edge);
            final BoostPad edgeTarget = boostPadGraph.getEdgeTarget(edge);
            //final double d2 = edgeSource.location.distance(edgeTarget.location);
            double greedinessFactorCopy = greedinessFactor;
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

    public SimpleWeightedGraph<BoostPad, DefaultWeightedEdge> getGraph() {
        return boostPadGraph;
    }
}
