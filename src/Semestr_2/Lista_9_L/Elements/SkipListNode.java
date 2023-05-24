package Semestr_2.Lista_9_L.Elements;

public class SkipListNode<T> {
    private final T value;
    private final int level;
    private final SkipListNode<T>[] nextNodes;

    @SuppressWarnings("unchecked")
    public SkipListNode(T value, int level) {
        this.value = value;
        this.level = level;
        this.nextNodes = new SkipListNode[level + 1];
    }

    public T getValue() {return value;}
    public int getLevel() {return level;}
    public SkipListNode<T> getNextNode(int level) {return nextNodes[level];}
    public void setNextNode(int level, SkipListNode<T> node) {nextNodes[level] = node;}
}
