package SO.SchedulingAlgorithmsDisk.Algorithms;

import SO.SchedulingAlgorithmsDisk.Process;

import java.util.ArrayList;
import java.util.Comparator;

public class EDF extends Algorithm {
    private final ArrayList<Process> processes;
    private int currentHeadPosition;

    public EDF(ArrayList<Process> processes, int currentHeadPosition) {
        this.processes = processes;
        this.processes.sort(Comparator.comparingInt(Process::getDeadline));
        this.currentHeadPosition = currentHeadPosition;
    }

    public void execute() {
        float distanceSum = 0;
        int numberOfProcesses = 0;

        while (!processes.isEmpty() && processes.get(0).getDeadline() != -1) {
            Process process = processes.remove(0);
            distanceSum += Math.abs(process.getHeadPosition() - currentHeadPosition);
            currentHeadPosition = process.getHeadPosition();
            numberOfProcesses++;
        }

        if (!processes.isEmpty()) {
            int idx = -1;
            int lastDistance = Math.abs(processes.get(0).getHeadPosition() - currentHeadPosition);
            for (int i = 0; i < processes.size(); i++) {
                int distance = Math.abs(processes.get(i).getHeadPosition() - currentHeadPosition);
                if (lastDistance >= distance) lastDistance = distance;
                else if (idx == -1) idx = i - 1;
            }

            while (!processes.isEmpty()) {
                Process process = processes.get(idx);
                if (idx > 0) idx--;
                distanceSum += Math.abs(process.getHeadPosition() - currentHeadPosition);
                currentHeadPosition = process.getHeadPosition();
                processes.remove(process);
                numberOfProcesses++;
            }
        }
        printResults(distanceSum, numberOfProcesses);
    }

    public void addProcess(Process process) {
        processes.add(process);
    }
}
