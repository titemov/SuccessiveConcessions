import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.Random;

public class Backend extends Interface {
    public static int rng(int low, int high) {
        Random r = new Random();
        return r.nextInt(high - low) + low;
    }

    public static double[][] arrayOptimization(double[][] array, boolean fromRestrict){
        System.out.println("_______________");
        double[][] result;
        int minRowIndex=999;
        int minColumnIndex=999;
        int maxRowIndex=-1;
        int maxColumnIndex=-1;
        for (int i=0;i<array.length;i++){
            for (int n=0;n<array[0].length;n++){
                if(array[i][n]!=0){
                    if (minRowIndex>i) minRowIndex=i;
                    if (minColumnIndex>n) minColumnIndex=n;
                    if (maxRowIndex<i) maxRowIndex=i;
                    if (maxColumnIndex<n) maxColumnIndex=n;
                }
                //System.out.println(minRowIndex+" "+minColumnIndex+" "+maxRowIndex+" "+maxColumnIndex);
            }
        }
        if (maxRowIndex==-1 || maxColumnIndex==-1) return null;
        if (minRowIndex==999 || minColumnIndex==999) return null;

        if (fromRestrict && minColumnIndex<5){
            maxColumnIndex=array[0].length-1;
        } else if (fromRestrict) {
            return null;
        }

        result = new double[maxRowIndex-minRowIndex+1][maxColumnIndex-minColumnIndex+1];
        for (int i=0;i<(maxRowIndex-minRowIndex+1);i++){
            for(int n=0;n<(maxColumnIndex-minColumnIndex+1);n++){
                result[i][n]=array[minRowIndex+i][minColumnIndex+n];
            }
        }
        //Matrix.printmat(result);
        return result;
    }

}