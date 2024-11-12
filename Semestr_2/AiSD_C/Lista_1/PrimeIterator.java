package AiSD_C.Lista_1;

import java.util.Iterator;

public final class PrimeIterator implements Iterator<Integer> {
    private int bot;
    private final int top;
    private int elemNext = -1;

    public PrimeIterator(int bot, int top) {
        this.bot = bot;
        this.top = top;
        findNextValid();
    }

    private void findNextValid() {
        while (hasNext()) {
            elemNext = bot++;
            if (isPrime(elemNext)) {
                return;
            }
        }
        elemNext = -1;
    }

    @Override
    public boolean hasNext() {
        return bot <= top;
    }

    @Override
    public Integer next() {
        int nextValue = elemNext;
        findNextValid();
        return nextValue;
    }

    public static boolean isPrime(int num) {
        if (num <= 1) return false;
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        PrimeIterator iterator = new PrimeIterator(0, 8);

        while (iterator.hasNext())
            System.out.println(iterator.next());
    }
}
