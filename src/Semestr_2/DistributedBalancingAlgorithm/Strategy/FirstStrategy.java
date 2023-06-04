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
        Processor requestedProcessor = processors[r.nextInt(processors.length)];

        boolean isNativeOverloaded = nativeProcessor.isOverloaded();
        boolean isOverloaded = requestedProcessor.isOverloaded();

        int overloadedCPUs = 0;
        while (overloadedCPUs < numberOfChances && (nativeProcessor == requestedProcessor || isOverloaded)) {
            if (isOverloaded) overloadedCPUs++;
            requestedProcessor = processors[r.nextInt(processors.length)];
            if (requestedProcessor != nativeProcessor)
                isOverloaded = requestedProcessor.isOverloaded();
            else isOverloaded = isNativeOverloaded;
        }
        return overloadedCPUs == numberOfChances ? nativeProcessor : requestedProcessor;
    }
}
