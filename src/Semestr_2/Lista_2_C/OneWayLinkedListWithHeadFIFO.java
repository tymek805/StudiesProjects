package Semestr_2.Lista_2_C;

public class OneWayLinkedListWithHeadFIFO<E> {
    private Element<E> head = null;

    public OneWayLinkedListWithHeadFIFO() {}

    public boolean add(E e) {
        Element<E> newElem = new Element<>(e);
        if (head == null) {
            head = newElem;
            return true;
        }
        Element<E> tail = head;
        while (tail.getNext() != null)
            tail = tail.getNext();
        tail.setNext(newElem);
        return true;
    }

    public E getFirst() {
        Element<E> element = head.getNext();
        return element == null ? null : element.getValue();
    }

    public boolean remove() {
        if (head == null) return false;
        head = head.getNext();
        return true;
    }
}
