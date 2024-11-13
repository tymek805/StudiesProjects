package SO.SchedulingAlgorithms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class SJF {
    private ArrayList<Process> pendingProcesses = new ArrayList<>();
    private PriorityQueue<Process> processes;
    private int time = 0;

    public SJF(ArrayList<Process> processes) {
        this.processes = new PriorityQueue<>(Comparator.comparingInt(Process::getProcessLength));
        this.processes.addAll(processes);
    }

    public void execute() {
        float waitingTime = 0;
        int numberOfProcesses = 0;
        while (!processes.isEmpty() || !pendingProcesses.isEmpty()) {
            addToQueue();
            Process process = processes.poll();
            process.setWaitingTime(Math.max(time - process.getArrivalTime(), 0));
            time += process.getProcessLength();
            waitingTime += process.getWaitingTime();
            numberOfProcesses++;
        }
        System.out.println("SJF: " + waitingTime / numberOfProcesses);
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

    public void addProcess(Process process) {
        pendingProcesses.add(process);
    }
}
