package util.data_structure.bvh.binary.query_radius;

import util.data_structure.bvh.binary.basic.BinaryNode;
import util.data_structure.bvh.binary.basic.DynamicChildBinaryNode;
import util.data_structure.bvh.binary.basic.Node;

import java.util.function.Consumer;
import java.util.function.Function;

public class BNQueryRadius<T, U> implements BinaryNode<T, U, Node<T, U>> {
    private final DynamicChildBinaryNode<T, U, Node<T, U>> binaryNode;

    public BNQueryRadius() {
        this.binaryNode = new DynamicChildBinaryNode<>(child -> {});
    }

    public T getValue() {
        return binaryNode.getValue();
    }

    public void setValue(T nodeObject) {
        this.binaryNode.setValue(nodeObject);
    }

    @Override
    public void accept(Function<T, Boolean> nodeValidator, Function<U, Boolean> leafValidator, Consumer<U> visitor) {
        binaryNode.accept(nodeValidator, leafValidator, visitor);
    }

    @Override
    public Node<T, U> getLeft() {
        return binaryNode.getLeft();
    }

    @Override
    public Node<T, U> getRight() {
        return binaryNode.getRight();
    }

    @Override
    public void setLeft(Node<T, U> node) {
        binaryNode.setLeft(node);
    }

    @Override
    public void setRight(Node<T, U> node) {
        binaryNode.setRight(node);
    }
}
