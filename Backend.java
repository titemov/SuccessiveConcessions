import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.Arrays;
import java.util.Random;

public class Backend extends Interface {
    public static int rng(int low, int high) {
        Random r = new Random();
        return r.nextInt(high - low) + low;
    }

    public static double[][] arrayOptimization(double[][] array, boolean fromRestrict){
        //System.out.println("_______________");
        double[][] result;
        int minRowIndex=999;
        int minColumnIndex=999;
        int maxRowIndex=-1;
        int maxColumnIndex=-1;
        for (int i=0;i<array.length;i++){
            for (int n=0;n<array[0].length;n++){
                if(array[i][n]!=0){
                    if (minRowIndex > i) minRowIndex = i;
                    if (minColumnIndex > n) minColumnIndex = n;
                    if (maxRowIndex < i) maxRowIndex = i;
                    if (maxColumnIndex < n) maxColumnIndex = n;
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

        if (fromRestrict) {
            minColumnIndex = 0;
            maxColumnIndex = array[0].length-1;
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

    public static int solve(double[][] aimInput, double[][] restrictInput, String[] maxMin,double compromiseInput) {
        double ans;
        String fullAns = new String();
        int restrictInputRows = restrictInput.length;
        int restrictInputColumns = restrictInput[0].length;

        SimplexMethod.inpMatrixManual(aimInput[0], restrictInput, maxMin[0]);
        try {
            ans=SimplexMethod.solveTask();
            fullAns+="1) "+String.valueOf(ans)+" ";
        } catch (Exception e) {
            System.out.println(e);
            return 1;
        }

        if (aimInput.length - 1 < 1) return 1;

        for (int i = 1; i < aimInput.length; i++) {
            double[][] newRestricted = new double[restrictInputRows + 1][restrictInputColumns];
            for (int n = 0; n < restrictInput.length; n++) {
                for (int j = 0; j < restrictInput[0].length; j++) {
                    newRestricted[n][j]=restrictInput[n][j];
                }
            }
            for(int n=0;n<aimInput[0].length;n++){
                newRestricted[newRestricted.length-1][n]=aimInput[i-1][n];
            }

            Matrix.printmat(newRestricted);

            double ans1;
            double ans2;

            if (maxMin[i-1].equals("max")) {
                newRestricted[newRestricted.length - 1][newRestricted[0].length - 1] = ans-compromiseInput;
                SimplexMethod.inpMatrixManual(aimInput[i], newRestricted, maxMin[i]);
                try {
                    ans1=SimplexMethod.solveTask();
                } catch (Exception e) {
                    System.out.println(e);
                    return 1;
                }
                newRestricted[newRestricted.length - 1][newRestricted[0].length - 1] = ans;
                SimplexMethod.inpMatrixManual(aimInput[i], newRestricted, maxMin[i]);
                try {
                    ans2=SimplexMethod.solveTask();
                } catch (Exception e) {
                    System.out.println(e);
                    return 1;
                }
                if (maxMin[i].equals("max")) {
                    ans = Math.max(ans1, ans2);
                }else{
                    ans=Math.min(ans1,ans2);
                }
            }else{
                newRestricted[newRestricted.length - 1][newRestricted[0].length - 1] = ans+compromiseInput;
                SimplexMethod.inpMatrixManual(aimInput[i], newRestricted, maxMin[i]);
                try {
                    ans1=SimplexMethod.solveTask();
                } catch (Exception e) {
                    System.out.println(e);
                    return 1;
                }
                newRestricted[newRestricted.length - 1][newRestricted[0].length - 1] = ans;
                SimplexMethod.inpMatrixManual(aimInput[i], newRestricted, maxMin[i]);
                try {
                    ans2=SimplexMethod.solveTask();
                } catch (Exception e) {
                    System.out.println(e);
                    return 1;
                }
                if (maxMin[i].equals("max")) {
                    ans = Math.max(ans1, ans2);
                }else{
                    ans=Math.min(ans1,ans2);
                }
            }
            fullAns+=(i+1)+") "+ans+" ";
        }
        Log.writeLog("------- ОТВЕТ: "+fullAns+" -------",true);
        return 0;
    }

}