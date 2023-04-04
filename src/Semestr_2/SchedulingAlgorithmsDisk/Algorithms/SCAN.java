package Semestr_2.SchedulingAlgorithmsDisk.Algorithms;

import Semestr_2.SchedulingAlgorithmsDisk.Process;

import java.util.ArrayList;
import java.util.Comparator;

public class SCAN extends Algorithm{
    private final ArrayList<Process> processes;
    private int currentHeadPosition;
    private final int MAX_DISC_SIZE;

    public SCAN(ArrayList<Process> processes, int currentHeadPosition, int MAX_DISC_SIZE){
        super();
        this.processes = processes;
        this.processes.sort(Comparator.comparingInt(Process::getHeadPosition));
        this.currentHeadPosition = currentHeadPosition;
        this.MAX_DISC_SIZE = MAX_DISC_SIZE;
    }

    public void execute(){
        float distanceSum = 0;
        int numberOfProcesses = 0;

        int idx = 0;
        int lastDistance = Math.abs(processes.get(0).getHeadPosition() - currentHeadPosition);
        for (int i = 1; i < processes.size(); i++){
            int distance = Math.abs(processes.get(i).getHeadPosition() - currentHeadPosition);
            if (lastDistance >= distance) lastDistance = distance;
            else {idx = i - 1; i = processes.size();}
        }
        distanceSum += MAX_DISC_SIZE - currentHeadPosition;
        while (!processes.isEmpty()){
            Process process = processes.get(idx);
            currentHeadPosition = process.getHeadPosition();
            processes.remove(process);
            if (idx > processes.size() - 1) idx--;
            if (idx == -1)
                distanceSum += MAX_DISC_SIZE - process.getHeadPosition();
            numberOfProcesses++;
        }
        printResults(distanceSum, numberOfProcesses);
    }


    public void addProcess(Process process){processes.add(process);}
}
