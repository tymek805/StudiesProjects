package Semestr_2.SchedulingAlgorithms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class SRTF {
    private ArrayList<Process> pendingProcesses;
    private PriorityQueue<Process> processes;
    private ArrayList<Process> endedProcesses = new ArrayList<>();
    private int time = 0;

    public SRTF(ArrayList<Process> processes){
        this.pendingProcesses = processes;
        this.processes = new PriorityQueue<>(Comparator.comparingInt(Process::getRemainingTime));
    }

    public void execute(){
        float waitingTime = 0;
        while (!processes.isEmpty() || !pendingProcesses.isEmpty()){
            addToQueue();

            Process process = processes.peek();
            process.setRemainingTime(process.getRemainingTime() - 1);
            time++;

            if (process.getRemainingTime() == 0){
                process.setWaitingTime(Math.max(time - process.getArrivalTime() - process.getProcessLength(), 0));
                endedProcesses.add(process);
                processes.remove(process);
                waitingTime += process.getWaitingTime();
            }
        }
        System.out.println("SRTF: " + waitingTime / endedProcesses.size());
    }

    private void addToQueue() {
        for (int i = 0; i < pendingProcesses.size(); i++) {
            Process process = pendingProcesses.get(i);
            if (process.getArrivalTime() <= time) {
                processes.add(process);
                pendingProcesses.remove(i);
            }
        }
    }
}
