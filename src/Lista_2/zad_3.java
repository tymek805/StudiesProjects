package Lista_2;

public class zad_3 {
    public static double E_x_tl(double x, int n){
        double sum = 1;
        double tmp = 1;
        int k = 1;
        while (k < n){
            tmp = tmp * x / k;
            sum += tmp;
            k++;
        }
        return sum;
    }

    public static double Sin_x_tl(double x, int n){
        double sum = x;
        double tmp = x;
        int k = 1;
        while (k < n){
            tmp = tmp * (-1) * ((x*x)/((2*k)*(2*k+1)));
            sum += tmp;
            k++;
        }
        return sum;
    }

    public static double Cos_x_tl(double x, int n){
        double sum = 1;
        double tmp = 1;
        int k = 1;
        while (k < n){
            tmp = tmp * -1 * x * x / ((2*k)*(2*k-1));
            sum += tmp;
            k++;
        }
        return sum;
    }

    public static void main(String[] args) {
            double x = 2;
            int n = 300;
            System.out.println("E: " + E_x_tl(x, n));
            System.out.println("Sin: " + Sin_x_tl(x, n));
            System.out.println("Cos: " + Cos_x_tl(x, n));
    }
}
