package Semestr_2.Lista_4_L;

import java.util.Iterator;
import java.util.ListIterator;

public class TwoWayLinkedList<E> implements IList<E> {
    private final Element<E> tail;

    public TwoWayLinkedList(){
        tail = new Element<>(null);
        tail.setNext(tail);
        tail.setPrevious(tail);
    }

    private Element<E> getElement(int index){
        if (index < 0) return null;
        Element<E> element;

        int size = size();
        if (index < size / 2){
            element = tail.getNext();
            while (index > 0 && element != tail){
                element = element.getNext();
                index--;
            }
        } else {
            element = tail.getPrevious();
            while (index < size - 1 && element != tail){
                element = element.getPrevious();
                index++;
            }
        }

        if (element == tail)
            throw new IndexOutOfBoundsException("Index " + index + " is not in list");
        return element;
    }

    private Element<E> getElement(E value){
        Element<E> element = tail.getNext();
        while (element != tail && !value.equals(element.getValue())){
            element = element.getNext();
        }
        if (element == tail)
            return null;
        return element;
    }

    public boolean isEmpty(){return tail.getNext() == tail || tail.getNext() == null;}

    @Override
    public void clear() {
        tail.setNext(tail);
        tail.setPrevious(tail);
    }

    @Override
    public boolean add(E data) {
        Element<E> newElem = new Element<>(data);
        tail.insertBefore(newElem);
        if (tail.getNext() == tail) tail.setNext(newElem);
        return true;
    }

    @Override
    public boolean add(int index, E data) {
        if (index < 0) return false;
        Element<E> newElem = new Element<>(data);
        if (index == 0) {
            tail.insertAfter(newElem);
            return true;
        }

        Element<E> elem = getElement(index - 1);
        if (elem == null)
            return false;
        elem.insertAfter(newElem);
        return true;
    }

    @Override
    public int size() {
        Element<E> element = tail.getNext();
        int counter = 0;
        while (element != tail) {
            counter++;
            element = element.getNext();
        }
        return counter;
    }

    @Override
    public int indexOf(E data) {
        Element<E> elem = tail.getNext();
        int idx = 0;
        while (elem != null && elem != tail) {
            if (elem.getValue().equals(data))
                return idx;
            idx++;
            elem = elem.getNext();
        }
        return -1;
    }

    @Override
    public boolean contains(E data) {return indexOf(data) >= 0;}

    @Override
    public E get(int index) {
        Element<E> elem = getElement(index);
        return elem == null ? null : elem.getValue();
    }

    @Override
    public E set(int index, E data) {
        Element<E> elem = getElement(index);
        if (elem == null)
            return null;
        E elemData = elem.getValue();
        elem.setValue(data);
        return elemData;
    }

    @Override
    public E remove(int index) {
        Element<E> element = getElement(index);
        if (element == null) return null;
        element.remove();
        return element.getValue();
    }

    @Override
    public boolean remove(E value) {
        Element<E> element = getElement(value);
        if (element == null) return false;
        element.remove();
        return true;
    }

    @Override
    public ListIterator<E> listIterator() {return null;}
    @Override
    public Iterator<E> iterator() {return null;}
}
