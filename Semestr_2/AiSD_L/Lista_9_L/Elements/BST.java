package AiSD_L.Lista_9_L.Elements;

import java.util.Comparator;
import java.util.NoSuchElementException;

public class BST<T> {
    private final Comparator<T> comparator;
    private Node<T> root;

    public BST(Comparator<T> comparator) {
        this.comparator = comparator;
        this.root = null;
    }

    public Node<T> search(T value) {
        return findElement(value, root);
    }

    private Node<T> findElement(T value, Node<T> parentNode) {
        if (parentNode == null || value == parentNode.getKey())
            return parentNode;
        if (comparator.compare(value, parentNode.getKey()) < 0)
            return findElement(value, parentNode.getLeftNode());
        else
            return findElement(value, parentNode.getRightNode());
    }

    public Node<T> minimum() {
        return findMinimum(root);
    }

    private Node<T> findMinimum(Node<T> parentNode) {
        if (parentNode.getLeftNode() == null)
            return parentNode;
        return findMinimum(parentNode.getLeftNode());
    }

    public Node<T> maximum() {
        return findMaximum(root);
    }

    private Node<T> findMaximum(Node<T> parentNode) {
        if (parentNode.getRightNode() == null)
            return parentNode;
        return findMaximum(parentNode.getRightNode());
    }

    public void postOrder(Visitor<T> visitor) {
        postOrder(root, visitor);
    }

    private void postOrder(Node<T> node, Visitor<T> visitor) {
        if (node != null) {
            postOrder(node.getLeftNode(), visitor);
            postOrder(node.getRightNode(), visitor);
            visitor.execute(node.getKey());
        }
    }

    public Node<T> successor(T value) {
        Node<T> node = root;
        Node<T> successor = null;

        while (node != null) {
            if (comparator.compare(value, node.getKey()) < 0) {
                successor = node;
                node = node.getLeftNode();
            } else
                node = node.getRightNode();
        }
        return successor;
    }

    public void insert(T value) {
        Node<T> node = root;
        Node<T> lastNode = null;

        while (node != null) {
            lastNode = node;
            if (comparator.compare(value, node.getKey()) < 0)
                node = node.getLeftNode();
            else
                node = node.getRightNode();
        }

        if (lastNode == null)
            root = new Node<>(value);
        else if (comparator.compare(value, lastNode.getKey()) < 0)
            lastNode.setLeftNode(new Node<>(value));
        else
            lastNode.setRightNode(new Node<>(value));
    }

    public T remove(T key) {
        Node<T> removeNode = root;
        Node<T> parentNode = null;

        while (removeNode != null && comparator.compare(removeNode.getKey(), key) != 0) {
            parentNode = removeNode;
            if (comparator.compare(removeNode.getKey(), key) > 0)
                removeNode = removeNode.getLeftNode();
            else
                removeNode = removeNode.getRightNode();
        }

        if (removeNode == null)
            throw new NoSuchElementException("Given value is not in tree");

        Node<T> leftNode = removeNode.getLeftNode();
        Node<T> rightNode = removeNode.getRightNode();
        // Przypadek 1 & 2 - węzeł z ma najwięcej jednego potomka
        if (leftNode == null || rightNode == null) {
            Node<T> replaceNode;
            if (leftNode == null)
                replaceNode = rightNode;
            else
                replaceNode = leftNode;

            if (parentNode == null) {
                root = replaceNode;
                return null;
            }

            if (parentNode.getLeftNode() == removeNode)
                parentNode.setLeftNode(replaceNode);
            else
                parentNode.setRightNode(replaceNode);
        }
        // Przypadek 3 - węzeł z ma dwóch potomków
        else {
            Node<T> successorNode = successor(removeNode.getKey());
            if (removeNode == root) {
                successorNode.setLeftNode(root.getLeftNode());
                successorNode.setRightNode(root.getRightNode());
                root = successorNode;
            } else if (parentNode.getLeftNode() == removeNode)
                parentNode.setLeftNode(successorNode);
            else
                parentNode.setRightNode(successorNode);
            successorNode.setLeftNode(removeNode.getLeftNode());
        }
        return removeNode.getKey();
    }
}
