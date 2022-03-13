package util.rocket_league.dynamic_objects.boost;

import org.jgrapht.alg.shortestpath.AStarShortestPath;
import util.math.vector.Vector3;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

public class BoostPadPathGenerator {
    public static LinkedHashSet<Vector3> generatePath(final BoostPad boostPadSource, final BoostPad boostPadTarget) {
        return new AStarShortestPath<>(
                BoostPadManager.boostPadGraphWrapper.getGraph(),
                BoostPadPathGenerator::AStarHeuristic)
                .getPath(boostPadSource.boostId, boostPadTarget.boostId)
                .getVertexList().stream()
                .map(BoostPadManager.boostPads::get)
                .map(boostPad -> boostPad.location)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private static double AStarHeuristic(final Integer i, final Integer j) {
        return BoostPadManager.boostPads.get(i).location
                .distance(BoostPadManager.boostPads.get(j).location);
    }
}
