public class zad_2 {
    public int[][] longestNaturalSubstrings(int[] numbers){
        int[][] ans = new int[numbers.length][];
        int nextNatural = 0;
        int num_of_arrays = 0;
        for (int i = 0; i < numbers.length; i++){
            if (numbers[i] == nextNatural){
                if (i == numbers.length - 1) ans[num_of_arrays] = fillTable(nextNatural + 1);
                nextNatural++;
            } else if (nextNatural > 0){
                ans[num_of_arrays] = fillTable(nextNatural);
                nextNatural = 0;
                num_of_arrays++;
            }
        }

        return ans;
    }
    public int[] fillTable(int border){
        int[] table = new int[border];
        for (int i = 0; i < border; i++){
            table[i] = i;
        }
        return table;
    }

    public static void main(String[] args) {
//        int[][] ans = new zad_2().longestNaturalSubstrings(new int[]{1, 2, 8, -3, -3, -2, 0, 1, 2, 7, 10, 0, 1});
        int[][] ans = new zad_2().longestNaturalSubstrings(new int[]{1, 2, 0, 1, 2, 3, -2, 0, 1, 2, 7, 10, 0, 1});
        for (int i = 0; i < ans.length; i++){
            if (ans[i] != null) {
                System.out.print("{ ");
                for (int j = 0; j < ans[i].length; j++) {
                    System.out.print(ans[i][j] + " ");
                }
                System.out.print("}");
            }
        }
    }
}
