package Semestr_2.PageReplacementAlgorithms.Algorithms;

import Semestr_2.PageReplacementAlgorithms.Page;

import java.util.*;

public class LRU extends Algorithm{
    public LRU(Page[] pages, int physicalMemorySize){
        super(pages, physicalMemorySize);
    }

    public void execute() {
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
}