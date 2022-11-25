package Lista_4.etap2;

public class Stroiciel {
    public static void nastrajanie(instrumentKlawiszowy instrument) {
        if (instrument.getCzyNastrojony()){
            System.out.println("Instrument jest sprawny!");
        } else {
            instrument.setCzyNastrojony(true);
            System.out.println("Instrument został nastrojony!");
        }
    }
}
