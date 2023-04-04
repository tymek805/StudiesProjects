package Semestr_2.SchedulingAlgorithmsDisk;

import Semestr_2.SchedulingAlgorithmsDisk.Algorithms.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProcessCore {
    private final static int INITIAL_HEAD_POSITION = 50;
    private final static int DISC_SIZE = 1000;
    private final static int NUMBER_OF_PROCESSES = 500;
    private final static int MAX_DEADLINE = 20;

    private final Process[] processes = new Process[NUMBER_OF_PROCESSES];

    public void execute(){
        createProcesses();

        Algorithm[] algorithms = new Algorithm[]{
            new FCFS(new ArrayList<>(List.of(processes)), INITIAL_HEAD_POSITION),
            new SSTF(new ArrayList<>(List.of(processes)), INITIAL_HEAD_POSITION),
            new SCAN(new ArrayList<>(List.of(processes)), INITIAL_HEAD_POSITION, DISC_SIZE),
            new CSCAN(new ArrayList<>(List.of(processes)), INITIAL_HEAD_POSITION, DISC_SIZE),
            new EDF(new ArrayList<>(List.of(processes)), INITIAL_HEAD_POSITION),
            new FDSCAN(new ArrayList<>(List.of(processes)), INITIAL_HEAD_POSITION)
        };

        for (Algorithm algorithm : algorithms)
            algorithm.execute();
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