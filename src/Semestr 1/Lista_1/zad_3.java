package Lista_1;

public class zad_3 {
    public static void main(String[] args) {
        int n = 13;
        int m = 25;
        System.out.println("NWD to: " + NWD(n, m));
    }

    private static int NWD(int n, int m) {
        if (m == 0){
            return n;
        } else {
            return NWD(m, n%m);
        }
    }
}