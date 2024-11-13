package SO.FrameAllocationAlgorithms;

import SO.FrameAllocationAlgorithms.Algorithms.*;
import SO.FrameAllocationAlgorithms.Elements.Page;
import SO.FrameAllocationAlgorithms.Elements.Process;

import java.util.*;

public class MemoryManagement {
    private final static int NUMBER_OF_PROCESSES = 5;
    private final static int MEMORY_SIZE = 50;
    private final static int MAX_PROCESS_LENGTH = 100;
    private final static int MAX_PAGE_REFERENCE = 10000;
    private final static int DELTA_T = 5;
    private final static double LOWER_INTERVAL = 0.8;
    private final static double HIGHER_INTERVAL = 0.9;

    public MemoryManagement() {
        Process[] processes = createProcesses();
//        Process[] processes = {new Process(6, new int[]{2, 5}), new Process(9, new int[]{5, 11}), new Process(12, new int[]{11, 16})};
        FrameAlgorithm[] algorithms = {
                new EqualAllocation(MEMORY_SIZE, NUMBER_OF_PROCESSES),
                new ProportionalAllocation(MEMORY_SIZE, NUMBER_OF_PROCESSES, processes),
                new ControllingFrequency(MEMORY_SIZE, NUMBER_OF_PROCESSES, processes, DELTA_T, LOWER_INTERVAL, HIGHER_INTERVAL),
                new ZoneAllocation(MEMORY_SIZE, NUMBER_OF_PROCESSES, processes, DELTA_T, LOWER_INTERVAL, HIGHER_INTERVAL)};

        for (FrameAlgorithm algorithm : algorithms) {
            int[] pageFaults = executeLRU(processes, algorithm);
            int sum = 0;
            for (Integer pageFault : pageFaults) {
//                System.out.println(pageFault);
                sum += pageFault;
            }
            System.out.println(algorithm.getClass().getSimpleName() + ": " + sum);
        }
    }

    private Process[] createProcesses() {
        Process[] processes = new Process[NUMBER_OF_PROCESSES];
        if (MEMORY_SIZE < NUMBER_OF_PROCESSES)
            throw new IllegalArgumentException("Memory should be greater than number of processes!");

        int boundStep = MAX_PAGE_REFERENCE / NUMBER_OF_PROCESSES;
        int botBound = 0;

        Random r = new Random();
        for (int i = 0; i < NUMBER_OF_PROCESSES; i++)
            processes[i] = new Process(
                    r.nextInt(MAX_PROCESS_LENGTH / 4, MAX_PROCESS_LENGTH),
                    new int[]{botBound, botBound += boundStep});

        return processes;
    }

    private int[] executeLRU(Process[] processes, FrameAlgorithm frameAlgorithm) {
        int[] numberOfPageFaults = new int[NUMBER_OF_PROCESSES];
        int time = 0;

        int[] allocatedFrames = frameAlgorithm.allocateFrames(null);
        while (time < MAX_PROCESS_LENGTH) {
            boolean allFinished = true;
            if (!frameAlgorithm.getStatic() && time % DELTA_T == 0)
                allocatedFrames = frameAlgorithm.allocateFrames(allocatedFrames);
            for (int i = 0; i < NUMBER_OF_PROCESSES; i++) {
                Process process = processes[i];
                if (time % DELTA_T == 0) process.resetPageFaultsInterval();
                Page page = process.getPage(time);
                if (page != null) {
                    LinkedList<Page> frames = process.getFrames();
                    if (!inFrames(page, frames.listIterator())) {
//                        System.out.println("Page: "+ page.getPageReference() + " To remove page: " + (process.getFrames().size() == 0 ? " "   : process.getFrames().getFirst().getPageReference()));
                        if (frames.size() >= allocatedFrames[i])
                            frames.removeFirst();
                        numberOfPageFaults[i]++;
                        process.incrementPageFaultsInterval();
                    } else frames.removeIf(page1 -> page1.getPageReference() == page.getPageReference());
                    frames.addLast(page);

                    allFinished = false;
                }
            }
            if (allFinished) break;
            time++;
        }
        clearFrames(processes);
        return numberOfPageFaults;
    }

    private void clearFrames(Process[] processes) {
        for (Process process : processes)
            process.setFrames(new LinkedList<>());
    }

    private boolean inFrames(Page page, ListIterator<Page> iterator) {
        int pageReference = page.getPageReference();
        while (iterator.hasNext())
            if (iterator.next().getPageReference() == pageReference)
                return true;
        return false;
    }

    public static void main(String[] args) {
        new MemoryManagement();
    }
}
