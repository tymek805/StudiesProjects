package PSiO.Lista_1;

import static java.lang.Math.sqrt;

public class zad_1 {
    public static void main(String[] args) {
        int a = 1;
        int b = 1;
        int c = -2;
        if (a != 0) {
            float delta = b * b - 4 * a * c;
            if (delta > 0) {
                float x_1 = (float) ((-b - sqrt(delta)) / 2 * a);
                float x_2 = (float) ((-b + sqrt(delta)) / 2 * a);

                System.out.printf("Istnieją dwa pierwiastki: %f oraz %f", x_1, x_2);
            } else if (delta == 0) {
                float x_0 = (float) ((-b) / (2 * a));
                System.out.printf("Istnieje jeden pierwiastek: %f", x_0);
            } else {
                System.out.println("Nie ma pierwiastków");
            }
        } else {
            System.out.println("Jest to równanie liniowe!");
        }
    }
}
