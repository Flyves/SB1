package util.control_functions;

import util.state_machine.Behaviour;

public class PdController implements Behaviour<Double, Double> {
    private final double p;
    private final double d;
    private Double previousError;
    private Long lastTime;

    public PdController(final double p, final double d) {
        this.p = p;
        this.d = d;
    }

    @Override
    public Double exec(final Double error) {
        final double proportional = error*p;
        if(previousError == null || lastTime == null) {
            updateInternalState(error);
            return proportional;
        }
        final long currentTime = System.currentTimeMillis();
        final double derivative = (error-previousError) * ((currentTime-lastTime)/1000.0) * d;
        updateInternalState(error);
        return proportional + derivative;
    }

    private void updateInternalState(Double error) {
        previousError = error;
        lastTime = System.currentTimeMillis();
    }
}
