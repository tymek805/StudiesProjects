package AiSD_L.Lista_6_L.testing;

import java.util.List;

import AiSD_L.Lista_6_L.core.SortingAlgorithm;
import AiSD_L.Lista_6_L.measuring.Timer;
import AiSD_L.Lista_6_L.testing.generation.Generator;

public class Tester {

    public static <T> RunResult runOnce(SortingAlgorithm<MarkedValue<T>> algorithm, Generator<MarkedValue<T>> generator, int size) {
        algorithm.reset();

        List<MarkedValue<T>> list = generator.generate(size);
        Timer timer = new Timer();

        timer.start();
        algorithm.sort(list);
        timer.stop();

        return new RunResult(timer.durationMillis(), algorithm.comparisons(), algorithm.swaps(),
                ListChecker.isSorted(list, algorithm.baseComparator()), ListChecker.isStable(list, algorithm.baseComparator()));
    }

    public static <T> Result runNTimes(SortingAlgorithm<MarkedValue<T>> algorithm, Generator<MarkedValue<T>> generator,
                                       int size, int repetitions) {
        double averageTime = 0.0;
        double averageTimeSquared = 0.0;

        double averageComparisons = 0.0;
        double averageComparisonsSquared = 0.0;

        double averageSwaps = 0.0;
        double averageSwapsSquared = 0.0;

        boolean sorted = true;
        boolean stable = true;

        for (int n = 1; n <= repetitions; ++n) {
            RunResult result = runOnce(algorithm, generator, size);

            averageTime = updatedAverage(averageTime, result.timeInMilliseconds(), n);
            averageTimeSquared = updatedAverage(averageTimeSquared,
                    (double) result.timeInMilliseconds() * (double) result.timeInMilliseconds(), n);

            averageComparisons = updatedAverage(averageComparisons, result.comparisons(), n);
            averageComparisonsSquared = updatedAverage(averageComparisonsSquared,
                    (double) result.comparisons() * (double) result.comparisons(), n);

            averageSwaps = updatedAverage(averageSwaps, result.swaps(), n);
            averageSwapsSquared = updatedAverage(averageSwapsSquared, (double) result.swaps() * (double) result.swaps(), n);

            sorted = sorted && result.sorted();
            stable = stable && result.stable();
        }

        return new Result(averageTime, calculateStdDev(averageTime, averageTimeSquared),
                averageComparisons, calculateStdDev(averageComparisons, averageComparisonsSquared),
                averageSwaps, calculateStdDev(averageSwaps, averageSwapsSquared),
                sorted, stable);
    }

    private static double updatedAverage(double average, double value, int n) {
        return average + (value - average) / n;
    }

    private static double calculateStdDev(double average, double averagedSquares) {
        return Math.sqrt(averagedSquares - (average * average));
    }

}
