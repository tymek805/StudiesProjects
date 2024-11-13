package SO.FrameAllocationAlgorithms.Algorithms;

import SO.FrameAllocationAlgorithms.Elements.Process;

import java.util.ArrayList;

public class ZoneAllocation implements FrameAlgorithm {
    private final int NUMBER_OF_PROCESSES;
    private final int MEMORY_SIZE;
    private final double DELTA_T;
    private final Process[] processes;
    private final double botCoherent, topCoherent;

    public ZoneAllocation(int MEMORY_SIZE, int NUMBER_OF_PROCESSES, Process[] processes, int DELTA_T, double botCoherent, double topCoherent) {
        this.NUMBER_OF_PROCESSES = NUMBER_OF_PROCESSES;
        this.MEMORY_SIZE = MEMORY_SIZE;
        this.processes = processes;
        this.DELTA_T = DELTA_T;
        this.botCoherent = botCoherent;
        this.topCoherent = topCoherent;
    }

    @Override
    public int[] allocateFrames(int[] allocatedFrames) {
        if (allocatedFrames == null) return new EqualAllocation(MEMORY_SIZE, NUMBER_OF_PROCESSES).allocateFrames(null);
        int[] frames = new int[NUMBER_OF_PROCESSES];
        ArrayList<Integer> val = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_PROCESSES; i++) {
            Process process = processes[i];
            double e = process.getPageFaultsInterval() / DELTA_T;
            int t = process.getPageFaultsInterval();
            val.add(t);
            if (e < botCoherent && process.getFrames().size() > 1)
                frames[i] = allocatedFrames[i] - 1;
            else if (e > topCoherent)
                frames[i] = allocatedFrames[i] + 1;
            else
                frames[i] = allocatedFrames[i];
        }

        return frames;
    }

    @Override
    public boolean getStatic() {
        return false;
    }
}
