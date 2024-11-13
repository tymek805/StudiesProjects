package SO.SchedulingAlgorithmsDisk.Algorithms;

import SO.SchedulingAlgorithmsDisk.Process;

import java.util.ArrayList;
import java.util.LinkedList;

public class FCFS extends Algorithm {
    private final LinkedList<Process> processes;
    private int currentHeadPosition;

    public FCFS(ArrayList<Process> processes, int currentHeadPosition) {
        this.processes = new LinkedList<>(processes);
        this.currentHeadPosition = currentHeadPosition;
    }

    public void execute() {

        float distanceSum = 0;
        int numberOfProcesses = 0;
        while (!processes.isEmpty()) {
            Process process = processes.removeFirst();
            distanceSum += Math.abs(process.getHeadPosition() - currentHeadPosition);
            currentHeadPosition = process.getHeadPosition();
            numberOfProcesses++;
        }
        printResults(distanceSum, numberOfProcesses);
    }
}
