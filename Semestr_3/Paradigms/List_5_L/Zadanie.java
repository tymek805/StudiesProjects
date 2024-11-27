public class Zadanie {
    private String nazwa;
    private boolean isPilne;
    private Status taskStatus;
    private Pracownik contractor;

    public Zadanie(String nazwa, boolean isPilne) {
        this.nazwa = nazwa;
        this.isPilne = isPilne;
        this.taskStatus = Status.WPrzygotowaniu;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public boolean getIsPilne() {
        return isPilne;
    }

    public void setIsPilne(boolean isPilne) {
        this.isPilne = isPilne;
    }

    public Status getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Status taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Pracownik getContractor() {
        return contractor;
    }

    public void setContractor(Pracownik contractor) {
        this.contractor = contractor;
        taskStatus = Status.WToku;
    }

    public void endTask(){
        taskStatus = Status.Zakonczone;
    }

    @Override
    public String toString() {
        return "Zadanie{" +
                "nazwa='" + nazwa + '\'' +
                ", isPilne=" + isPilne +
                ", taskStatus=" + taskStatus +
                ", contractor=" + contractor.getPesel() +
                '}';
    }
}
