package AiSD_L.Lista_5_L;

import java.util.*;

import AiSD_L.Lista_5_L.core.SortingAlgorithm;

public class BinaryInsertionSort<T> extends SortingAlgorithm<T> {

    public BinaryInsertionSort(Comparator<? super T> comparator) {
        super(comparator);
    }

    @Override
    public List<T> sort(List<T> list) {
        for (int i = 1; i < list.size(); i++) {
            int j = i - 1;
            T item = list.get(i);
            int loc = insertPosition(list, 0, j, item);
            while (j >= loc) {
                swap(list, j, j + 1);
                j--;
            }
        }
        return list;
    }

    private int insertPosition(List<T> list, int bot, int top, T item) {
        if (top < bot) return bot;

        int mid = bot + (top - bot) / 2;
        if (list.get(mid) == item)
            return mid;
        else if (compare(list.get(mid), item) > 0)
            return insertPosition(list, bot, mid - 1, item);
        else
            return insertPosition(list, mid + 1, top, item);
    }
}
