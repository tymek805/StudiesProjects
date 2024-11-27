public class Pracownik {
    private String nazwisko;
    private String imie;
    private String pesel;
    private int pensja;

    public Pracownik(String nazwisko, String imie, String pesel, int pensja) {
        this.nazwisko = nazwisko;
        this.imie = imie;
        this.pesel = pesel;
        this.pensja = pensja;
    }


    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public int getPensja() {
        return pensja;
    }

    public void setPensja(int pensja) {
        this.pensja = pensja;
    }

    public void percentRaise(float percent){
        pensja = (int) (pensja * percent / 100);
    }

    @Override
    public String toString() {
        return "Pracownik{" +
                "nazwisko='" + nazwisko + '\'' +
                ", imie='" + imie + '\'' +
                ", pesel=" + pesel +
                ", pensja=" + pensja +
                '}';
    }
}
