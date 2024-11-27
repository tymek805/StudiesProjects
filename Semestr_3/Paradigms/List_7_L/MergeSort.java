import java.util.ArrayList;
import java.util.Arrays;

public class MergeSort {
    private final int numberOfProcessors;

    private static class SortThread extends Thread {
        SortThread(int[] array, int begin, int end) {
            super(() -> MergeSort.mergeSort(array, begin, end));
            this.start();
        }
    }

    public MergeSort() {
        this.numberOfProcessors = Runtime.getRuntime().availableProcessors();
    }

    public int[] sort(int[] array) {
        System.out.println("List to sort: " + Arrays.toString(array));
        int length = array.length;
        boolean isExact = length % numberOfProcessors == 0;
        int amountPerThread = isExact ? length / numberOfProcessors : length / (numberOfProcessors - 1);
        ArrayList<SortThread> threads = new ArrayList<>();

        for (int i = 0; i < length; i += amountPerThread) {
            int remain = length - i;
            int end = remain < amountPerThread ? i + remain - 1 : i + amountPerThread - 1;
            threads.add(new SortThread(array, i, end));
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException ignored) {
            }
        }

        for (int i = 0; i < length; i += amountPerThread) {
            int mid = i == 0 ? 0 : i - 1;
            int remain = length - i;
            int end = remain < amountPerThread ? i + remain - 1 : i + amountPerThread - 1;
            merge(array, 0, mid, end);
        }

        return array;
    }

    private static void mergeSort(int[] array, int begin, int end) {
        if (begin < end) {
            int mid = (begin + end) / 2;
            mergeSort(array, begin, mid);
            mergeSort(array, mid + 1, end);
            merge(array, begin, mid, end);
        }
    }

    public static void merge(int[] array, int begin, int mid, int end) {
        int[] temp = new int[end - begin + 1];
        int i = begin, j = mid + 1;
        int k = 0;

        while (i <= mid && j <= end) {
            if (array[i] <= array[j]) {
                temp[k] = array[i];
                i += 1;
            } else {
                temp[k] = array[j];
                j += 1;
            }
            k += 1;
        }

        while (i <= mid) {
            temp[k] = array[i];
            i += 1;
            k += 1;
        }

        while (j <= end) {
            temp[k] = array[j];
            j += 1;
            k += 1;
        }

        for (i = begin, k = 0; i <= end; i++, k++) {
            array[i] = temp[k];
        }
    }

    public static void main(String[] args) {
        MergeSort mergeSort = new MergeSort();
        int[] array = {16, 4, 23, 20, 4, 49, 37, 46, 18, 6, 37, 20, 33, 49, 33, 28, 16, 32, 27, 39, 6, 24, 6, 42, 25, 30, 15, 20, 32, 6, 17, 42, 7, 45, 2, 36, 14, 37, 32, 25, 4, 6, 36, 38, 45, 18, 32, 48};
        System.out.println("Sorted list: " + Arrays.toString(mergeSort.sort(array)));
    }
}