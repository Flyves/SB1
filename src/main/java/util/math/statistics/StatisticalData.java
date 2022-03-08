package util.math.statistics;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StatisticalData<T> {

    private final List<T> statisticalDataList;
    private final int dataListLimit;

    public StatisticalData(final int dataListLimit) {
        this.statisticalDataList = new LinkedList<>();
        this.dataListLimit = dataListLimit;
    }

    public void add(final T randomDataPoint) {
        statisticalDataList.add(randomDataPoint);
        if(statisticalDataList.size() > dataListLimit) {
            statisticalDataList.remove(0);
        }
    }

    public double computeMean(final Function<T, Double> bijection) {
        return new Mean().exec(statisticalDataList.stream().map(bijection).collect(Collectors.toList()));
    }

    public double computeVariance(final Function<T, Double> bijection) {
        return new StandardDeviation().exec(statisticalDataList.stream().map(bijection).collect(Collectors.toList()));
    }

}
