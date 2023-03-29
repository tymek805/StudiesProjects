package Semestr_2.SchedulingAlgorithmsDisk;

import java.util.ArrayList;
import java.util.LinkedList;

public class FCFS {
    private final LinkedList<Process> processes;
    private int currentHeadPosition;

    public FCFS(ArrayList<Process> processes, int currentHeadPosition){
        this.processes = new LinkedList<>(processes);
        this.currentHeadPosition = currentHeadPosition;
    }

    public void execute(){
        float distanceSum = 0;
        int numberOfProcesses = 0;
        while (!processes.isEmpty()){
            Process process = processes.removeFirst();
            distanceSum += Math.abs(process.getHeadPosition() - currentHeadPosition);
            currentHeadPosition = process.getHeadPosition();
            numberOfProcesses++;
        }
        System.out.println("FCFS");
        System.out.println("Dystans pokonany: " + distanceSum);
        System.out.println("Średnie wychylenie głowicy: " + distanceSum / numberOfProcesses);
        System.out.println();
    }
}
