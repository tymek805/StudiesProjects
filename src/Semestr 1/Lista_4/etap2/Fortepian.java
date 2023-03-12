package Lista_4.etap2;

import java.util.Hashtable;

public class Fortepian extends instrumentKlawiszowy{
    private int numerSeryjny;
    private boolean czyChopinowski = false;
    private String stanPokrywy = "zamknięta";
    private final Hashtable<Boolean, String> chopinowskiDict = new Hashtable<>();

    Fortepian(){
        super();
        this.numerSeryjny = 1;
        chopinowskiDict.put(true, "Fortepian jest chopinowski!");
        chopinowskiDict.put(false, "Fortepian NIE jest chopinowski!");
    }

    public Fortepian(String marka, String kolor, boolean czyNastrojony, int numerSeryjny, boolean czyChopinowski){
        super(marka, kolor, czyNastrojony);
        this.numerSeryjny = numerSeryjny;
        this.czyChopinowski = czyChopinowski;
        chopinowskiDict.put(true, "Fortepian jest chopinowski!");
        chopinowskiDict.put(false, "Fortepian NIE jest chopinowski!");
    }

    // Gettery
    public int getNumerSeryjny(){return numerSeryjny;}
    public boolean getCzyChopinowski(){return czyChopinowski;}

    // Settery
    public void setNumerSeryjny(int numerSeryjny){this.numerSeryjny = numerSeryjny;}
    public void setCzyChopinowski(boolean czyChopinowski){this.czyChopinowski = czyChopinowski;}

    // Metody
    public void getStan(){
        super.getStan();
        System.out.println("Numer seryjny: " + numerSeryjny);
        System.out.println("Stan pokrywy: " + stanPokrywy);
        System.out.println(chopinowskiDict.get(czyChopinowski));
    }

    public void uchylPokrywe(){
        stanPokrywy = "uchylona";
        zagraj();
    }

    public void zagraj(){
        System.out.println("Gram z pokrywą " + stanPokrywy + "...");
    }
}