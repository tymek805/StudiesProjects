package Semestr_2.Lista_6_L;

import Semestr_2.Lista_6_L.core.SortingAlgorithm;

import java.util.Comparator;
import java.util.List;
import java.util.LinkedList;

public class MergeSort<T> extends SortingAlgorithm<T> {

    public MergeSort(Comparator<? super T> comparator) {
        super(comparator);
    }

    @Override
    public List<T> sort(List<T> list) {
        if (list instanceof LinkedList<T>){
            return mergeSort((LinkedList<T>) list);
        }
        return null;

    }
    @SuppressWarnings("unchecked")
    public LinkedList<T> mergeSort(LinkedList<T> list) {
        int n = list.size();
        LinkedList<T> result = new LinkedList<>();
        LinkedList<T>[] queue = new LinkedList[2];
        queue[0] = list;
        queue[1] = result;
        int level = 0;
        int size = 1;
        while (size < n) {
            LinkedList<T> currentQueue = queue[level % 2];
            LinkedList<T> nextQueue = queue[(level + 1) % 2];
            while (!currentQueue.isEmpty()) {
                LinkedList<T> first = new LinkedList<>();
                LinkedList<T> second = new LinkedList<>();

                for (int i = 0; i < size && !currentQueue.isEmpty(); i++)
                    first.addLast(currentQueue.removeFirst());
                for (int i = 0; i < size && !currentQueue.isEmpty(); i++)
                    second.addLast(currentQueue.removeFirst());

                while (!first.isEmpty() || !second.isEmpty()) {
                    if (first.isEmpty()) {
                        nextQueue.addLast(second.removeFirst());
                    } else if (second.isEmpty()) {
                        nextQueue.addLast(first.removeFirst());
                    } else {
                        if (compare(first.getFirst(), second.getFirst()) <= 0) {
                            nextQueue.addLast(first.removeFirst());
                        } else {
                            nextQueue.addLast(second.removeFirst());
                        }
                    }
                }
            }
            level++;
            size *= 2;
        }

        if (level % 2 == 1) {
            list.clear();
            list.addAll(result);
            return result;
        } else
            return list;
    }
}
