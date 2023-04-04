package Semestr_2.SchedulingAlgorithmsDisk;

import java.util.ArrayList;
import java.util.Comparator;

public class CSCAN {
    private final ArrayList<Process> processes;
    private int currentHeadPosition;
    private final int MAX_DISC_SIZE;

    public CSCAN(ArrayList<Process> processes, int currentHeadPosition, int MAX_DISC_SIZE){
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
            if (idx > processes.size() - 1 && !processes.isEmpty()){
                distanceSum += MAX_DISC_SIZE;
                currentHeadPosition = 0;
                idx = 0;
            }
            else if (processes.isEmpty()) distanceSum += process.getHeadPosition();
            numberOfProcesses++;
        }

        System.out.println("CSCAN");
        System.out.println("Dystans pokonany: " + distanceSum);
        System.out.println("Średnie wychylenie głowicy: " + distanceSum / numberOfProcesses);
    }


    public void addProcess(Process process){processes.add(process);}
}
