package Semestr_2.SchedulingAlgorithmsDisk;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class SSTF {
    private final ArrayList<Process> processes;
    private int currentHeadPosition;

    public SSTF(ArrayList<Process> processes, int currentHeadPosition){
        this.processes = processes;
        this.processes.sort(Comparator.comparingInt(Process::getHeadPosition));
        this.currentHeadPosition = currentHeadPosition;
    }

    public void execute(){
        float distanceSum = 0;
        int numberOfProcesses = 0;

        int idx = -1;
        int lastDistance = Math.abs(processes.get(0).getHeadPosition() - currentHeadPosition);
        for (int i = 0; i < processes.size(); i++){
            int distance = Math.abs(processes.get(i).getHeadPosition() - currentHeadPosition);
            if (lastDistance >= distance) lastDistance = distance;
            else if (idx == -1) idx = i - 1;
        }

        while (!processes.isEmpty()){
            Process process = processes.get(idx);
            if (idx > 0) idx--;
            distanceSum += Math.abs(process.getHeadPosition() - currentHeadPosition);
            currentHeadPosition = process.getHeadPosition();
            processes.remove(process);
            numberOfProcesses++;
        }

        System.out.println("SSTF");
        System.out.println("Dystans pokonany: " + distanceSum);
        System.out.println("Średnie wychylenie głowicy: " + distanceSum / numberOfProcesses);
        System.out.println();
    }


    public void addProcess(Process process){processes.add(process);}
}
