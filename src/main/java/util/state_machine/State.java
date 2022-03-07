package util.state_machine;

public abstract class State<I, O> implements Behaviour<I, O>, Transition<I, O>, Startable<I>, Stopable<I> {
    @Override
    public void start(final I input) { }
    @Override
    public void stop(final I input) { }
}
