package Semestr_2.Lista_10_L.Lista;

public class Node {
    private Node representative;
    private Node next;
    private Node last;
    private int length;


    public Node() {
        this.representative = this;
        this.next = null;
        this.last = this;
        this.length = 1;
    }

    public int getLength() {
        return representative.length;
    }
    public void setLength(int length) {
        representative.length = length;
    }

    public Node getLast() {
        return representative.last;
    }
    public void setLast(Node last) {
        this.representative.last = last;
    }

    public Node getRepresentative() {
        return representative;
    }
    public void setRepresentative(Node representative) {
        this.representative = representative;
    }

    public Node getNext() {
        return next;
    }
    public void setNext(Node next) {
        this.next = next;
    }
}