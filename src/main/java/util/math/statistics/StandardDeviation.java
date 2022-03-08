package util.math.statistics;

import util.state_machine.Behaviour;

import java.util.List;

public class StandardDeviation implements Behaviour<List<Double>, Double> {
    @Override
    public Double exec(List<Double> dataList) {
        final double mean = new Mean().exec(dataList);
        return dataList.stream()
                .map(data -> data - mean)
                .map(data -> data * data)
                .reduce(Double::sum)
                .map(sum -> sum/dataList.size())
                .map(Math::sqrt)
                .orElse(0d);
    }
}
