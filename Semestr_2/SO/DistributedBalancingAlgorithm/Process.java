package SO.DistributedBalancingAlgorithm;

public class Process {
    private final int load;
    private int phaseLength;
    private final int processorID;
    private final int arrivalTime;

    public Process(int load, int phaseLength, int arrivalTime, int processorID) {
        this.load = load;
        this.phaseLength = phaseLength;
        this.arrivalTime = arrivalTime;
        this.processorID = processorID;
    }

    public int getLoad() {
        return load;
    }

    public int getPhaseLength() {
        return phaseLength;
    }

    public void decreasePhaseLength() {
        phaseLength--;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getProcessorID() {
        return processorID;
    }
}
