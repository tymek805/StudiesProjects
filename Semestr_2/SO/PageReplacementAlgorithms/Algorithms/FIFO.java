package SO.PageReplacementAlgorithms.Algorithms;

import SO.PageReplacementAlgorithms.Page;

import java.util.LinkedList;

public class FIFO extends Algorithm {
    public FIFO(Page[] pages, int physicalMemorySize) {
        super(pages, physicalMemorySize);
    }

    public void execute() {
        LinkedList<Page> frames = new LinkedList<>();
        int numberOfPageFaults = 0;
        for (Page page : pages) {
            if (!inFrames(page, frames.listIterator())) {
                if (frames.size() >= physicalMemorySize)
                    frames.removeFirst();
                frames.addLast(page);
                numberOfPageFaults++;
            }
        }
        System.out.println(getClass().getSimpleName() + " faults: " + numberOfPageFaults);
    }
}
