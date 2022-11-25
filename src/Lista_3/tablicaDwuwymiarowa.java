package Lista_3;

import java.util.Random;

public class tablicaDwuwymiarowa {
    private final int n, k;
    private float[][] obj_1, obj_2;
    public tablicaDwuwymiarowa(int n, int k){
        this.n = n;
        this.k = k;
        this.obj_1 = new float[n][n];
        this.obj_2 = new float[n][n];
        setMatrix(obj_1);
        setMatrix(obj_2);
    }
    private float[][] setMatrix(float[][] obj){
        Random rand = new Random();
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                obj[i][j] = rand.nextFloat(k);
            }
        }
        return obj;
    }

    private void getMatrix(float[][] obj){
        String output = "";
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                if (j < n-1) output += obj[i][j] + " ";
                else output += obj[i][j] + "\n";
            }
        }
        System.out.println(output);
    }

    public void getInitial() {
        getMatrix(obj_1);
        getMatrix(obj_2);
    }

    public void addition(){
        float[][] obj = new float[n][n];
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                obj[i][j] = obj_1[i][j] + obj_2[i][j];
            }
        }
        getMatrix(obj);
    }

    public void multiplication() {
        float[][] obj = new float[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                float sum = 0;
                for (int q = 0; q < n; q++) {
                    sum += obj_1[i][q] * obj_2[q][j];
                }
                obj[i][j] = sum;
            }
        }
        getMatrix(obj);
    }

    public static void main(String[] args){
        tablicaDwuwymiarowa obj = new tablicaDwuwymiarowa(10, 5);
        obj.getInitial();
        obj.addition();
        obj.multiplication();
    }
}