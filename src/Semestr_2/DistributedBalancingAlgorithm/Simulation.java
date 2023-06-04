package Semestr_2.DistributedBalancingAlgorithm;

import Semestr_2.DistributedBalancingAlgorithm.Strategy.FirstStrategy;
import Semestr_2.DistributedBalancingAlgorithm.Strategy.SecondStrategy;
import Semestr_2.DistributedBalancingAlgorithm.Strategy.Strategy;
import Semestr_2.DistributedBalancingAlgorithm.Strategy.ThirdStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Simulation {
    private final static int NUMBER_OF_PROCESSORS = 5;      // N
    private final static int NUMBER_OF_PROCESSES = 1000;
    private final static int MAX_PHASE_LENGTH = 10;
    private final static int MAX_ARRIVAL_TIME = 500;
    private final static double MAXIMAL_THRESHOLD = 90.0;   // p
    private final static double MINIMAL_THRESHOLD = 10.0;   // r
    private final static int NUMBER_OF_CHANCES = 10;       // z
    public static final int DELTA_T = 10;

    public Simulation(){
        Processor[] processors = createProcessors();
        Processor[] processors1 = createProcessors();
        Processor[] processors2 = createProcessors();

        Process[] processes1 = createProcesses();
        Process[] processes2 = createProcesses();
        Process[] processes3 = createProcesses();

        Strategy[] strategies = {
                new FirstStrategy(processors, processes1, DELTA_T, NUMBER_OF_CHANCES),
                new SecondStrategy(processors1, processes2, DELTA_T, MAXIMAL_THRESHOLD),
                new ThirdStrategy(processors2, processes3, DELTA_T, MINIMAL_THRESHOLD, MAXIMAL_THRESHOLD)
        };
        for (Strategy strategy : strategies)
            strategy.execute();
    }

    private Processor[] createProcessors(){
        Processor[] processors = new Processor[NUMBER_OF_PROCESSORS];
        for (int i = 0; i < NUMBER_OF_PROCESSORS; i++)
            processors[i] = new Processor(MINIMAL_THRESHOLD, MAXIMAL_THRESHOLD);
        return processors;
    }
    private Process[] createProcesses(){
        Process[]  processes = new Process[NUMBER_OF_PROCESSES];
        Random r = new Random();
        for (int i = 0; i < NUMBER_OF_PROCESSES; i++)
            processes[i] = (new Process(
                    r.nextInt(1,100),
                    r.nextInt(1, MAX_PHASE_LENGTH),
                    r.nextInt(MAX_ARRIVAL_TIME),
                    r.nextInt(NUMBER_OF_PROCESSORS)));
        return processes;
    }

    public static void main(String[] args) {
        Simulation simulation = new Simulation();
    }
}
