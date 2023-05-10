package Semestr_2.Lista_7_L;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class ArrayDHeap<T> implements Heap<T> {
    private T[] content;
    private int lastOccupied = -1;
    private final int numberOfChildren;
    private final Comparator<T> comparator;

    @SuppressWarnings("unchecked")
    public ArrayDHeap(int size, int numberOfChildren, Comparator<T> comparator){
        if (size < 1) throw new IllegalArgumentException("Initial size of array must be positive number");
        content = (T[]) new Object[size];
        this.numberOfChildren = numberOfChildren;
        this.comparator = comparator;
    }

    @Override
    public void clear() {
        Arrays.fill(content, null);
        lastOccupied = -1;
    }

    @Override
    public void add(T element) {
        if (element == null)
            throw new IllegalArgumentException("Value cannot be null");
        if (lastOccupied + 1 >= content.length)
            content = Arrays.copyOf(content, content.length * 2);
        content[++lastOccupied] = element;
        heapCreation();
    }

    @Override
    public T maximum() {
        if (lastOccupied < 0)
            throw new NoSuchElementException("Heap is empty");
        T value = content[0];
        content[0] = content[lastOccupied];
        content[lastOccupied--] = null;
        sink(0);
        return value;
    }

    public T minimum(){
        if (lastOccupied < 0)
            throw new NoSuchElementException("Heap is empty");

        int minimumIdx = 0;
        for (int i = (content.length - 1) / numberOfChildren; i >= 0; i--) {
            if (content[i] != null && lastOccupied > 0){
                int smallestIdx = i;
                for (int j = 1; j <= numberOfChildren; j++){
                    int childIdx = i * numberOfChildren + j;

                    if (childIdx <= lastOccupied  && comparator.compare(content[childIdx], content[smallestIdx]) < 0)
                        smallestIdx = childIdx;
                }
                if (comparator.compare(content[smallestIdx], content[minimumIdx]) < 0){
                    minimumIdx = smallestIdx;
                }
            }
        }

        T value = content[minimumIdx];
        content[minimumIdx] = content[lastOccupied];
        content[lastOccupied--] = null;
        sink(minimumIdx);
        return value;
    }

    private void heapCreation(){
        for (int i = (content.length - 1) / numberOfChildren; i >= 0; i--) {
            if (content[i] != null && lastOccupied > 0)
                sink(i);
        }
    }

    private void sink(int parentIdx){
        int biggestIdx = parentIdx;
        for (int j = 1; j <= numberOfChildren; j++){
            int childIdx = parentIdx * numberOfChildren + j;

            if (childIdx <= lastOccupied  && comparator.compare(content[childIdx], content[biggestIdx]) > 0)
                biggestIdx = childIdx;
        }
        if (biggestIdx != parentIdx) {
            swap(biggestIdx, parentIdx);
            sink(biggestIdx);
        }
    }

    private void swap(int leftIdx, int rightIdx){
        T temp = content[leftIdx];
        content[leftIdx] = content[rightIdx];
        content[rightIdx] = temp;
    }

    public void printHeap(){
        for (T t : content)
            if (t != null) System.out.print(t + " ");
        System.out.println();
    }
}
