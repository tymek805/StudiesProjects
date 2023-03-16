package Semestr_2.Lista_2_L;

public class MainTest {
    public void testArray2Iterator(int[] array){
        Array2<Object> array2 = new Array2<>(array);
        try {
            Array2Iterator<Object> iterator = new Array2Iterator<>(array2);
            int numberOfObjects = 0;
            while (iterator.hasNext()) {
                iterator.next();
                numberOfObjects++;
            }
            int sum = 0;
            for (int k : array) sum += k;
            System.out.println(sum);
            System.out.println(numberOfObjects);
        } catch (NullPointerException e) {System.err.println(e.getMessage());}
    }
    public void testArray2SkipIterator(int[] array, int n){
        Array2<Object> array2 = new Array2<>(array);

        try {
            Array2SkipIterator<Object> iterator = new Array2SkipIterator<>(array2, n);
            int numberOfObjects = 0;
            while (iterator.hasNext()) {
                iterator.next();
                numberOfObjects++;
            }
            int sum = 0;
            for (int k : array) sum += k;
            System.out.println(sum);
            System.out.println(numberOfObjects);
        } catch (NullPointerException e) {System.err.println(e.getMessage());}
    }


    public static void main(String[] args) {
//        int[] array = new int[]{2, 2};
//        int[] array = new int[]{3, 3, 2, 2};
//        int[] array = new int[]{4, 4, 2, 5, 4};
        int[] array = new int[]{5, 3, 2, 4, 6};
        MainTest mainTest = new MainTest();
        mainTest.testArray2Iterator(array);
        mainTest.testArray2SkipIterator(array, 2);
    }
}
