package SO.FrameAllocationAlgorithms.Elements;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Random;

public class Process {
    private LinkedList<Page> frames = new LinkedList<>();
    private final Page[] pages;
    private int pageFaultsInterval = 0;

    public Process(int processLength, int[] pagesBound) {
        if (pagesBound.length != 2) throw new NoSuchElementException("Bound parameter must have two arguments!");
//        if (processLength == 6)
//            pages = new Page[]{new Page(4), new Page(3), new Page(4), new Page(4), new Page(2), new Page(2)};
//        else if (processLength == 9)
//            pages = new Page[]{new Page(10), new Page(9), new Page(8), new Page(10), new Page(10), new Page(6), new Page(7), new Page(5), new Page(10)};
//        else
//            pages = new Page[]{new Page(11), new Page(14)   , new Page(15), new Page(13), new Page(12), new Page(14), new Page(11), new Page(12), new Page(13), new Page(15), new Page(14), new Page(11)};
        pages = generatePages(processLength, pagesBound);
    }

    //TODO zasada lokalności odwołań
    private Page[] generatePages(int processLength, int[] pagesBound) {
        Page[] pages = new Page[processLength];

        Random random = new Random();
        for (int i = 0; i < processLength; i++) {
            pages[i] = new Page(random.nextInt(pagesBound[0], pagesBound[1]));
        }
        return pages;
    }

    public Page getPage(int idx) {
        if (idx < 0)
            throw new IndexOutOfBoundsException("Index must be positive number");
        if (idx >= pages.length)
            return null;
        return pages[idx];
    }

    public Page[] getPages() {
        return pages;
    }

    public LinkedList<Page> getFrames() {
        return frames;
    }

    public void setFrames(LinkedList<Page> frames) {
        this.frames = frames;
    }

    public int getPageFaultsInterval() {
        return pageFaultsInterval;
    }

    public void incrementPageFaultsInterval() {
        pageFaultsInterval++;
    }

    public void resetPageFaultsInterval() {
        pageFaultsInterval = 0;
    }
}
