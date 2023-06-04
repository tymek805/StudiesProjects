package Semestr_2.DistributedBalancingAlgorithm.Strategy;

import Semestr_2.DistributedBalancingAlgorithm.Process;
import Semestr_2.DistributedBalancingAlgorithm.Processor;

import java.util.*;

public class SecondStrategy extends Strategy{
    public final double maximalThreshold;

    public SecondStrategy(Processor[] processors, Process[] processes, int deltaT, double maximalThreshold) {
        super(processors, processes, deltaT);
        this.maximalThreshold = maximalThreshold;
    }

    @Override
    protected Processor findAvailableProcessor(Processor nativeProcessor) {
        if (!nativeProcessor.isOverloaded()) return nativeProcessor;

        Random r = new Random();
        Processor requestedProcessor = processors[r.nextInt(processors.length)];
        boolean isOverloaded = requestedProcessor.isOverloaded();

        HashMap<Processor, Integer> visitedProcessors = new HashMap<>();
        while (nativeProcessor == requestedProcessor || isOverloaded) {
            visitedProcessors.put(requestedProcessor, 0);
            requestedProcessor = processors[r.nextInt(processors.length)];
            if (requestedProcessor != nativeProcessor)
                isOverloaded = requestedProcessor.isOverloaded();
            else isOverloaded = true;
            if (visitedProcessors.size() == processors.length)
                return null;
        }
        return requestedProcessor;
    }
}
