package Semestr_2.Lista_9_L.Elements;

import java.util.Comparator;
import java.util.Random;

public class SkipList<T> {
    private final double p;
    private final Comparator<T> comparator;

    private SkipListNode<T> head;
    private int maxLevel;

    public SkipList(double p, Comparator<T> comparator) {
        this.p = p;
        this.comparator = comparator;
        head = new SkipListNode<>(null, 0);
        maxLevel = 0;
    }

    public T search(T value) {
        if (value == null)
            throw new IllegalArgumentException("Value cannot be null");
        SkipListNode<T> current = head;

        for (int i = maxLevel; i >= 0; i--) {
            while (current.getNextNode(i) != null && comparator.compare(current.getNextNode(i).getValue(), value) <= 0)
                current = current.getNextNode(i);

            if (current != head && comparator.compare(current.getValue(), value) == 0) {
//                if (current.getNextNode(current.getLevel()) != null)
//                    System.out.println(current.getValue() + " next: " + current.getNextNode(current.getLevel()).getValue());
//                else
//                    System.out.println(current.getValue() + " next: " + current.getNextNode(current.getLevel()));
                return current.getValue();
            }
        }
        return null;
    }

    public void insert(T value) {
        int level = getRandomLevel();
        if (level > maxLevel)
            increaseHeadLevel();

        SkipListNode<T> newNode = new SkipListNode<>(value, level);
        SkipListNode<T> current = head;

        for (int i = maxLevel; i >= 0; i--) {
            while (current.getNextNode(i) != null && comparator.compare(current.getNextNode(i).getValue(), value) < 0)
                current = current.getNextNode(i);

            if (i <= level) {
                newNode.setNextNode(i, current.getNextNode(i));
                current.setNextNode(i, newNode);
            }
        }
    }

    /*
    public void remove(T value) {
        delete(head, value, maxLevel);
    }

    private SkipListNode<T> delete(SkipListNode<T> current, T value, int currentLevel) {
        int compareVal = comparator.compare(current.getNextNode(currentLevel).getValue(), value);
        if (currentLevel < 0) return current;
        else if (current.getNextNode(currentLevel) != null && compareVal == 0)
            current.setNextNode(currentLevel, delete(current.getNextNode(currentLevel).getNextNode(currentLevel), value, currentLevel));
        else if (current.getNextNode(currentLevel) != null && compareVal < 0)
            return delete(current.getNextNode(currentLevel), value, currentLevel);
        return current;
    }
    */

    public void remove(T value){
        SkipListNode<T> current = head;

        for (int level = maxLevel; level >= 0; level--) {
            while (current.getNextNode(level) != null && comparator.compare(current.getNextNode(level).getValue(), value) < 0)
                current = current.getNextNode(level);

            if (current.getNextNode(level) != null && comparator.compare(current.getNextNode(level).getValue(), value) == 0)
                current.setNextNode(level, current.getNextNode(level).getNextNode(level));
        }
    }

    private void increaseHeadLevel() {
        maxLevel++;
        SkipListNode<T> oldHead = head;
        head = new SkipListNode<>(null, maxLevel);
        for (int i = 0; i < maxLevel; i++)
            head.setNextNode(i, oldHead.getNextNode(i));
    }
    private int getRandomLevel() {
        Random r = new Random();
        int level = 0;
        while (r.nextDouble() < p) level++;
        return level;
    }
}
