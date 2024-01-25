//------------------ Kalkulator dla liczb calkowitych ---------------------
// Zastosowano analize skladniowa typu zejscia rekurencyjnego (rekursywnego)

public class calculator {
    enum symbol {ZADEN, LICZBA, PLUS, MINUS, MNOZ, DZIEL, L_NAWIAS, P_NAWIAS, KONIEC};
    private static char  ch;          // ostatni przeczytany znak
    private static int licznikZn;     // licznik znakow
    private static String wyr;        // lancuch znakow zawierajacy interpretowane wyrazenie
    private static symbol sym;        // ostatni przeczytany symbol
    private static int wartLiczby;    // wartosc ostatniej przeczytanej liczby
    private static int wartWyrazenia; // wartosc wyrazenia
    private static symbol[] strans = new symbol[256];  // tablica, sluzaca do zamiany znakow na symbole



    private static void blad(int b) { //------------------------------------------------- blad
        String[] error = { "^ nieoczekiwany koniec wyrazenia",                 // 0
                "^ oczekiwana cyfra lub '(' ",                       // 1
                "^ oczekiwany ')' ",                                 // 2
                "^ oczekiwany znak operacji lub koniec wyrazenia",  // 3
                "^ dzielenie przez zero"                            // 4
        };
        System.out.println(wyr);
        if (b <= error.length) System.out.printf("%" + (licznikZn + error[b].length()) +"s\n", error[b]);
        else System.out.println("blad w programie interpretera");
        System.exit(0);            // koniec dzialania interpretera
    }

    // ---------------- funkcje analizatora leksykalnego (skanera)
    private static void pobZnak() { //---------------------------------------------- pob_znak
        // sprawdza koniec, umieszcza nastepny znak w ch, zwieksza licznikZn
        if (licznikZn < wyr.length()) {ch = wyr.charAt(licznikZn); licznikZn++;}
        else ch = 0;
    }

    private static void liczbaNaturalna() { //---------------------------------- liczba_naturalna
        sym = symbol.LICZBA;
        wartLiczby = ch - '0'; pobZnak();
        while (Character.isDigit(ch))
        { wartLiczby = wartLiczby * 10 + ch - '0'; pobZnak(); };
    }

    private static void pobSymbol() { //----------------------------------------- pob_symbol
        // pomija wszystkie biale znaki z wyjatkiem '\n'
        while (ch != '\n' && Character.isWhitespace(ch)) pobZnak();
        if (Character.isDigit(ch))
            liczbaNaturalna();    // skaner sam oblicza wartosc literalow numerycznych
        else { sym = strans[ch]; pobZnak(); }
    }

    // ---------------- funkcje analizatora skladniowego (parsera)
    private static int wyrazenie() { //---------------------------------- wyrazenie
        int wyr, skl = 0;
        symbol oper;

        if (sym == symbol.PLUS || sym == symbol.MINUS) {
            oper = sym; pobSymbol(); wyr = skladnik();
            if (oper == symbol.MINUS) wyr = -wyr;
        }
        else wyr = skladnik();
        while (sym == symbol.PLUS || sym == symbol.MINUS) {
            oper = sym; pobSymbol();
            skl = skladnik();
            switch (oper) {
                case PLUS : wyr = wyr + skl; break;
                case MINUS: wyr = wyr - skl; break;
            }
        }
        return wyr;
    }

    private static int skladnik () { //----------------------------------- skladnik
        int skl, cz = 0;
        symbol oper;

        skl = czynnik();
        while (sym == symbol.MNOZ || sym == symbol.DZIEL) {
            oper = sym; pobSymbol();
            cz = czynnik();
            switch (oper) {
                case MNOZ : skl = skl * cz; break;
                case DZIEL: if (cz != 0) skl = skl / cz;
                else blad(4); break;
            }
        }
        return skl;
    }

    private static int czynnik () { //---------------------------------------- czynnik
        int cz = 0;

        switch (sym) {
            case LICZBA: cz = wartLiczby; pobSymbol(); break;
            case L_NAWIAS: pobSymbol(); cz = wyrazenie();
                if (sym == symbol.P_NAWIAS) pobSymbol();
                else blad(2);
                break;
            default    : blad(1);
        }
        return cz;
    }

    // inicjacja zmiennych
    public static void main(String[] args) {
        if (args.length == 1) {
            wyr = args[0];
            if (wyr.length() == 0) blad(0);   // przypadek ""
            licznikZn = 0; ch = ' ';
            // for (int i=0; i<256; i++) strans[i] = symbol.ZADEN;
            for (symbol name: strans) name = symbol.ZADEN;
            strans['+']  = symbol.PLUS;       strans['-'] = symbol.MINUS;
            strans['*']  = symbol.MNOZ;       strans['/'] = symbol.DZIEL;
            strans['(']  = symbol.L_NAWIAS;   strans[')'] = symbol.P_NAWIAS;
            strans[0] = symbol.KONIEC;
            pobSymbol();
            if (sym == symbol.KONIEC) blad(0);
            else {
                wartWyrazenia = wyrazenie();
                if (sym != symbol.KONIEC) blad(3);
                else System.out.println(wyr + " = " + wartWyrazenia);
            }
        }
        else
            System.out.println("Wywolanie: Kalk \"<wyrazenie>\"");
    }  // koniec main
}    // koniec Kalk