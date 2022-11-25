// Tymoteusz Lango
// IST 1 stopień
// grupa 2

import java.lang.String;
import java.util.Scanner;

public class konwerter {
    public static int Romanian_to_arabic(String num){
        char[] chars = num.toCharArray();
        char[] all_numbers = {'I', 'V', 'X', 'L', 'C', 'D', 'M'};
        int[] dec_numbers = {1, 5, 10, 50, 100, 500, 1000};

        int ans = 0;
        int last_idx = -1;
        for (int i = 0; i < num.length(); i++){
            boolean isAddition = true;
            int current_idx = -1;
            int next_idx = -1;
            for (int j = 0; j < 7; j++){
                if (chars[i] == all_numbers[j]) current_idx = j;
                if (i < num.length()-1 && chars[i + 1] == all_numbers[j]) next_idx = j;
            }
            if (next_idx < current_idx || current_idx == last_idx) isAddition = true;
            else if (next_idx > current_idx) isAddition = false;

            if (isAddition) ans += dec_numbers[current_idx];
            else ans -= dec_numbers[current_idx];
            last_idx = current_idx;
        }
        return ans;
    }
    public static String Arabic_to_romanian(int num){
        String[] all_numbers = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] dec_numbers = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String ans = "";
        while (num != 0){
            for (int i = 0; i < dec_numbers.length; i++){
                while (dec_numbers[i] <= num){
                    ans += all_numbers[i];
                    num -= dec_numbers[i];
                }
            }
        }
        return ans;
    }
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Napisz R jeśli chcesz zamienić liczbę rzymską lub A jeśli arabską: ");
        String test = input.nextLine();
        test = test.toUpperCase();
        if (test.equals("R")){
            System.out.print("Wprowadź cyfrę rzymską: ");
            String num = input.nextLine();
            System.out.println(Romanian_to_arabic(num));
        }
        else if (test.equals("A")){
            System.out.print("Wprowadź cyfrę arabską: ");
            int num = input.nextInt();
            System.out.println(Arabic_to_romanian(num));
        } else System.out.println("Błędnie wprowadzona wartość!");
        input.close();
    }
}