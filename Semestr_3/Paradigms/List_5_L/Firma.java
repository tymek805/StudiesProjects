import java.util.ArrayList;

public class Firma {
    ArrayList<Pracownik> pracownicy;
    ArrayList<Zadanie> zadania;

    public Firma(){
        pracownicy = new ArrayList<>();
        zadania = new ArrayList<>();
    }

    public void addWorker(Pracownik pracownik) {
        pracownicy.add(pracownik);
    }
    public void addTask(Zadanie zadanie) {
        zadania.add(zadanie);
    }

    public Pracownik findByPesel(String pesel){
        return pracownicy.stream()
                .filter(pracownik -> pracownik.getPesel().equals(pesel))
                .findFirst()
                .orElse(null);
    }

    public void raiseForAll(float raise) {
        pracownicy.forEach(pracownik -> pracownik.percentRaise(raise));
    }

    public void giveBonus(int bonus, Specjalista specjalista){
        specjalista.setPremia(bonus);
    }

    public ArrayList<Zadanie> filer(String name, Status status, Pracownik pracownik){
        return new ArrayList<>(zadania.stream()
                .filter(zadanie -> (name == null || zadanie.getNazwa().equals(name)))
                .filter(zadanie -> (status == null || zadanie.getTaskStatus().equals(status)))
                .filter(zadanie -> (pracownik == null || zadanie.getContractor().getPesel().equals(pracownik.getPesel())))
                .sorted((z1, z2) -> Boolean.compare(z2.getIsPilne(), z1.getIsPilne()))
                .toList());
    }

    @Override
    public String toString() {
        return "Firma{" +
                "pracownicy=" + pracownicy +
                ", zadania=" + zadania +
                '}';
    }
}