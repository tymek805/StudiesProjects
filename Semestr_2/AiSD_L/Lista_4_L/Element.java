package AiSD_L.Lista_4_L;

public class Element<E> {
    private E value;
    private Element<E> next, previous;

    public Element(E data) {
        this.value = data;
    }

    public void insertAfter(Element<E> element) {
        element.setNext(this.getNext());
        element.setPrevious(this);
        this.getNext().setPrevious(element);
        this.setNext(element);
    }

    public void insertBefore(Element<E> element) {
        element.setNext(this);
        element.setPrevious(this.getPrevious());
        this.getPrevious().setNext(element);
        this.setPrevious(element);
    }

    public void remove() {
        this.getNext().setPrevious(this.getPrevious());
        this.getPrevious().setNext(this.getNext());
    }

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }

    public Element<E> getNext() {
        return next;
    }

    public void setNext(Element<E> next) {
        this.next = next;
    }

    public Element<E> getPrevious() {
        return previous;
    }

    public void setPrevious(Element<E> previous) {
        this.previous = previous;
    }
}
