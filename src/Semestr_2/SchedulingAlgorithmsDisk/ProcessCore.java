package Semestr_2.SchedulingAlgorithmsDisk;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProcessCore {
    private final ArrayList<Process> fcfsProcesses = new ArrayList<>();
    private final ArrayList<Process> sstfProcesses = new ArrayList<>();
    private final ArrayList<Process> scanProcesses = new ArrayList<>();
    private final ArrayList<Process> cscanProcesses = new ArrayList<>();
    private final ArrayList<Process> edfProcesses = new ArrayList<>();
    private final ArrayList<Process> fdScanProcesses = new ArrayList<>();
    private final static int INITIAL_HEAD_POSITION = 100;
    private final static int DISC_SIZE = 1000;
    private final static int NUMBER_OF_PROCESSES = 500;
    private final static int MAX_DEADLINE = 20;

    private final Process[] processes = new Process[NUMBER_OF_PROCESSES];;

    public void execute(){
        createProcesses();
        FCFS fcfs = new FCFS(new ArrayList<>(List.of(processes)), INITIAL_HEAD_POSITION);
        fcfs.execute();

        SSTF sstf = new SSTF(new ArrayList<>(List.of(processes)), INITIAL_HEAD_POSITION);
        sstf.execute();

        SCAN scan = new SCAN(new ArrayList<>(List.of(processes)), INITIAL_HEAD_POSITION, DISC_SIZE);
        scan.execute();

        CSCAN cscan = new CSCAN(new ArrayList<>(List.of(processes)), INITIAL_HEAD_POSITION, DISC_SIZE);
        cscan.execute();

        EDF edf = new EDF(new ArrayList<>(List.of(processes)), INITIAL_HEAD_POSITION);
        edf.execute();

        FDSCAN fdscan = new FDSCAN(new ArrayList<>(List.of(processes)), INITIAL_HEAD_POSITION);
        fdscan.execute();
    }

    public void createProcesses(){
        Random r = new Random();
        for (int i = 0; i < NUMBER_OF_PROCESSES; i++){
            int val = r.nextInt(DISC_SIZE);
            int deadline = r.nextInt(MAX_DEADLINE);
            processes[i] = new Process(val, deadline);
        }
    }

    public static void main(String[] args) {
        new ProcessCore().execute();
    }
}
