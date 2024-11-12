package AiSD_L.Lista_9_L;

import AiSD_L.Lista_9_L.Dictionaries.BSTDictionary;
import AiSD_L.Lista_9_L.Dictionaries.IDictionary;
import AiSD_L.Lista_9_L.Dictionaries.RBDictionary;
import AiSD_L.Lista_9_L.Dictionaries.SkipDictionary;

import java.util.Comparator;
import java.util.Random;

public class TestMain {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        Comparator<Integer> comparator = Comparator.comparingInt(o -> o);
        IDictionary<Integer, String>[] dictionaries = new IDictionary[6];
        dictionaries[0] = new BSTDictionary<>(comparator);
        dictionaries[1] = new RBDictionary<>(comparator);
        dictionaries[2] = new SkipDictionary<>(0.0, comparator);
        dictionaries[3] = new SkipDictionary<>(0.25, comparator);
        dictionaries[4] = new SkipDictionary<>(0.5, comparator);
        dictionaries[5] = new SkipDictionary<>(0.8, comparator);

        Timer timer = new Timer();
        int numberOfElements = 10000;
        int[] val = new int[numberOfElements];
        Random r = new Random();
        for (int i = 0; i < numberOfElements; i++)
            val[i] = r.nextInt(numberOfElements);

        for (IDictionary<Integer, String> dictionary : dictionaries) {
            timer.start();

            for (Integer i : val)
                dictionary.insert(i, "T");

//            for (Integer i : val)
//                dictionary.get(i);

//            for (Integer i : val)
//                dictionary.remove(i);

            timer.stop();
//            System.out.println(dictionary.getClass().getSimpleName() + ": " + timer.durationMillis());
            System.out.println(timer.durationNanos());
            timer.reset();
        }
    }
}
