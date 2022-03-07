package util.trajectory;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

public class DiscreteTrajectory<T> {

    public final List<T> curve;

    public DiscreteTrajectory(final List<T> curve) {
        this.curve = curve;
    }

    public Optional<T> closestFrom(final T element, BiFunction<T, T, Double> distanceFunction) {
        return curve.stream().reduce((e1, e2) -> distanceFunction.apply(element, e1) < distanceFunction.apply(element, e2) ? e1 : e2);
    }
}
