package Semestr_2.Lista_6_L.testing.comparators;

import java.util.Comparator;

import Semestr_2.Lista_6_L.testing.MarkedValue;

public class MarkedValueComparator<T> implements Comparator<MarkedValue<T>> {
	
	private Comparator<? super T> comparator;
	
	public MarkedValueComparator(Comparator<? super T> comparator) {
		this.comparator = comparator;
	}
	
	public Comparator<? super T> baseComparator() {
		return comparator;
	}
	
	@Override
	public final int compare(MarkedValue<T> lhs, MarkedValue<T> rhs) {
		return comparator.compare(lhs.value(), rhs.value());
	}

}
