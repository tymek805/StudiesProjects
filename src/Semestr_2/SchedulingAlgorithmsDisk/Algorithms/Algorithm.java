package Semestr_2.SchedulingAlgorithmsDisk.Algorithms;

public abstract class Algorithm {
    public void execute(){}

    protected void printResults(float distanceSum, int numberOfProcesses) {
        System.out.println(getClass().getSimpleName());
        System.out.println("Dystans pokonany: " + distanceSum);
        System.out.println("Średnie wychylenie głowicy: " + distanceSum / numberOfProcesses);
    }
}
