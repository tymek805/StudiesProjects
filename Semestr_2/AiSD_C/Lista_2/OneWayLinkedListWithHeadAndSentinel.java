package AiSD_C.Lista_2;

public class OneWayLinkedListWithHeadAndSentinel<E> {
    private final Element<E> head;
    private final Element<E> sentinel;

    public OneWayLinkedListWithHeadAndSentinel() {
        sentinel = new Element<>(null);
        sentinel.setNext(sentinel);

        head = new Element<>(null);
        head.setNext(sentinel);
    }

    private Element<E> getElement(int index) {
        Element<E> elem = sentinel.getNext();
        while (index > 0 && elem != null) {
            index--;
            elem = elem.getNext();
        }
        return elem;
    }

    public void insert(int index, E e) {
        if (index < 0) return;
        Element<E> newElem = new Element<>(e);
        if (index == 0) {
            newElem.setNext(sentinel.getNext());
            sentinel.setNext(newElem);
        } else {
            Element<E> elem = getElement(index - 1);
            if (elem == null) return;
            newElem.setNext(elem.getNext());
            elem.setNext(newElem);
        }
    }

    public E get(int index) {
        Element<E> elem = getElement(index);
        return elem == null ? null : elem.getValue();
    }

    public int size() {
        Element<E> element = sentinel.getNext();
        int counter = 0;
        while (element != null && element != sentinel) {
            counter++;
            element = element.getNext();
        }
        return counter;
    }

    public void clear() {
        sentinel.setNext(sentinel);
    }

    public E delete(int index) {
        if (sentinel.getNext() == sentinel)
            return null;
        if (index == 0) {
            Element<E> elem = sentinel.getNext();
            E resetValue = elem.getValue();
            sentinel.setNext(elem.getNext());
            return resetValue;
        }
        Element<E> elem = getElement(index - 1);
        if (elem == null || elem.getNext() == null)
            return null;
        E resetValue = elem.getNext().getValue();
        elem.setNext(elem.getNext().getNext());
        return resetValue;
    }

    public boolean delete(E e) {
        Element<E> elem = sentinel.getNext();
        if (elem == sentinel)
            return false;
        if (elem.getValue().equals(e)) {
            sentinel.setNext(elem.getNext());
            return true;
        }

        while (elem.getNext() != null && !elem.getNext().getValue().equals(e))
            elem = elem.getNext();

        if (elem.getNext() == null)
            return false;
        elem.setNext(elem.getNext().getNext());
        return true;
    }

    public E set(int index, E e) {
        Element<E> elem = getElement(index);
        if (elem == null)
            return null;
        E elemData = elem.getValue();
        elem.setValue(e);
        return elemData;
    }

    public int indexOf(E data) {
        int idx = 0;
        Element<E> elem = sentinel.getNext();
        while (elem != null && elem != sentinel) {
            if (elem.getValue() == data)
                return idx;
            idx++;
            elem = elem.getNext();
        }
        return -1;
    }

    public void wyswietlListe() {
        for (int i = 0; i < size(); i++)
            System.out.println(get(i));
    }
}
