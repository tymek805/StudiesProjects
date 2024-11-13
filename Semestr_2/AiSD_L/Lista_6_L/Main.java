package AiSD_L.Lista_6_L;

import java.util.Comparator;

import AiSD_L.Lista_6_L.core.SortingAlgorithm;
import AiSD_L.Lista_6_L.testing.*;
import AiSD_L.Lista_6_L.testing.comparators.*;
import AiSD_L.Lista_6_L.testing.generation.*;
import AiSD_L.Lista_6_L.testing.generation.conversion.*;

public class Main {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        Comparator<MarkedValue<Integer>> integerComparator = new MarkedValueComparator<>(new IntegerComparator());
        Generator<Integer>[] generators = new Generator[]{new OrderedIntegerArrayGenerator(), new RandomIntegerArrayGenerator(10000), new ReversedIntegerArrayGenerator(), new ShuffledIntegerArrayGenerator()};
        for (Generator<Integer> generator : generators) {
            Generator<MarkedValue<Integer>> currentGenerator = new LinkedListGenerator<>(new MarkingGenerator<>(generator));
            SortingAlgorithm<MarkedValue<Integer>> algorithm = new QuickSort<>(integerComparator, false);
            int[] val = {1, 10, 20, 50, 100, 250, 500, 1000, 1500, 3000, 5000};
            for (int i : val) {
                Result r = Tester.runNTimes(algorithm, currentGenerator, i, 20);
//				System.out.println(r.averageComparisons() + " " + i);
                System.out.println(r.averageComparisons() + " " + r.averageSwaps());
            }
        }
//
//		Generator<MarkedValue<Integer>> generator = new LinkedListGenerator<>(new MarkingGenerator<>(new RandomIntegerArrayGenerator(100000)));
//		SortingAlgorithm<MarkedValue<Integer>> algorithm = new MergeSortDouble<>(integerComparator);
//		Result result = Tester.runNTimes(algorithm, generator, 100, 20);
//		System.out.println("always sorted: " + result.sorted());
    }

    private static void printStatistic(String label, double average, double stdDev) {
        System.out.println(label + ": " + double2String(average) + " +- " + double2String(stdDev));
    }

    private static String double2String(double value) {
        return String.format("%.12f", value);
    }
}
