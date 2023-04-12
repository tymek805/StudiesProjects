package Semestr_2.Lista_5_L;

import Semestr_2.Lista_5_L.core.SortingAlgorithm;

import java.util.Comparator;
import java.util.List;

public class CocktailSort<T> extends SortingAlgorithm<T> {

	public CocktailSort(Comparator<? super T> comparator) {
		super(comparator);
	}

	@Override
	public List<T> sort(List<T> list) {
		boolean swapped = true;
		int start = 0;
		int end = list.size();

		while (swapped) {
			swapped = false;
			for (int i = start; i < end - 1; ++i) {
				if (compare(list.get(i), list.get(i + 1)) > 0) {
					swap(list, i, i + 1);
					swapped = true;
				}
			}
			if (!swapped) break;

			swapped = false;
			end--;
			for (int i = end - 1; i >= start; i--) {
				if (compare(list.get(i), list.get(i + 1)) > 0) {
					swap(list, i, i + 1);
					swapped = true;
				}
			}
			start++;
		}

		return list;
	}
}
