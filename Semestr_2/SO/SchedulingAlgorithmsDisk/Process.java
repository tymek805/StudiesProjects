package SO.SchedulingAlgorithmsDisk;

public class Process {
    private final int headPosition;
    private final int deadline;
    private boolean isDead = false;

    public Process(int headPosition, int deadline) {
        this.headPosition = headPosition;
        this.deadline = deadline;
    }

    public int getHeadPosition() {
        return headPosition;
    }

    public int getDeadline() {
        return deadline;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }
}
