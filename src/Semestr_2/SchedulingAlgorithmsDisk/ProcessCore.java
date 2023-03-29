package Semestr_2.SchedulingAlgorithmsDisk;

import java.util.ArrayList;
import java.util.Random;

public class ProcessCore {
    private final ArrayList<Process> fcfsProcesses = new ArrayList<>();
    private final ArrayList<Process> sstfProcesses = new ArrayList<>();
    private final ArrayList<Process> scanProcesses = new ArrayList<>();
    private final ArrayList<Process> cscanProcesses = new ArrayList<>();
    private final static int MAX_DISC_SIZE = 200;

    public void execute(){
        test();

        FCFS fcfs = new FCFS(fcfsProcesses, 50);
        fcfs.execute();

        SSTF sstf = new SSTF(sstfProcesses, 50);
        sstf.execute();

        SCAN scan = new SCAN(scanProcesses, 50, MAX_DISC_SIZE);
        scan.execute();

        CSCAN cscan = new CSCAN(cscanProcesses, 50, MAX_DISC_SIZE);
        cscan.execute();
    }
    public void test(){
        int[] val = new int[] {82, 170, 43, 140, 24, 16, 190};
        for (int j : val) {
            fcfsProcesses.add(new Process(j));
            sstfProcesses.add(new Process(j));
            scanProcesses.add(new Process(j));
            cscanProcesses.add(new Process(j));
        }
    }

    public static void main(String[] args) {
        new ProcessCore().execute();
    }
}
