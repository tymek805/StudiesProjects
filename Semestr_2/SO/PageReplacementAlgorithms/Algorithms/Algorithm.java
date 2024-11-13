package SO.PageReplacementAlgorithms.Algorithms;

import SO.PageReplacementAlgorithms.Page;

import java.util.ListIterator;

public abstract class Algorithm {
    protected final Page[] pages;
    protected final int physicalMemorySize;

    protected Algorithm(Page[] pages, int physicalMemorySize) {
        this.pages = pages;
        this.physicalMemorySize = physicalMemorySize;
    }

    public abstract void execute();

    protected boolean inFrames(Page page, ListIterator<Page> iterator) {
        int pageReference = page.getPageReference();
        while (iterator.hasNext())
            if (iterator.next().getPageReference() == pageReference)
                return true;
        return false;
    }
}
