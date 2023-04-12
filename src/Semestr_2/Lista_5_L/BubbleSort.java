package Semestr_2.Lista_5_L;

import java.util.Comparator;
import java.util.List;

import Semestr_2.Lista_5_L.core.SortingAlgorithm;

public class BubbleSort<T> extends SortingAlgorithm<T> {

	public BubbleSort(Comparator<? super T> comparator) {
		super(comparator);
	}

	@Override
	public List<T> sort(List<T> list) {
		int size = list.size();
		
		for(int pass = 1; pass < size; ++pass) {
			for(int left = 0; left < (size - pass); ++left) {
				int right = left + 1;
				
				if(compare(list.get(left), list.get(right)) > 0) {
					swap(list, left, right);
				}
			}
		}
		
		return list;
	}

}
