package Semestr_2.Lista_2_L;

import java.util.Iterator;

public class Array2<T> implements Iterable<T>{
    private final T[][] table;
    private int MAX_LENGTH = -1;

    @SuppressWarnings("unchecked")
    public Array2(int[] numberOfElements) {
        if (numberOfElements == null || numberOfElements.length < 1)
            throw new NullPointerException("Array cannot be created!");

        table = (T[][]) new Object[numberOfElements.length][];
        for (int i = 0; i < numberOfElements.length; i++){
            if (numberOfElements[i] < 1)
                throw new NullPointerException("Unsupported value! Value cannot be less than 1");

            table[i] = (T[]) new Object[numberOfElements[i]];
            MAX_LENGTH = Math.max(MAX_LENGTH, numberOfElements[i]);
            for (int j = 0; j < numberOfElements[i]; j++){
                table[i][j] = null;
            }
        }
    }

    public T get(int i, int j){
        if (i >= table.length || j >= table[i].length)
            throw new ArrayIndexOutOfBoundsException("Index out of bounds for index: " + i + " " + j);
        return table[i][j];
    }
    public void set(T newElem, int i, int j){
        if (i < table.length && j < table[i].length)
            table[i][j] = newElem;
    }

    public T[][] getTable() {return table;}
    public int getMax() {return MAX_LENGTH;}

    @Override
    public Iterator<T> iterator() {
        return new Array2SkipIterator<T>(this, 2);
    }
}
