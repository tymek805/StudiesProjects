package AiSD_L.Lista_5_L.testing.generation;

import java.util.ArrayList;

public class ReversedIntegerArrayGenerator implements Generator<Integer> {

    @Override
    public ArrayList<Integer> generate(int size) {
        ArrayList<Integer> list = new ArrayList<Integer>(size);

        for (int i = size - 1; i >= 0; --i) {
            list.add(i);
        }

        return list;
    }

}
