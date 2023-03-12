package Semestr_2.Lista_1_C;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayIterator<T> implements Iterator<T> {
	private T array[];
	private int pos = 0;

	public ArrayIterator(T[] array) {
		this.array = array;
	}

	public boolean hasNext() {
		return pos < array.length;
	}

	public T next() throws NoSuchElementException {
		if (hasNext())
			return array[pos++];
		else
			throw new NoSuchElementException();
	}
}
