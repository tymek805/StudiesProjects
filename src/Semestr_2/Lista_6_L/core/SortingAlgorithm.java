package Semestr_2.Lista_6_L.core;

import java.util.Comparator;
import java.util.List;

import Semestr_2.Lista_6_L.measuring.CountingComparator;
import Semestr_2.Lista_6_L.measuring.CountingSwapper;

public abstract class SortingAlgorithm<T> {
	
	private CountingComparator<T> comparator;
	private CountingSwapper<T> swapper;
	
	public SortingAlgorithm(Comparator<? super T> comparator) {
		this.comparator = new CountingComparator<T>(comparator);
		swapper = new CountingSwapper<T>();
	}
	
	public void reset() {
		comparator.reset();
		swapper.reset();
	}
	
	public long comparisons() {
		return comparator.count();
	}
	
	public long swaps() {
		return swapper.count();
	}
	
	public Comparator<? super T> baseComparator() {
		return comparator.baseComparator();
	}
	
	protected int compare(T lhs, T rhs) {
		return comparator.compare(lhs, rhs);
	}
	
	protected void swap(List<T> list, int index1, int index2) {
		swapper.swap(list, index1, index2);
	}
	
	public abstract List<T> sort(List<T> list);

}
