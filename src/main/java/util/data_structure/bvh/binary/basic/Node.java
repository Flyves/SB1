package util.data_structure.bvh.binary.basic;

import java.util.function.Consumer;
import java.util.function.Function;

public interface Node<T, U> {
    void accept(Function<T, Boolean> nodeValidator, Function<U, Boolean> leafValidator, Consumer<U> visitor);
    T getValue();
    void setValue(T nodeObject);
}
