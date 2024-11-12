package SO.SchedulingAlgorithms;

import java.util.ArrayList;
import java.util.Random;

public class ProcessCore {
    private ArrayList<Process> fifoProcesses = new ArrayList<>();
    private ArrayList<Process> sjfProcesses = new ArrayList<>();
    private ArrayList<Process> srtfProcesses = new ArrayList<>();
    private ArrayList<Process> rrProcesses = new ArrayList<>();

    public void execute() {
        generateTestProcesses();

        new FIFO(fifoProcesses).execute();
        new SJF(sjfProcesses).execute();
        new SRTF(srtfProcesses).execute();
        new RR(rrProcesses, 30).execute();
    }

    public void generateTestProcesses() {
        Random random = new Random();
        double mean = 50;
        double stdDeviation = 20;

        for (int i = 1; i <= 500; i++) {
            int randomLength = (int) (random.nextGaussian() * stdDeviation + mean);
            randomLength = Math.max(1, Math.min(randomLength, (int) mean * 2));
            int randomArrival = random.nextInt(50);

            fifoProcesses.add(new Process(i, randomArrival, randomLength));
            sjfProcesses.add(new Process(i, randomArrival, randomLength));
            srtfProcesses.add(new Process(i, randomArrival, randomLength));
            rrProcesses.add(new Process(i, randomArrival, randomLength));
        }
    }

    public static void main(String[] args) {
        new ProcessCore().execute();
    }
}
