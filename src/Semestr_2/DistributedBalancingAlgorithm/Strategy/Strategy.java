package Semestr_2.DistributedBalancingAlgorithm.Strategy;

import Semestr_2.DistributedBalancingAlgorithm.Process;
import Semestr_2.DistributedBalancingAlgorithm.Processor;

import java.util.*;

public abstract class Strategy {
    protected final Processor[] processors;
    protected final ArrayList<Process> processes;
    protected final int deltaT;

    protected Strategy(Processor[] processors, Process[] processes, int deltaT){
        this.processors = Arrays.copyOf(processors, processors.length);
        this.processes = new ArrayList<>(List.of(processes));
        this.deltaT = deltaT;
    }

    public void execute() {
        ArrayList<Double> meanLoadArray = new ArrayList<>();
        int migrations = 0;
        int time = 0;

        while (!processes.isEmpty()){
            processes.sort(Comparator.comparingInt(Process::getArrivalTime));
            for (Process process : processes) {
                if (process.getArrivalTime() > time) break;
                Processor nativeProcessor = processors[process.getProcessorID()];
                Processor executiveProcessor = findAvailableProcessor(nativeProcessor);
                if (executiveProcessor == null) break;
                if (nativeProcessor != executiveProcessor) migrations++;
                executiveProcessor.addProcess(process);
            }
            int finalTime = time;
            processes.removeIf(process -> process.getArrivalTime() <= finalTime);
            for (Processor processor : processors)
                processor.executeProcesses();
            time++;
            if (time % deltaT == 0) meanLoadArray.add(measureLoad());
        }
        printResults(meanLoadArray, migrations);
    }
    protected abstract Processor findAvailableProcessor(Processor nativeProcessor);

    private double measureLoad(){
        double sum = 0.0;
        for (Processor processor : processors)
            sum += processor.getLoad();
        return sum / processors.length;
    }

    private int sumLoadCalls(){
        int loadAsk = 0;
        for (Processor p : processors)
            loadAsk += p.getCounter();
        return loadAsk;
    }
    private double calculateStandardDev(double x, ArrayList<Double> meanLoadArray){
        double standardDeviation = 0.0;
        for (double num : meanLoadArray) standardDeviation += Math.pow(num - x, 2);
        return Math.sqrt(standardDeviation / meanLoadArray.size());
    }

    protected void printResults(ArrayList<Double> meanLoadArray, int migrations){
        OptionalDouble average = meanLoadArray.stream().mapToDouble(a -> a).average();

        System.out.println(this.getClass().getSimpleName());
        String[] measurements = {
                "Średnie obciążenie procesorów:\t" + average.getAsDouble() + " %",
                "Średnie odchylenie: \t\t\t±" + calculateStandardDev(average.getAsDouble(), meanLoadArray) + " %",
                "Ilość zapytań o obciążenie: \t" + sumLoadCalls(),
                "Ilość migracji procesów: \t\t" + migrations,
        };
        for (String measurement : measurements) System.out.println(measurement);
        System.out.println();
    }
}
