package Semestr_2.Lista_7_L;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class Array3Heap<T extends Comparable<? super T>> implements Heap<T> {
    private T[] content;
    private int lastOccupied = -1;
    private final Comparator<T> comparator;

    @SuppressWarnings("unchecked")
    public Array3Heap(int size, Comparator<T> comparator){
        content = (T[]) new Comparable[size];
        this.comparator = comparator;
        // todo capacity > 0
    }

    @Override
    public void clear() {
        Arrays.fill(content, null);
        lastOccupied = -1;
    }

    @Override
    public void add(T element) {
        // todo null value exception
        if (lastOccupied >= content.length)
            content = Arrays.copyOf(content, content.length * 2);
        content[++lastOccupied] = element;
        heapCreation();
    }

    @Override
    public T minimum() {
        if (lastOccupied < 0)
            throw new NoSuchElementException("Heap is empty");
        T value = content[0];
        content[0] = content[lastOccupied];
        content[lastOccupied--] = null;
        sink(0);
        return value;
    }

    private void heapCreation(){
        for (int i = (content.length - 1) / 3; i >= 0; i--) {
            if (content[i] != null && lastOccupied > 0)
                sink(i);
        }
    }

    private void sink(int parentIdx){
        int biggestIdx = parentIdx;
        for (int j = 1; j < 4; j++){
            int childIdx = parentIdx * 3 + j;
//            int val = content[childIdx].compareTo(content[++childIdx]);
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
        for (T t : content){
            if (t != null) System.out.print(t + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int size = 11;
        Array3Heap<Integer> heap = new Array3Heap<>(size, Comparator.comparingInt(o -> o));
        for (int i = 0; i < size; i++)
            heap.add(i);
        for (int i = 0; i < size; i++){
            heap.printHeap();
            heap.minimum();
        }
    }
}
