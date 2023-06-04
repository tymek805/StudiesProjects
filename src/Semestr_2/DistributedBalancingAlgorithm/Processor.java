package Semestr_2.DistributedBalancingAlgorithm;

import java.util.ArrayList;

public class Processor {
    private final ArrayList<Process> processes = new ArrayList<>();
    private int counter = 0;
    private double load = 0.0;
    private final double minThreshold, maxThreshold;

    public Processor(double minThreshold, double maxThreshold){
        this.minThreshold = minThreshold;
        this.maxThreshold = maxThreshold;
    }
    public void addProcess(Process process){
        processes.add(process);
        load += process.getLoad();
    }
    public Process[] relieveLoad(){
        int x = (int) (processes.size() * 0.4);
        Process[] relieveProcesses = new Process[x];
        for (int i = 0; i < x; i++){
            Process removeProcess = processes.remove(i);
            load -= removeProcess.getLoad();
            relieveProcesses[i] = removeProcess;
        }
        return relieveProcesses;
    }
    public void executeProcesses(){
        for (Process process : processes)
            process.decreasePhaseLength();
        processes.removeIf(process -> {
            boolean isFinished = process.getPhaseLength() <= 0;
            if (isFinished) load -= process.getLoad();
            return isFinished;
        });
    }

    public boolean isOverloaded(){
        counter++;
        return load > maxThreshold;
    }
    public double getLoad() {
        counter++;
        return load;
    }
    public int getCounter() {return counter;}
}
