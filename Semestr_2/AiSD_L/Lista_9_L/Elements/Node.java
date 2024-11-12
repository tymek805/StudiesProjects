package AiSD_L.Lista_9_L.Elements;

public class Node<T> {
    private Node<T> leftNode, rightNode;
    private T key;

    public Node(T key) {
        this.key = key;
        leftNode = null;
        rightNode = null;
    }

    public Node(T key, Node<T> leftNode, Node<T> rightNode) {
        this.key = key;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

    public T getKey() {
        return key;
    }

    public Node<T> getLeftNode() {
        return leftNode;
    }

    public Node<T> getRightNode() {
        return rightNode;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public void setLeftNode(Node<T> leftNode) {
        this.leftNode = leftNode;
    }

    public void setRightNode(Node<T> rightNode) {
        this.rightNode = rightNode;
    }
}
