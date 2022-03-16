package util.rocket_league.dynamic_objects.ball;

import rlbot.cppinterop.RLBotDll;
import rlbot.cppinterop.RLBotInterfaceException;
import rlbot.flat.PredictionSlice;
import util.rocket_league.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public Stream<BallData> filterBallBounces() {
        return stream().filter(ballData -> {
            final int index = indexOf(ballData);
            if(index >= size()-1) {
                return false;
            }
            final int nextIndex = index + 1;
            final BallData nextBallData = get(nextIndex);
            return nextBallData.velocity.minus(ballData.velocity).scaled(Constants.BALL_PREDICTION_REFRESH_RATE)
                    .minus(Constants.GRAVITY_VECTOR)
                    .magnitudeSquared() > 100 * 100;
        });
    }
}
