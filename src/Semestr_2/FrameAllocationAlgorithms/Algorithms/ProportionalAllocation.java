package Semestr_2.FrameAllocationAlgorithms.Algorithms;

import Semestr_2.FrameAllocationAlgorithms.Elements.Process;

public class ProportionalAllocation implements FrameAlgorithm {
    private final int NUMBER_OF_PROCESSES;
    private final int MEMORY_SIZE;
    private final Process[] processes;

    public ProportionalAllocation(int MEMORY_SIZE, int NUMBER_OF_PROCESSES, Process[] processes){
        this.NUMBER_OF_PROCESSES = NUMBER_OF_PROCESSES;
        this.MEMORY_SIZE = MEMORY_SIZE;
        this.processes = processes;
    }

    @Override
    public int[] allocateFrames(int[] ignore) {
        int[] frames = new int[NUMBER_OF_PROCESSES];
        int processesLength = 0;
        for (Process p : processes)
            processesLength += p.getPages().length;
        for (int i = 0; i < NUMBER_OF_PROCESSES; i++)
            frames[i] = processes[i].getPages().length * MEMORY_SIZE / processesLength;
        return frames;
    }

    @Override
    public boolean getStatic() {return true;}
}