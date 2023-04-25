package Semestr_2.Lista_6_L.testing.generation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ShuffledIntegerArrayGenerator implements Generator<Integer> {
	
	private Random rng;
	
	public ShuffledIntegerArrayGenerator() {
		rng = new Random();
	}
	
	public ShuffledIntegerArrayGenerator(long seed) {
		rng = new Random(seed);
	}

	@Override
	public ArrayList<Integer> generate(int size) {
		ArrayList<Integer> list = new ArrayList<Integer>(size);
		
		for(int i = 0; i < size; ++i) {
			list.add(i);
		}
		
		Collections.shuffle(list, rng);
		
		return list;
	}

}