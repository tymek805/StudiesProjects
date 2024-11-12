package PSiO.Lista_4.etap2;

import java.util.Hashtable;

public class Pianiono extends instrumentKlawiszowy {
    private int cena;
    private String typBudowy;
    private boolean czyWyciszony = false;
    private final Hashtable<Boolean, String> wyciszonyDict = new Hashtable<>();

    Pianiono() {
        super();
        this.cena = 5500;
        this.typBudowy = "tradycyjny";
        wyciszonyDict.put(true, "cicho");
        wyciszonyDict.put(false, "normalnie");
    }

    public Pianiono(String marka, String kolor, boolean czyNastrojony, int cena, String typBudowy) {
        super(marka, kolor, czyNastrojony);
        this.cena = cena;
        this.typBudowy = typBudowy;
        wyciszonyDict.put(true, "cicho");
        wyciszonyDict.put(false, "normalnie");
    }

    // Gettery
    public int getCena() {
        return cena;
    }

    public String getTypBudowy() {
        return typBudowy;
    }

    public boolean getCzyWyciszony() {
        return czyWyciszony;
    }

    // Settery
    public void setCena(int cena) {
        this.cena = cena;
    }

    public void setTypBudowy(String typBudowy) {
        this.typBudowy = typBudowy;
    }

    // Metody
    public void wycisz() {
        czyWyciszony = true;
        zagraj();
    }

    public void zagraj() {
        System.out.println("Gram " + wyciszonyDict.get(czyWyciszony) + "...");
    }

    public void getStan() {
        super.getStan();
        System.out.println("Cena: " + cena + " zł");
        System.out.println("Typ budowy pianina: " + typBudowy);
        System.out.println("Jak gra? " + wyciszonyDict.get(czyWyciszony));
    }
}
