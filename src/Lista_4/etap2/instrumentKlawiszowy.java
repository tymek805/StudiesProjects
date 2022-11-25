package Lista_4.etap2;

public abstract class instrumentKlawiszowy {
    private String marka;
    private String kolor;
    private boolean czyNastrojony;
    private Lawa lawa = null;

    instrumentKlawiszowy(){
        this.marka = "Yamaha";
        this.kolor = "Czarny";
        this.czyNastrojony = false;
    }
    // Konstruktor przeciążony
    public instrumentKlawiszowy(String marka, String kolor, boolean czyNastrojony){
        this.marka = marka;
        this.kolor = kolor;
        this.czyNastrojony = czyNastrojony;
    }

    // Gettery
    public String getMarka(){return marka;}
    public String getKolor(){return  kolor;}
    public boolean getCzyNastrojony(){return czyNastrojony;}
    public Lawa getLawa(){return lawa;}

    // Settery
    public void setMarka(String marka){
        this.marka = marka;
    }
    public void setKolor(String kolor){
        this.kolor = kolor;
    }
    public void setCzyNastrojony(boolean czyNastrojony){
        this.czyNastrojony = czyNastrojony;
    }

    //Metody
    protected void zagraj(){
        System.out.print("Gram ");
    }

    public void getStan() {
        System.out.println("Marka: " + marka);
        System.out.println("Kolor: " + kolor);
        if (czyNastrojony){
            System.out.println("Nastrojone!");
        } else{
            System.out.println("Nie nastrojone...");
        }
    }

    public void zarezerwowanieStrojenia(){
        Stroiciel.nastrajanie(this);
    }
    public void zakupLawy(String material, boolean czyRegulowana, String kolor){
        lawa = new Lawa(material, czyRegulowana, kolor);
    }
}