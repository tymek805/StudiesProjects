package Lista_4.etap2;

public class Main {
    public static void wypiszWszystko(instrumentKlawiszowy[] instrumenty){
        for (instrumentKlawiszowy item: instrumenty) {
            System.out.println("\n--------------------------------------------------");
            item.getStan();
            item.zagraj();
            System.out.println("--------------------------------------------------\n");
        }
    }
    public static void main(String[] args){
        instrumentKlawiszowy[] instrumenty = new instrumentKlawiszowy[5];
        instrumenty[0] = new Pianiono(); // Domyślne Pianiono
        instrumenty[1] = new Fortepian(); // Domyślny Fortepian
        instrumenty[2] = new Fortepian("Casio", "drewniany", false, 17920, false);
        instrumenty[3] = new Pianiono("Roland", "Antracyt", false, 3499, "hybrydowy");
        instrumenty[4] = new Fortepian("Kawai", "Biały", true, 22845, true);

        wypiszWszystko(instrumenty);

        // Strojenie wszystkich fortepianów
        // Dla chopinowskich kupienie ław
        // Strojenie pianin tradycyjnych oraz po strojeniu zwiększenie ceny o 500
        for (instrumentKlawiszowy item: instrumenty) {
            if (item instanceof Fortepian){
                if(!item.getCzyNastrojony()){
                    System.out.print("Fortepian -> ");
                    item.zarezerwowanieStrojenia();
                }
                if (((Fortepian) item).getCzyChopinowski()){
                    item.zakupLawy("Skóra", true, "Czarny");
                }
            } else if (item instanceof Pianiono && ((Pianiono) item).getTypBudowy().equals("tradycyjny")) {
                System.out.print("Pianino -> ");
                item.zarezerwowanieStrojenia();
                ((Pianiono) item).setCena(((Pianiono) item).getCena() + 500);
            }
        }
        // Dla danego fortepianu i danego pianina wykonanie odpowiednich funkcji
        for (instrumentKlawiszowy item: instrumenty) {
            if (item instanceof Fortepian && item.getMarka().equals("Casio")){
                System.out.print("ID:" + ((Fortepian) item).getNumerSeryjny() + " -> ");
                ((Fortepian) item).uchylPokrywe();
            } else if (item instanceof Pianiono && item.getCzyNastrojony()) {
                System.out.print(((Pianiono) item).getTypBudowy() + " -> ");
                ((Pianiono) item).wycisz();
            }
        }
        wypiszWszystko(instrumenty);
    }
}
