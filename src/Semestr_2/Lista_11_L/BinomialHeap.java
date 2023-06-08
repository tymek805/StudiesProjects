package Semestr_2.Lista_11_L;

import java.util.Comparator;

public class BinomialHeap<T> {
    private final Comparator<T> comparator;
    private BinomialTree<T> head = null;

    public BinomialHeap(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public BinomialTree<T> maximum() {
        BinomialTree<T> maxNode = this.head;
        BinomialTree<T> currentNode = this.head;
        T maxValue = currentNode == null ? null : currentNode.getValue();
        while (currentNode != null) {
            if (comparator.compare(currentNode.getValue(), maxValue) > 0) {
                maxValue = currentNode.getValue();
                maxNode = currentNode;
            }
            currentNode = currentNode.getSibling();
        }
        return maxNode;
    }

    public BinomialTree<T> insert(T value) {
        BinomialHeap<T> temp = new BinomialHeap<>(comparator);
        BinomialTree<T> tree = new BinomialTree<>(value);
        temp.head = tree;
        this.union(temp);
        return tree;
    }

    public void increaseValue(BinomialTree<T> node, T newValue) {
        if (comparator.compare(node.getValue(), newValue) > 0)
            throw new IllegalArgumentException("Given new value must be greater than old value");
        node.setValue(newValue);

        BinomialTree<T> curr = node;
        BinomialTree<T> parent = node.getParent();
        while (parent != null && comparator.compare(curr.getValue(), parent.getValue()) > 0 ) {
            T temp = curr.getValue();
            curr.setValue(parent.getValue());
            parent.setValue(temp);

            curr = parent;
            parent = curr.getParent();
        }
    }

    public T extractMax() {
        if (head == null) return null;

        BinomialTree<T> max = maximum();
        BinomialTree<T> current = head;
        BinomialTree<T> prev = null;

        while (current != max) {
            prev = current;
            current = current.getSibling();
        }

        if (prev == null) head = current.getSibling();
        else prev.setSibling(current.getSibling());

        current = current.getChild();

        while (current != null) {
            current.setParent(null);
            current = current.getSibling();
        }

        if (max.getChild() != null) {
            BinomialTree<T> reversedMaxChild = max.getChild().reverse();
            BinomialHeap<T> tempHeap = new BinomialHeap<>(comparator);
            tempHeap.head = reversedMaxChild;
            union(tempHeap);
        }

        return max.getValue();
    }

    public void union(BinomialHeap<T> heap2) {
        merge(heap2);

        if (this.head == null) return;
        BinomialTree<T> prev = null;
        BinomialTree<T> x = this.head;
        BinomialTree<T> next = this.head.getSibling();

        while (next != null) {
            if ((x.getDegree() != next.getDegree()) ||
                    (next.getSibling() != null && next.getSibling().getDegree() == x.getDegree())) {
                prev = x;
                x = next;
            } else if (comparator.compare(x.getValue(), next.getValue()) >= 0){
                x.setSibling(next.getSibling());
                next.linkTo(x);
            } else {
                if (prev == null) this.head = next;
                else prev.setSibling(next);
                x.linkTo(next);
                x = next;
            }
            next = x.getSibling();
        }
    }

    private void merge(BinomialHeap<T> mergedHeap) {
        BinomialTree<T> a = this.head;
        BinomialTree<T> b = mergedHeap.head;

        if (a == null) {
            this.head = b;
            return;
        }
        if (b == null) return;

        if (a.getDegree() > b.getDegree()) {
            a = b;
            b = this.head;
            this.head = a;
        }

        while (b != null) {
            if (a.getSibling() == null) {
                a.setSibling(b);
                return;
            } else if (a.getSibling().getDegree() < b.getDegree()) {
                a = a.getSibling();
            } else {
                BinomialTree<T> c = b.getSibling();
                b.setSibling(a.getSibling());
                a.setSibling(b);
                a = a.getSibling();
                b = c;
            }
        }
    }

    public void displayHeap() {
        System.out.print("\nHeap : ");
        displayHeap(head);
        System.out.println("\n");
    }

    private void displayHeap(BinomialTree<T> head) {
        if (head == null) return;
        displayHeap(head.getChild());
        System.out.print(head.getValue() + (head.getParent() == null ? "         " : " "));
        displayHeap(head.getSibling());
    }
}
