package Semestr_2.Lista_3_L;

import java.util.Iterator;
import java.util.ListIterator;

public class OneWayLinkedListWithSentinel<E> implements IList<E> {
    private final Element<E> sentinel;

    public OneWayLinkedListWithSentinel(){
        sentinel = new Element<>(null);
        sentinel.setNext(sentinel);
    }

    private Element<E> getElement(int index){
        Element<E> elem = sentinel.getNext();
        while (index > 0 && elem != null){
            index--;
            elem = elem.getNext();
        }
        return elem;
    }

    public boolean isEmpty(){return sentinel.getNext() == sentinel;}

    private E[] moveTable(E[] table, E value){
        for (int i = 1; i < table.length; i++)
            table[i - 1] = table[i];

        table[table.length - 1] = value;
        return table;
    }

    public int solution(int n, Element<E> element){
        if (element.getNext() == null) {
            if (n == 1) {
                System.out.println("Value: " + element.getValue());
                return indexOf(element.getValue()) * -1;
            }
            return n - 1;
        }
        int temp = solution(n, element.getNext());

        if (temp < 0) return temp;
        System.out.println(temp);

        if (temp == 1) return indexOf(element.getValue()) * -1;
        return temp - 1;
    }

    public E getFromEnd(int n){
        Element<E> element = sentinel.getNext();
        int idx = solution(n, element) * -1;
        return get(idx);
    }

    @Override
    public void clear() {sentinel.setNext(sentinel);}

    @Override
    public boolean add(E data) {
        Element<E> newElem = new Element<>(data);
        if (sentinel.getNext() == sentinel){
            sentinel.setNext(newElem);
            return true;
        }
        Element<E> tail = sentinel;
        while (tail.getNext() != null)
            tail = tail.getNext();
        tail.setNext(newElem);
        return true;
    }

    @Override
    public boolean add(int index, E data) {
        if (index < 0) return false;
        Element<E> newElem = new Element<>(data);
        if (index == 0) {
            newElem.setNext(sentinel.getNext());
            sentinel.setNext(newElem);
            return true;
        }

        Element<E> elem = getElement(index - 1);
        if (elem == null)
            return false;
        newElem.setNext(elem.getNext());
        elem.setNext(newElem);
        return true;
    }

    @Override
    public int size() {
        Element<E> element = sentinel.getNext();
        int counter = 0;
        while (element != null && element != sentinel) {
            counter++;
            element = element.getNext();
        }
        return counter;
    }

    @Override
    public int indexOf(E data) {
        int idx = 0;
        Element<E> elem = sentinel.getNext();
        while(elem != null && elem != sentinel) {
            if (elem.getValue() == data)
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
        if (sentinel.getNext() == sentinel)
            return null;
        if (index == 0){
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

    @Override
    public boolean remove(E value) {
        Element<E> elem = sentinel.getNext();
        if (elem == sentinel)
            return false;
        if (elem.getValue().equals(value)){
            sentinel.setNext(elem.getNext());
            return true;
        }

        while(elem.getNext() != null && !elem.getNext().getValue().equals(value))
            elem = elem.getNext();

        if (elem.getNext() == null)
            return false;
        elem.setNext(elem.getNext().getNext());
        return true;
    }

    @Override
    public ListIterator<E> listIterator() {return null;}
    @Override
    public Iterator<E> iterator() {return null;}
}
