package Lista_2;

public class zad_4 {
    public static double factorial(int x){
        long sum = 1;
        for (int i = 2; i <=x; i++){
            sum *= i;
        }
        return sum;
    }

    public static double power(double x, int k) {
        double num = 1;
        double ans = 1;

        if (k % 2 == 1){
            k = k - 1;
            ans = x;
        }
        for (int i = 1; i <= k/2; i++){
            num *= x;
        }
        num *= num;
        ans = num * ans;
        return ans;
    }

    public static double E_x_tl(double x){
        double sum = 0;
        double last_factorial = 0;
        boolean isApproximated = false;
        int i = 0;
        while (!isApproximated){
            double fact = factorial(i);
            if (fact >= last_factorial){
                sum += power(x, i) / fact;
                last_factorial = fact;
                i++;
            }
            else {
                isApproximated = true;
            }
        }
        return sum;
    }
    public static double Sin_x_tl(double x){
        double sum = 0;
        double last_factorial = 0;
        boolean isApproximated = false;
        int i = 0;
        while (!isApproximated){
            double fact = factorial(2 * i + 1);
            if (fact >= last_factorial){
                sum += power(x, 2 * i + 1) / fact * power(-1, i);
                last_factorial = fact;
                i++;
            }
            else{
                isApproximated = true;
            }
        }
        return sum;
    }
    public static double Cos_x_tl(double x){
        double sum = 0;
        double last_factorial = 0;
        boolean isApproximated = false;
        int i = 0;
        while (!isApproximated){
            double fact = factorial(2 * i);
            if (fact >= last_factorial){
                sum += power(x, 2 * i) / fact * power(-1, i);
                last_factorial = fact;
                i++;
            }
            else {
                isApproximated = true;
            }
        }
        return sum;
    }
    public static void main(String[] args) {
            double x = 2;
            System.out.println("E: " + E_x_tl(x));
            System.out.println("Sin: " + Sin_x_tl(x));
            System.out.println  ("Cos: " + Cos_x_tl(x));
    }
}
