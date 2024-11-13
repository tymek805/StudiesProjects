package PSiO.Lista_3;

import java.util.Random;

public class tablicaJednowymiarowa {
    private final int n, k;
    private final int[] tab;
    private int evenNum, oddNum;

    public tablicaJednowymiarowa(int n, int k) {
        this.n = n;
        this.k = k;
        this.tab = new int[n];
    }

    public void Wypelnij() {
        Random rand = new Random();

        for (int i = 0; i < n; i++) {
            tab[i] = rand.nextInt(k) + 1;
        }
    }

    public void getTablica() {
        StringBuilder output = new StringBuilder("{ ");
        for (int i = 0; i < n; i++) {
            if (i < n - 1) output.append(tab[i]).append(", ");
            else output.append(tab[i]).append(" }");
        }
        System.out.println(output);
    }

    public void getTablicaOdwrotna() {
        StringBuilder output = new StringBuilder("{ ");
        for (int i = n - 1; i >= 0; i--) {
            if (i > 0) output.append(tab[i]).append(", ");
            else output.append(tab[i]).append(" }");
        }
        System.out.println(output);
    }

    public void count_Even_Odd() {
        evenNum = 0;
        oddNum = 0;
        for (int i = 0; i < n; i++) {
            if (tab[i] % 2 == 0) evenNum++;
            else oddNum++;
        }
    }

    public void make_Even_Odd() {
        count_Even_Odd();
        int[] tab_even = new int[evenNum];
        int[] tab_odd = new int[oddNum];
        int e = 0, o = 0;
        for (int i = 0; i < n; i++) {
            if (tab[i] % 2 == 0) {
                tab_even[e] = tab[i];
                e++;
            } else {
                tab_odd[o] = tab[i];
                o++;
            }
        }
        printTable(tab_even);
        printTable(tab_odd);
    }

    public void searchMax() {
        int max = 0;
        for (int i = 0; i < n; i++) {
            if (max < tab[i]) max = tab[i];
        }
        System.out.println(max);
    }

    public void searchMin() {
        int min = -1;
        for (int i = 0; i < n; i++) {
            if (i == 0) min = tab[i];
            if (min > tab[i]) min = tab[i];
        }
        System.out.println(min);
    }

    private void printTable(int[] t) {
        StringBuilder output = new StringBuilder("{ ");
        for (int i = 0; i < t.length; i++) {
            if (i < t.length - 1) output.append(t[i]).append(", ");
            else output.append(t[i]);
        }
        System.out.println(output + " }");
    }

    public static void main(String[] args) {
        tablicaJednowymiarowa tab = new tablicaJednowymiarowa(10, 100);
        tab.Wypelnij();
        tab.getTablica();
        tab.getTablicaOdwrotna();
        tab.make_Even_Odd();
        tab.searchMax();
        tab.searchMin();
    }
}
