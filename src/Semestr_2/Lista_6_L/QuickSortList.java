package Semestr_2.Lista_6_L;

import Semestr_2.Lista_6_L.core.SortingAlgorithm;

import java.util.*;

public class QuickSortList<T> extends SortingAlgorithm<T> {
    private final boolean firstPivot;

    public QuickSortList(Comparator<? super T> comparator, boolean firstPivot) {
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
        swap(linkedList, bot, top);
        ListIterator<T> listIteratorBot = linkedList.listIterator(bot);
        ListIterator<T> listIteratorTop = linkedList.listIterator(top);
        int lastSmaller = bot;
        int lastBigger = top;
        int idxNext = bot;
        int idxPrev = top - 1;

        while (idxNext < top){
            if (compare(listIteratorBot.next(), pivot) < 0)
                swap(linkedList, idxNext, lastSmaller++);
            if (idxPrev > idxNext && compare(listIteratorTop.previous(), pivot) > 0)
                swap(linkedList, idxPrev, --lastBigger);
            idxNext++;
            idxPrev--;
        }
        swap(linkedList, lastSmaller, top);
        return lastSmaller;
    }

    private int partitionRandom(LinkedList<T> linkedList, int bot, int top) {
        int pivotIndex = new Random().nextInt(top - bot + 1) + bot;
        T pivot = linkedList.get(pivotIndex);
        swap(linkedList, pivotIndex, top);
        ListIterator<T> listIteratorBot = linkedList.listIterator(bot);
        ListIterator<T> listIteratorTop = linkedList.listIterator(top);
        int lastSmaller = bot;
        int lastBigger = top;
        int idxNext = bot;
        int idxPrev = top - 1;

        while (idxNext < top){
            if (compare(listIteratorBot.next(), pivot) < 0)
                swap(linkedList, idxNext, lastSmaller++);
            if (idxPrev > idxNext && compare(listIteratorTop.previous(), pivot) > 0)
                swap(linkedList, idxPrev, --lastBigger);
            idxNext++;
            idxPrev--;
        }
        swap(linkedList, lastSmaller, top);
        return lastSmaller;
    }
}