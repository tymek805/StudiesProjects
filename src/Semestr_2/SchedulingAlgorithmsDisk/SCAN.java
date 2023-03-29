package Semestr_2.SchedulingAlgorithmsDisk;

import java.util.ArrayList;
import java.util.Comparator;

public class SCAN {
    private final ArrayList<Process> processes;
    private int currentHeadPosition;
    private final int MAX_DISC_SIZE;

    public SCAN(ArrayList<Process> processes, int currentHeadPosition, int MAX_DISC_SIZE){
        this.processes = processes;
        this.processes.sort(Comparator.comparingInt(Process::getHeadPosition));
        this.currentHeadPosition = currentHeadPosition;
        this.MAX_DISC_SIZE = MAX_DISC_SIZE - 1;
    }

    public void execute(){
        float distanceSum = 0;
        int numberOfProcesses = 0;

        int idx = -1;
        int lastDistance = Math.abs(processes.get(0).getHeadPosition() - currentHeadPosition);
        for (int i = 0; i < processes.size(); i++){
            int distance = Math.abs(processes.get(i).getHeadPosition() - currentHeadPosition);
            if (lastDistance >= distance) lastDistance = distance;
            else if (idx == -1) idx = i;
        }

        distanceSum += MAX_DISC_SIZE - currentHeadPosition;
        while (!processes.isEmpty()){
            Process process = processes.get(idx);
            System.out.println(process.getHeadPosition());
            currentHeadPosition = process.getHeadPosition();
            processes.remove(process);
            if (idx == 0)
                distanceSum += MAX_DISC_SIZE - process.getHeadPosition();
            if (idx > processes.size() - 1) idx--;
            numberOfProcesses++;
        }

        System.out.println("SCAN");
        System.out.println("Dystans pokonany: " + distanceSum);
        System.out.println("Średnie wychylenie głowicy: " + distanceSum / numberOfProcesses);
        System.out.println();
    }


    public void addProcess(Process process){processes.add(process);}
}
