public class Solution {
    public Substring shortestNaturalSubstring(int[] numbers){
        Substring substring = new Substring();
        if (numbers == null) return substring;

        int nextNatural = 0;
        for (int i = 0; i < numbers.length; i++){
            if (numbers[i] == nextNatural){
                if (i == numbers.length - 1){
                    if (substring.getLength() > nextNatural + 1) {
                        substring.setLength(nextNatural + 1);
                        substring.setPos(i - nextNatural);
                    }
                }
                nextNatural++;
            } else if (nextNatural > 0){
                if (substring.getPos() == -1) {
                    substring.setLength(nextNatural);
                    substring.setPos(i - nextNatural);
                }
                else if (substring.getLength() > nextNatural + 1) {
                    substring.setLength(nextNatural);
                    substring.setPos(i - nextNatural);
                }
                nextNatural = 0;
            }
        }
        return substring;
    }

    public static void main(String[] args) {
        Substring ans_1 = new Solution().shortestNaturalSubstring(new int[]{1, 2, 8, -3, -3, -2, 0, 1, 2, 7, 10, 0, 1});
        System.out.println(ans_1.getLength() + " " + ans_1.getPos());

        Substring ans_2 = new Solution().shortestNaturalSubstring(new int[]{1, 2, 8, -3, -3, -2, 0, 1, 2, 7, 10, 0});
        System.out.println(ans_2.getLength() + " " + ans_2.getPos());

        Substring ans_3 = new Solution().shortestNaturalSubstring(new int[]{0, 1, 4, 8, -3, -3, -2, 0, 1, 2, 7, 10, 0, 1});
        System.out.println(ans_3.getLength() + " " + ans_3.getPos());

        Substring ans_4 = new Solution().shortestNaturalSubstring(null);
        System.out.println(ans_4.getLength() + " " + ans_4.getPos());
    }
}
