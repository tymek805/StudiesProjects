package Semestr_2.SchedulingAlgorithms;

import java.util.ArrayList;
import java.util.LinkedList;

public class FIFO {
    private LinkedList<Process> processes;

    public FIFO(ArrayList<Process> processes){
        this.processes = new LinkedList<>(processes);
    }

    public void execute(){
        int time = 0;
        float waitingTime = 0;
        int numberOfProcesses = 0;
        while (!processes.isEmpty()){
            Process process = processes.removeFirst();
            process.setWaitingTime(Math.max(time - process.getArrivalTime(), 0));
            time += process.getProcessLength();
            waitingTime += process.getWaitingTime();
            numberOfProcesses++;
        }
        System.out.println("FIFO: " + waitingTime / numberOfProcesses);
    }
}
