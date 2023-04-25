package Semestr_2.Lista_6_L.testing.generation;

import java.util.ArrayList;
import java.util.Random;

public class RandomIntegerArrayGenerator implements Generator<Integer> {
	
	private Random rng;
	private int maxValue;
	
	public RandomIntegerArrayGenerator(int maxValue) {
		rng = new Random();
		this.maxValue = maxValue;
	}
	
	public RandomIntegerArrayGenerator(int maxValue, long seed) {
		rng = new Random(seed);
		this.maxValue = maxValue;
	}

	@Override
	public ArrayList<Integer> generate(int size) {
		ArrayList<Integer> list = new ArrayList<Integer>(size);
		
		for(int i = 0; i < size; ++i) {
			list.add(rng.nextInt(maxValue));
		}
		
		return list;
	}

}