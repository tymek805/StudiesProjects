package PSiO.Lista_1;

import java.util.Arrays;

public class zad_2 {
    public static void main(String[] args) {
        float numbers[] = {2, 3, 1};
        System.out.print(Arrays.toString(Sorting(numbers)));
    }

    private static float[] Sorting(float[] numbers) {
        for (int i = numbers.length; i > 0; i--) {
            for (int j = 0; j < numbers.length - 1; j++) {
                float a = numbers[j];
                float b = numbers[j + 1];
                if (a < b) {
                    numbers[j] = b;
                    numbers[j + 1] = a;
                }
            }
        }
        return numbers;
    }
}
