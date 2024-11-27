public class Specjalista extends Pracownik {
    private int premia;

    public Specjalista(String nazwisko, String imie, String pesel, int pensja, int premia) {
        super(nazwisko, imie, pesel, pensja);
        this.premia = premia;
    }

    public int getPremia() {
        return premia;
    }

    public void setPremia(int premia) {
        this.premia = premia;
    }

    @Override
    public String toString() {
        return "Specjalista{" +
                "nazwisko='" + getNazwisko() + '\'' +
                ", imie='" + getImie() + '\'' +
                ", pesel=" + getPesel() +
                ", pensja=" + getPensja() +
                ", premia=" + premia +
                '}';
    }
}
