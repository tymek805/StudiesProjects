package PSiO.Lista_4.etap2;

public class Lawa {
    private boolean czyRegulowana;
    private String kolor, material;

    public Lawa(String material, boolean czyRegulowana, String kolor)
    {
        this.material = material;
        this.czyRegulowana = czyRegulowana;
        this.kolor = kolor;
    }

    // Gettery
    public String getMaterial() {return material;}
    public boolean getCzyRegulowana() {return czyRegulowana;}
    public String getKolor() {return kolor;}

    // Settery
    public void setMaterial(String material) {this.material = material;}
    public void setCzyRegulowana(boolean czyRegulowana) {this.czyRegulowana = czyRegulowana;}
    public void setKolor(String kolor) {this.kolor = kolor;}

    public void getStan() {
        System.out.println("Instrument posiada ławę!");
        System.out.println("Ława: \n" +
                "Materiał: " + material +
                "\nCzy jest regulowana: " + czyRegulowana +
                "\nKolor: " + kolor);
    }
}
