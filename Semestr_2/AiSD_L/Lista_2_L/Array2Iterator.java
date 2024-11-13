package AiSD_L.Lista_2_L;

import java.util.Iterator;

public class Array2Iterator<T> implements Iterator<T> {
    private final T[][] table;
    private final int MAX_LENGTH;
    private int i = 0, j = 0;
    private boolean isNext = true;
    private T nextElem = null;

    public Array2Iterator(Array2<T> array) {
        table = array.getTable();
        MAX_LENGTH = array.getMax();
    }

    private void findNextValid() {
        if (i == table.length - 1) {
            j++;
            i = 0;
        } else i++;

        while (i < table.length && table[i].length - 1 < j) i++;

        if (i >= table.length && j >= MAX_LENGTH - 1) {
            isNext = false;
            nextElem = null;
        } else {
            isNext = true;
            nextElem = table[i][j];
        }
    }

    @Override
    public boolean hasNext() {
        return isNext;
    }

    @Override
    public T next() {
        T element = nextElem;
        System.out.println(i + " " + j);
        findNextValid();
        return element;
    }
}
