package AiSD_L.Lista_11_L;

import java.util.ArrayList;
import java.util.Collections;

public class Test {
    public static void main(String[] args) {
        BinomialHeap<Integer> heap = new BinomialHeap<>(Integer::compareTo);
        int[] heapToCreate = new int[]{7, 8, 14, 27, 11, 10, 30, 25, 3, 13, 20, 19, 9};

        int max = heapToCreate[0];
        for (int num : heapToCreate) {
            heap.insert(num);
            if (num > max) max = num;
        }
        System.out.println("Wartość maksymalna z heap: " + heap.maximum().getValue());
        System.out.println("Wartość maksymalna: " + max);

        BinomialTree<Integer> first = heap.insert(heapToCreate[0]);
        for (int i = 1; i < heapToCreate.length; i++)
            heap.insert(heapToCreate[i]);

        System.out.println();

        int newValue = 56;
        heap.increaseValue(first, newValue);
        System.out.println("Wstawiona wartość: " + newValue);
        System.out.println("Wartość maksymalna po wstawieniu: " + heap.maximum().getValue());

        BinomialHeap<Integer> heap1 = new BinomialHeap<>(Integer::compareTo);
        Integer[] heap1nums = new Integer[]{7, 9, 22, 12, 40, 32, 21, 33, 35, 31, 28, 37, 50, 26, 43, 44, 2, 25, 19};
        for (Integer i : heap1nums) heap1.insert(i);

        Integer[] heap2nums = new Integer[]{14, 29, 23, 42, 6, 20, 11};
        BinomialHeap<Integer> heap2 = new BinomialHeap<>(Integer::compareTo);
        for (Integer i : heap2nums) heap2.insert(i);

        heap1.union(heap2);

        ArrayList<Integer> list = new ArrayList<>();

        Collections.addAll(list, heap1nums);
        Collections.addAll(list, heap2nums);

        list.stream().sorted(Collections.reverseOrder()).forEach(o -> System.out.println(o + " " + heap1.extractMax()));
    }
}
