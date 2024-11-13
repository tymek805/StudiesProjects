package SO.PageReplacementAlgorithms.Algorithms;

import SO.PageReplacementAlgorithms.Page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class OPT extends Algorithm {
    public OPT(Page[] pages, int physicalMemorySize) {
        super(pages, physicalMemorySize);
    }

    public void execute() {
        ArrayList<Page> frames = new ArrayList<>();
        Map<Page, Long> counts = Arrays.stream(pages).collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        int numberOfPageFaults = 0;
        for (Page page : pages) {
            if (!inFrames(page, frames.listIterator())) {
                if (frames.size() >= physicalMemorySize) {
                    frames.remove(findLeastUsed(frames, counts));
                }
                frames.add(page);
                numberOfPageFaults++;
            }
            counts.put(page, counts.get(page) - 1);
        }
        System.out.println(getClass().getSimpleName() + "  faults: " + numberOfPageFaults);
    }

    private Page findLeastUsed(ArrayList<Page> frames, Map<Page, Long> counts) {
        long minValue = Long.MAX_VALUE;
        Page foundPage = null;
        for (Page page : frames) {
            long currentValue = counts.get(page);
            if (currentValue == 0) break;
            if (minValue > currentValue) {
                minValue = currentValue;
                foundPage = page;
            }
        }
        return foundPage;
    }
}
