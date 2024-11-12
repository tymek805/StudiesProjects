package SO.SchedulingAlgorithms;

import java.util.ArrayList;
import java.util.LinkedList;

public class RR {
    private int quantum;
    private LinkedList<Process> processes;
    private ArrayList<Process> endedProcesses = new ArrayList<>();
    private int time = 0;

    public RR(ArrayList<Process> processes, int quantum) {
        this.processes = new LinkedList<>();
        this.processes.addAll(processes);
        this.quantum = quantum;
    }

    public void execute() {
        float waitingTime = 0;

        while (!processes.isEmpty()) {
            Process process = processes.removeFirst();
            processes.addLast(process);

            if (process.getArrivalTime() <= time) {
                if (process.getRemainingTime() > quantum) {
                    time += quantum;
                    process.setRemainingTime(process.getRemainingTime() - quantum);
                } else if (process.getRemainingTime() <= quantum) {
                    time += process.getRemainingTime();
                    process.setRemainingTime(0);
                    process.setWaitingTime(time - process.getArrivalTime() - process.getProcessLength());
                    processes.remove(process);
                    endedProcesses.add(process);
                    waitingTime += process.getWaitingTime();
//                    waitingTime += time - process.getArrivalTime() - process.getProcessLength();
                }
            }
        }
        float n = endedProcesses.size();
        System.out.println("RR: " + waitingTime / n);
    }
}
