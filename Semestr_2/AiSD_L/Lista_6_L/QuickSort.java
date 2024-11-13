package AiSD_L.Lista_6_L;

import AiSD_L.Lista_6_L.core.SortingAlgorithm;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class QuickSort<T> extends SortingAlgorithm<T> {
    private final boolean firstPivot;

    public QuickSort(Comparator<? super T> comparator, boolean firstPivot) {
        super(comparator);
        this.firstPivot = firstPivot;
    }

    @Override
    public List<T> sort(List<T> list) {
        quickSort((LinkedList<T>) list, 0, list.size() - 1);
        return list;
    }

    private void quickSort(LinkedList<T> linkedList, int bot, int top) {
        if (bot < top) {
            int partition;
            if (firstPivot)
                partition = partitionFirst(linkedList, bot, top);
            else
                partition = partitionRandom(linkedList, bot, top);
            quickSort(linkedList, bot, partition - 1);
            quickSort(linkedList, partition + 1, top);
        }
    }

    private int partitionFirst(LinkedList<T> linkedList, int bot, int top) {
        T pivot = linkedList.get(bot);
        int lastBigger = top;
        for (int i = top; i > bot; i--) {
            if (compare(linkedList.get(i), pivot) > 0)
                swap(linkedList, i, lastBigger--);
        }
        swap(linkedList, lastBigger, bot);
        return lastBigger;
    }

    private int partitionRandom(LinkedList<T> linkedList, int bot, int top) {
        int pivotIndex = new Random().nextInt(top - bot + 1) + bot;
        T pivot = linkedList.get(pivotIndex);
        swap(linkedList, pivotIndex, top);
        int lastSmaller = bot;
        for (int i = bot; i < top; i++)
            if (compare(linkedList.get(i), pivot) < 0)
                swap(linkedList, i, lastSmaller++);

        swap(linkedList, lastSmaller, top);
        return lastSmaller;
    }
}
