package SO.PageReplacementAlgorithms.Algorithms;

import SO.PageReplacementAlgorithms.Page;

import java.util.LinkedList;

public class ALRU extends Algorithm {
    public ALRU(Page[] pages, int physicalMemorySize) {
        super(pages, physicalMemorySize);
    }

    public void execute() {
        LinkedList<Page> frames = new LinkedList<>();

        int numberOfPageFaults = 0;
        for (Page page : pages) {
            if (!inFrames(page, frames.listIterator())) {
                if (frames.size() >= physicalMemorySize) {
                    findVictim(frames);
                    frames.removeFirst();
                }
                numberOfPageFaults++;
            } else frames.remove(page);
            frames.addLast(page);
        }
        System.out.println(getClass().getSimpleName() + "  faults: " + numberOfPageFaults);
    }

    private void findVictim(LinkedList<Page> frames) {
        for (int i = 0; i < frames.size(); i++) {
            Page currentPage = frames.getFirst();
            if (currentPage.hasSecondChance()) {
                currentPage.setHasSecondChance(false);
                frames.addLast(frames.removeFirst());
            } else {
                return;
            }
        }
    }
}
