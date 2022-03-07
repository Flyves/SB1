package util.state_machine;

public interface Transition<I, O> {
    State<I, O> next(I input);
}
