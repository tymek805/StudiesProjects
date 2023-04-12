package Semestr_2.SchedulingAlgorithmsDisk.Algorithms;

import Semestr_2.SchedulingAlgorithmsDisk.Process;

import java.util.ArrayList;
import java.util.Comparator;

public class FDSCAN extends Algorithm{
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
        int numberOfDeadProcesses = 0;
        int time = 0;
        while (!processes.isEmpty()){
            int idx = 0;
            for (int i = 0; i < processes.size(); i++){
                Process currentProcess = processes.get(i);
                int distance = Math.abs(currentProcess.getHeadPosition() - currentHeadPosition);
                if (distance > currentProcess.getDeadline() - time && idx < processes.size() - 1 && !currentProcess.isDead()){
                    idx++;
                    numberOfDeadProcesses++;
                    currentProcess.setDead(true);
                }
                else
                    i = processes.size();
            }
            Process process = processes.get(idx);
            time += process.getHeadPosition();

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
        printResults(distanceSum, numberOfProcesses);
        System.out.println("Liczba porzuconych zgłoszeń: " + numberOfDeadProcesses);
    }
}
