package util.data_structure.bvh.binary.basic;

public interface BinaryNode<T, U, V extends Node<T, U>> extends Node<T, U> {
    Node<T, U> getLeft();
    Node<T, U> getRight();
    void setLeft(V node);
    void setRight(V node);
}
