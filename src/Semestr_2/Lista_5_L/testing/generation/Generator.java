package Semestr_2.Lista_5_L.testing.generation;

import java.util.List;

public interface Generator<T> {
	List<T> generate(int size);
}
