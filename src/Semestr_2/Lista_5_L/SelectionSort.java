package Semestr_2.Lista_5_L;

import Semestr_2.Lista_5_L.core.SortingAlgorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SelectionSort<T> extends SortingAlgorithm<T> {

    public SelectionSort(Comparator<? super T> comparator) {
        super(comparator);
    }

    @Override
    public List<T> sort(List<T> list) {
        int n = list.size();
        for (int i = 0, j = n - 1; i < j; i++, j--) {
            T max = list.get(i);
            int min_i = i, max_i = i;
            for (int k = i; k <= j; k++) {
                if (compare(list.get(k), list.get(max_i)) > 0) {
                    max = list.get(k);
                    max_i = k;
                } else if (compare(list.get(k), list.get(min_i)) < 0) {
                    min_i = k;
                }
            }
            swap(list, i, min_i);
            if (list.get(min_i) == max)
                swap(list, j, min_i);
            else
                swap(list, j, max_i);
        }
        return list;
    }
}