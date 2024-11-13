package PSiO.Lista_2;

public class zad_2 {
    public static void main(String[] args) {
        int x = 2;
        int k = 1023;
        double num = 1;
        double ans = 1;

        if (k % 2 == 1) {
            k = k - 1;
            ans = x;
        }
        for (int i = 1; i <= k / 2; i++) {
            num *= x;
        }
        num *= num;
        ans = num * ans;
        System.out.print(ans);
    }
}
