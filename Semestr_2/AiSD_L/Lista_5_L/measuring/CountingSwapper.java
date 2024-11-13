package AiSD_L.Lista_5_L.measuring;

import java.util.List;

public class CountingSwapper<T> extends Counter {

    public void swap(List<T> list, int index1, int index2) {
        T temp = list.get(index1);

        list.set(index1, list.get(index2));
        list.set(index2, temp);

        increment();
    }
}
