package Semestr_2.PageReplacementAlgorithms.Algorithms;

import Semestr_2.PageReplacementAlgorithms.Page;

import java.util.ArrayList;
import java.util.Random;

public class RAND extends Algorithm {
    public RAND (Page[] pages, int physicalMemorySize){
        super(pages, physicalMemorySize);
    }

    public void execute() {
        ArrayList<Page> frames = new ArrayList<>();
        int numberOfPageFaults = 0;
        for (Page page : pages){
            if (!inFrames(page, frames.listIterator())) {
                if (frames.size() >= physicalMemorySize)
                    frames.remove(new Random().nextInt(physicalMemorySize));
                frames.add(page);
                numberOfPageFaults++;
            }
        }
        System.out.println(getClass().getSimpleName() + " faults: " + numberOfPageFaults);
    }
}
