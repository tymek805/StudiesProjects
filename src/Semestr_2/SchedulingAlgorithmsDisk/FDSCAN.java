package Semestr_2.SchedulingAlgorithmsDisk;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class FDSCAN {
    private final ArrayList<Process> processes;
    private int currentHeadPosition;

    public FDSCAN(ArrayList<Process> processes, int currentHeadPosition){
        this.processes = processes;
        this.processes.sort(Comparator.comparingInt(Process::getDeadline));
        this.currentHeadPosition = currentHeadPosition;
    }

    public void execute(){
        float distanceSum = 0;
        int numberOfProcesses = 0;
        int time = 0;
        while (!processes.isEmpty()){
            int idx = 0;
            for (int i = 0; i < processes.size(); i++){
                Process currentProcess = processes.get(i);
                int distance = Math.abs(currentProcess.getHeadPosition() - currentHeadPosition);
                if (distance > currentProcess.getDeadline() - time && idx < i)
                    idx++;
                else i = processes.size();
            }
            Process process = processes.get(idx);
            time += process.getHeadPosition();

            int direction = 1;
            if (process.getHeadPosition() < currentHeadPosition) direction = -1;
            int distance = (processes.get(idx).getHeadPosition() - currentHeadPosition) * direction;

            this.processes.sort(Comparator.comparingInt(Process::getHeadPosition));

            ArrayList<Process> toRemove = new ArrayList<>();
            for (Process testProcess : processes) {
                if (testProcess.getHeadPosition() > process.getHeadPosition() && testProcess.getHeadPosition() < currentHeadPosition ||
                        testProcess.getHeadPosition() < process.getHeadPosition() && testProcess.getHeadPosition() > currentHeadPosition ) {
                    toRemove.add(testProcess);
                }
            }
            for (Process removeProcess : toRemove)
                processes.remove(removeProcess);

            distanceSum += Math.abs(process.getHeadPosition() - currentHeadPosition);
            currentHeadPosition = process.getHeadPosition();
            processes.remove(process);
            this.processes.sort(Comparator.comparingInt(Process::getDeadline));
            numberOfProcesses++;
        }

        System.out.println("FDSCAN");
        System.out.println("Dystans pokonany: " + distanceSum);
        System.out.println("Średnie wychylenie głowicy: " + distanceSum / numberOfProcesses);
    }

    public void addProcess(Process process){processes.add(process);}
}
