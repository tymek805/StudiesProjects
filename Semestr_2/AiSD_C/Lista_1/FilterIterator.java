package AiSD_C.Lista_1;

import java.util.Iterator;
import java.util.function.Predicate;

public final class FilterIterator<T> implements Iterator<T> {

    private Iterator<T> iterator;
    private Predicate<T> predicate;

    private T elemNext = null;
    private boolean isNext = true;

    public FilterIterator(Iterator<T> iterator, Predicate<T> predicate) {
        super();
        this.iterator = iterator;
        this.predicate = predicate;
        findNextValid();
    }

    private void findNextValid() {
        while (iterator.hasNext()) {
            elemNext = iterator.next();
            if (predicate.test(elemNext)) {
                return;
            }
        }
        isNext = false;
        elemNext = null;
    }

    @Override
    public boolean hasNext() {
        return isNext;
    }

    @Override
    public T next() {
        T nextValue = elemNext;
        findNextValid();
        return nextValue;
    }
}
