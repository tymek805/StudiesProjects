package SO.FrameAllocationAlgorithms.Algorithms;

import SO.FrameAllocationAlgorithms.Elements.Page;
import SO.FrameAllocationAlgorithms.Elements.Process;

import java.util.*;

public class LRU {
    private Page[] pages;
    private Process[] processes;
    private final int physicalMemorySize;

    public LRU(Process[] processes, int physicalMemorySize) {
        this.processes = processes;
        this.physicalMemorySize = physicalMemorySize;
    }

    public void execute(FrameAlgorithm frameAlgorithm) {
        LinkedList<Page> frames = new LinkedList<>();

        int numberOfPageFaults = 0;
        for (Page page : pages) {
            if (!inFrames(page, frames.listIterator())) {
                if (frames.size() >= physicalMemorySize)
                    frames.removeFirst();
                numberOfPageFaults++;
            } else frames.remove(page);
            frames.addLast(page);
        }
        System.out.println(getClass().getSimpleName() + "  faults: " + numberOfPageFaults);
    }

    private boolean inFrames(Page page, ListIterator<Page> iterator) {
        int pageReference = page.getPageReference();
        while (iterator.hasNext())
            if (iterator.next().getPageReference() == pageReference)
                return true;
        return false;
    }
}
