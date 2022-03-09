package util.data_structure.bvh.binary.query_radius;

import util.data_structure.bvh.binary.basic.DynamicChildLeafNode;
import util.data_structure.bvh.binary.basic.Node;

import java.util.function.Consumer;
import java.util.function.Function;

public class LNQueryRadius<T, U> implements Node<T, U> {
    private final DynamicChildLeafNode<T, U> leafNode;

    public LNQueryRadius(T nodeObject, U leafObject) {
        this.leafNode = new DynamicChildLeafNode<>(nodeObject, leafObject, () -> {});
    }

    public T getValue() {
        return leafNode.getValue();
    }

    public void setValue(T nodeObject) {
        this.leafNode.setValue(nodeObject);
    }

    @Override
    public void accept(Function<T, Boolean> nodeValidator, Function<U, Boolean> leafValidator, Consumer<U> visitor) {
        leafNode.accept(nodeValidator, leafValidator, visitor);
    }
}
