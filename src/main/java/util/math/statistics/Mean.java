package util.math.statistics;

import util.state_machine.Behaviour;

import java.util.List;

public class Mean implements Behaviour<List<Double>, Double> {
    @Override
    public Double exec(final List<Double> dataList) {
        return dataList.stream()
                .reduce(Double::sum)
                .map(data -> data/dataList.size())
                .orElse(0d);
    }
}
