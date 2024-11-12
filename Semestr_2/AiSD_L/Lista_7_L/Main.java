package AiSD_L.Lista_7_L;

import java.util.Comparator;

public class Main {


    public static void main(String[] args) {
        int size = 11;

//        Array3Heap<Integer> heap = new Array3Heap<>(size, Comparator.comparingInt(o -> o));
        ArrayDHeap<Integer> heap = new ArrayDHeap<>(size, 3, Comparator.comparingInt(o -> o));

        for (int i = 0; i < size; i++)
            heap.add(i);

        for (int i = 0; i < size; i++) {
            heap.printHeap();
            heap.maximum();
        }

        for (int i = 0; i < size; i++)
            heap.add(i);
        heap.clear();
        heap.printHeap();
    }
}
