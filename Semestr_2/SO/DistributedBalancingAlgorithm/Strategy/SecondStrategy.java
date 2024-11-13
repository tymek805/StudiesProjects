package SO.DistributedBalancingAlgorithm.Strategy;

import SO.DistributedBalancingAlgorithm.Process;
import SO.DistributedBalancingAlgorithm.Processor;

import java.util.*;

public class SecondStrategy extends Strategy {
    public final double maximalThreshold;

    public SecondStrategy(Processor[] processors, Process[] processes, int deltaT, double maximalThreshold) {
        super(processors, processes, deltaT);
        this.maximalThreshold = maximalThreshold;
    }

    @Override
    protected Processor findAvailableProcessor(Processor nativeProcessor) {
        if (!nativeProcessor.isOverloaded()) return nativeProcessor;

        Random r = new Random();
        ArrayList<Processor> CPUs = new ArrayList<>(List.of(processors));
        CPUs.remove(nativeProcessor);

        Processor requestedProcessor = CPUs.get(r.nextInt(CPUs.size()));
        while (CPUs.size() > 1 && requestedProcessor.isOverloaded()) {
            CPUs.remove(requestedProcessor);
            requestedProcessor = CPUs.get(r.nextInt(CPUs.size()));
        }
        return requestedProcessor.isOverloaded() ? null : requestedProcessor;
    }
}
