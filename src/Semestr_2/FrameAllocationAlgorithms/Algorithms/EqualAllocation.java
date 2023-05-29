package Semestr_2.FrameAllocationAlgorithms.Algorithms;

public class EqualAllocation implements FrameAlgorithm {
    private final int NUMBER_OF_PROCESSES;
    private final int MEMORY_SIZE;
    public EqualAllocation(int MEMORY_SIZE, int NUMBER_OF_PROCESSES){
        this.NUMBER_OF_PROCESSES = NUMBER_OF_PROCESSES;
        this.MEMORY_SIZE = MEMORY_SIZE;
    }

    @Override
    public int[] allocateFrames(int[] ignore) {
        int[] frames = new int[NUMBER_OF_PROCESSES];
        int numberOfFrames = MEMORY_SIZE / NUMBER_OF_PROCESSES;
        for (int i = 0; i < NUMBER_OF_PROCESSES; i++)
            frames[i] = numberOfFrames;
        return frames;
    }

    @Override
    public boolean getStatic() {return true;}
}