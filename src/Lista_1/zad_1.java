package Lista_1;

import java.util.Scanner;
import static java.lang.Math.sqrt;

public class zad_1 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Wprowadź a: ");
        int a = input.nextInt();
        while (a == 0){
            System.out.print("a jest równe 0, więc równanie nie jest kwadratowe! Wprowadź poprawne a: ");
            a = input.nextInt();
        }
        System.out.print("Wprowadź b: ");
        int b = input.nextInt();
        System.out.print("Wprowadź c: ");
        int c = input.nextInt();
        input.close();

        float delta = b * b - 4 * a * c;
        if (delta > 0){
            float x_1 = (float) ((-b - sqrt(delta)) / 2 * a);
            float x_2 = (float) ((-b + sqrt(delta)) / 2 * a);

            System.out.printf("Istnieją dwa pierwiastki: %f oraz %f", x_1, x_2);
        }
        else if(delta == 0){
            float x_0 = (float) ((-b) / (2 * a));
            System.out.printf("Istnieje jeden pierwiastek: %f", x_0);
        } else {
            System.out.println("Nie ma pierwiastków");
        }
    }
}
