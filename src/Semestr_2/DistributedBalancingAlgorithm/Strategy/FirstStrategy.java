package Semestr_2.DistributedBalancingAlgorithm.Strategy;

import Semestr_2.DistributedBalancingAlgorithm.Process;
import Semestr_2.DistributedBalancingAlgorithm.Processor;

import java.util.*;

public class FirstStrategy extends Strategy {
    private final int numberOfChances;

    public FirstStrategy(Processor[] processors, Process[] processes, int deltaT, int numberOfChances) {
        super(processors, processes, deltaT);
        this.numberOfChances = numberOfChances;
    }

    @Override
    protected Processor findAvailableProcessor(Processor nativeProcessor){
        Random r = new Random();
        ArrayList<Processor> CPUs = new ArrayList<>(List.of(processors));
        CPUs.remove(nativeProcessor);

        int overloadedCPUs = 0;
        Processor requestedProcessor = CPUs.get(r.nextInt(CPUs.size()));
        while (overloadedCPUs < numberOfChances && CPUs.size() > 1 && requestedProcessor.isOverloaded()) {
            overloadedCPUs++;
            CPUs.remove(requestedProcessor);
            requestedProcessor = CPUs.get(r.nextInt(CPUs.size()));
        }
        return requestedProcessor.isOverloaded() ? nativeProcessor : requestedProcessor;
    }
}
