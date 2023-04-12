package Semestr_2.Lista_5_L;

import java.util.Comparator;

import Semestr_2.Lista_5_L.core.SortingAlgorithm;
import Semestr_2.Lista_5_L.testing.*;
import Semestr_2.Lista_5_L.testing.comparators.*;
import Semestr_2.Lista_5_L.testing.generation.*;
import Semestr_2.Lista_5_L.testing.generation.conversion.*;

public class Main {

	public static void main(String[] args) {
		Comparator<MarkedValue<Integer>> markedComparator = new MarkedValueComparator<Integer>(new IntegerComparator());

		Generator<MarkedValue<Integer>> generator = new MarkingGenerator<Integer>(new RandomIntegerArrayGenerator(10));

		SortingAlgorithm<MarkedValue<Integer>> algorithm = new BubbleSort<>(markedComparator);

		int[] val = {1, 2, 5, 10, 20, 40, 50, 60 ,80, 100, 250, 500, 1000, 2000, 5000};
		for (int i : val){
			Result result = Tester.runNTimes(algorithm, generator, i, 20);
			System.out.println(result.averageComparisons() + " " + result.averageSwaps());
		}
	}

	private static void printStatistic(String label, double average, double stdDev) {
		System.out.println(label + ": " + double2String(average) + " +- " + double2String(stdDev));
	}

	private static String double2String(double value) {
		return String.format("%.12f", value);
	}

}
