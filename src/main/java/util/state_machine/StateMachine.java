package util.state_machine;

public class StateMachine<I, O> extends State<I, O> {
    private State<I, O> state;
    private State<I, O> nextState;

    public StateMachine(final State<I, O> initState) {
        state = null;
        nextState = initState;
    }

    public O exec(final I input) {
        final O output;

        if(nextState != state) nextState.start(input);
        state = nextState;
        output = state.exec(input);
        nextState = state.next(input);
        if(nextState != state) state.stop(input);

        return output;
    }

    @Override
    public State<I, O> next(I input) {
        return this;
    }
}
