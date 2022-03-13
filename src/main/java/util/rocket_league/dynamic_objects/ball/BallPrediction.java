package util.rocket_league.dynamic_objects.ball;

import rlbot.cppinterop.RLBotDll;
import rlbot.cppinterop.RLBotInterfaceException;
import rlbot.flat.PredictionSlice;

import java.util.ArrayList;
import java.util.Optional;

public class BallPrediction extends ArrayList<BallData> {
    public BallPrediction() {
        super();
        final Optional<rlbot.flat.BallPrediction> baseImplementationOpt = getBaseImplementation();
        baseImplementationOpt.ifPresent(baseImplementation -> {
            for(int i = 0; i < baseImplementation.slicesLength(); i++) {
                final PredictionSlice predictionSlice = baseImplementation.slices(i);
                add(new BallData(predictionSlice));
            }
        });
    }

    private static Optional<rlbot.flat.BallPrediction> getBaseImplementation() {
        try {
            return Optional.of(RLBotDll.getBallPrediction());
        } catch (RLBotInterfaceException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
