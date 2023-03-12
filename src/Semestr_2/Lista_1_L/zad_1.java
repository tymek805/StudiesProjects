public class zad_1 {
    public void drawHourglass(int n){
        int m = 2 * n - 1;

        for (int i = 0; i < m; i++){
            for (int j = 0; j < m; j++){
                if (i == 0 || i == m - 1) System.out.print("X");
                else if (j - i == 0 || j + i == m - 1) System.out.print("Y");
                else if (j - i > 1 && j + i < m - 2) System.out.print("O");
                else if (i > (m - 1) / 2 && j + i > m && j - i < -1) System.out.print("O");
                else System.out.print(" ");
            }
            System.out.println();
        }
    }

    public void drawBridge(int n){
        int m = 2 * n - 1;

        for (int i = 0; i < n; i++){
            for (int j = 0; j < m; j++){
                if (i == n - 2 || n < 2) System.out.print("X");
                else if (j == 0 || j == m - 1) System.out.print("I");
                else if (i > 0 && i < n - 2){
                    if (i == j) System.out.print("\\");
                    else if (j == m - 1 - i) System.out.print("/");
                    else System.out.print(" ");
                }
                else System.out.print(" ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        new zad_1().drawHourglass(-5);
        new zad_1().drawBridge(-5);
    }
}
