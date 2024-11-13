package SO.DistributedBalancingAlgorithm.Strategy;

import SO.DistributedBalancingAlgorithm.Process;
import SO.DistributedBalancingAlgorithm.Processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ThirdStrategy extends Strategy {
    public final double minimalThreshold, maximalThreshold;

    public ThirdStrategy(Processor[] processors, Process[] processes, int deltaT, double minimalThreshold, double maximalThreshold) {
        super(processors, processes, deltaT);
        this.minimalThreshold = minimalThreshold;
        this.maximalThreshold = maximalThreshold;
    }

    @Override
    protected Processor findAvailableProcessor(Processor nativeProcessor) {
        relieveOverloaded();
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

    private void relieveOverloaded() {
        for (Processor processor : processors) {
            if (processor.getLoad() < minimalThreshold) {
                Random r = new Random();
                ArrayList<Processor> CPUs = new ArrayList<>(List.of(processors));
                CPUs.remove(processor);

                Processor requestedProcessor = CPUs.get(r.nextInt(CPUs.size()));
                while (CPUs.size() > 1 && !requestedProcessor.isOverloaded()) {
                    CPUs.remove(requestedProcessor);
                    requestedProcessor = CPUs.get(r.nextInt(CPUs.size()));
                }
                if (!requestedProcessor.isOverloaded()) {
                    Process[] relieveProcesses = requestedProcessor.relieveLoad();
                    migrations += relieveProcesses.length;
                    for (Process process : relieveProcesses)
                        processor.addProcess(process);
                }
            }
        }
    }
}
