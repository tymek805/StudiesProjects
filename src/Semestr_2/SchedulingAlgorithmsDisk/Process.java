package Semestr_2.SchedulingAlgorithmsDisk;

public class Process {
    private final int headPosition;
    private final int deadline;

    public Process(int headPosition) {
        this.headPosition = headPosition;
        deadline = -1;
    }
    public Process(int headPosition, int deadline) {
        this.headPosition = headPosition;
        this.deadline = deadline;
    }
    public int getHeadPosition() {return headPosition;}
    public int getDeadline() {return deadline;}
}
