//------------------ Analizator dla formul rachunku zdan ---------------------
// Zastosowano analize skladniowa typu zejscia rekurencyjnego (rekursywnego)
//import java.util.*;
import java.util.Set;
import java.util.HashSet;

public class Z4e {
  enum symbol {ZADEN, ZMIENNA_ZDANIOWA, FALSUM, VERUM, NEGACJA, KONIUNKCJA, ALTERNATYWA, IMPLIKACJA, 
               ROWNOWAZNOSC, L_NAWIAS, P_NAWIAS, KONIEC};
  private static char  ch;             // ostatni przeczytany znak 
  private static int licznikZn;        // licznik znakow
  private static String formula;       // lancuch znakow zawierajacy analizowana formule
  private static symbol sym;           // ostatni przeczytany symbol
  private static String nazwa;         // nazwa ostatniej przeczytanej zmiennej zdaniowej
  private static Set<String> zbiorSpojnikow;   // zbior nazw spojnikowm wystepujacych w formule
  private static symbol[] strans = new symbol[256];  // tablica, sluzaca do zamiany znakow na symbole	
 

  
  private static void blad(int b) { //------------------------------------------------- blad
    String[] error = { "^ nieoczekiwany koniec formuly",                   // 0
                       "^ oczekiwana formula atomowa lub '(' ",            // 1					   
                       "^ oczekiwany ')' ",                                // 2
                       "^ oczekiwany spojnik dwuargumentowy",              // 3
                     };
    System.out.println(formula); 
	if (b <= error.length) System.out.printf("%" + (licznikZn + error[b].length()) +"s\n", error[b]);
	else System.out.println("blad w programie analizatora"); 	
	System.exit(0);            // koniec dzialania analizatora
  }

  // ---------------- funkcje analizatora leksykalnego (skanera)  
  private static void pobZnak() { //---------------------------------------------- pob_znak
   // sprawdza koniec, umieszcza nastepny znak w ch, zwieksza licznikZn 
    if (licznikZn < formula.length()) {ch = formula.charAt(licznikZn); licznikZn++;} 
	else ch = 0;
  } 

  private static void zmiennaZdaniowa() { //---------------------------------- zmiennaZdaniowa
    sym = symbol.ZMIENNA_ZDANIOWA;
    nazwa = String.valueOf(ch);   // konwersja char do String
	pobZnak();
  }

  private static void pobSymbol() { //----------------------------------------- pobSymbol
  // pomija wszystkie biale znaki z wyjatkiem '\n'
    while (ch != '\n' && Character.isWhitespace(ch)) pobZnak();
    if (Character.isLowerCase(ch))
      zmiennaZdaniowa();    
    else { sym = strans[ch]; pobZnak(); }	
  }  

  // ---------------- funkcje analizatora skladniowego (parsera)
  private static Set<String> formula() { //---------------------------------- formula
    Set<String> spojniki = new HashSet<String>();  
	
	switch (sym) {
       case ZMIENNA_ZDANIOWA:
       case FALSUM   :  
       case VERUM    :	
           spojniki = formulaAtomowa(); break;
       case L_NAWIAS     : 
           pobSymbol(); 
           if (sym == symbol.NEGACJA) {	
              spojniki.add("N"); pobSymbol();
              spojniki.addAll(formula());
           }			  
           else {
              spojniki = formula();
              switch (sym) {
                 case KONIUNKCJA  : spojniki.add("K"); pobSymbol(); spojniki.addAll(formula()); break;
                 case ALTERNATYWA : spojniki.add("A"); pobSymbol(); spojniki.addAll(formula()); break;
                 case IMPLIKACJA  : spojniki.add("C"); pobSymbol(); spojniki.addAll(formula()); break;
                 case ROWNOWAZNOSC:	spojniki.add("E"); pobSymbol(); spojniki.addAll(formula()); break;			 
                 default: blad(3); 
			  };				 
           };
           if (sym == symbol.P_NAWIAS) pobSymbol();
           else blad(2);
           break;		   
       default :
           blad (1); 
    }
	return spojniki;
  }	
  
  private static Set<String> formulaAtomowa() { //---------------------------------- formula
    Set<String> spojniki = new HashSet<String>();  
	switch (sym) {
       case ZMIENNA_ZDANIOWA : 	pobSymbol(); break;
       case FALSUM           : 	spojniki.add("F"); pobSymbol(); break;   
       case VERUM            : 	spojniki.add("V"); pobSymbol(); break; 	 	
    }	
	return spojniki;
  }	  

// inicjacja zmiennych 
  public static void main(String[] args) {
    if (args.length == 1) { 
	  formula = args[0]; 
      if (formula.length() == 0) blad(0);   // przypadek "" 
      licznikZn = 0; ch = ' ';	
      // for (int i=0; i<256; i++) strans[i] = symbol.ZADEN;
	  for (symbol name: strans) name = symbol.ZADEN;
      strans['F'] = symbol.FALSUM;       strans['V'] = symbol.VERUM;
      strans['N'] = symbol.NEGACJA;      strans['K'] = symbol.KONIUNKCJA;
      strans['A'] = symbol.ALTERNATYWA;  strans['C'] = symbol.IMPLIKACJA;	  
      strans['E'] = symbol.ROWNOWAZNOSC; strans['('] = symbol.L_NAWIAS;       
	  strans[')'] = symbol.P_NAWIAS;     strans[0]   = symbol.KONIEC;	  
	  pobSymbol(); 
	  if (sym == symbol.KONIEC) blad(0);
	  else {
	    zbiorSpojnikow = formula();
        if (sym != symbol.KONIEC) blad(3);
	    else System.out.println("W formule \"" + formula + "\" wystepuja spojniki " + zbiorSpojnikow);
      }		
	}  
    else 
	  System.out.println("Wywolanie: Z4e \"<formula>\""); 
  }  // koniec main
}    // koniec klasy