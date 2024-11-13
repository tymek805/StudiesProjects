package SO.SchedulingAlgorithms;

public class Process {
    private final int PID;
    private final int arrivalTime;
    private int waitingTime = 0;
    private int remainingTime;
    private final int processLength;

    public Process(int PID, int arrivalTime, int processLength) {
        this.PID = PID;
        this.arrivalTime = arrivalTime;
        this.processLength = processLength;
        this.remainingTime = processLength;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getProcessLength() {
        return processLength;
    }

    public void getStan() {
        System.out.println("PID: " + PID + " Arrival Time: " + arrivalTime + " Waiting time: " + waitingTime + " Remaining time: " + remainingTime);
    }
}
