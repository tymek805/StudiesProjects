package Semestr_2.Lista_6_L.testing.generation.conversion;

import java.util.LinkedList;

import Semestr_2.Lista_6_L.testing.generation.Generator;

public class LinkedListGenerator<T> implements Generator<T> {
	private Generator<? extends T> generator;
	
	public LinkedListGenerator(Generator<? extends T> generator) {
		this.generator = generator;
	}
	
	@Override
	public LinkedList<T> generate(int size) {
		LinkedList<T> list = new LinkedList<T>();
		
		for(T value : generator.generate(size)) {
			list.add(value);
		}
		
		return list;
	}

}
