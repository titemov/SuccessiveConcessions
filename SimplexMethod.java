import java.util.Scanner;

public class SimplexMethod {
    static int countCoef;
    static int countLimit;
    static boolean desire;
    static double[][] matrix;
    static int countRow;
    static int countCol;
    static int[] mins;
    static double answer = 0;
//    public static void main(String[] args) {
//        inpMatrixManual();
//        solveTask();
//    }
    public static double solveTask() throws Exception {
        int i = 1;
        while (!indexStrCalc()) {
            int a=i++;
            //System.out.println("\nИтерация " + a);
            Log.writeLog("\nИтерация " + a,true);
            if (!calcCheckMinIndxElCol()) {
                //System.out.println("Нет решения");
                Log.writeLog("Нет решения",true);
                throw new Exception();
            }
            calcAttitMin();
            printMatrix(true);
            calcNewCoef();
        }
        //System.out.println("\nИтерация " + i);
        Log.writeLog("\nИтерация " + i,true);
        printMatrix(false);
        answer = desire ? matrix[countLimit + 1][2] : -matrix[countLimit + 1][2];
        //System.out.println("\nОтвет: " + answer);
        Log.writeLog("\nПредварительный ответ: " + answer,true);
        return answer;
    }
    private static boolean indexStrCalc() {
        for (int i = 2; i < countCol - 1; ++i) {
            double sum = 0;
            for (int j = 1; j < countLimit + 1; ++j) {
                sum += matrix[j][0] * matrix[j][i];
            }
            matrix[countLimit + 1][i] = sum - matrix[0][i];
        }
        boolean check = true;
        for (int i = 3; i < countCol - 1; ++i) {
            if (matrix[countLimit + 1][i] < 0) {
                check = false;
                break;
            }
        }
        return check;
    }
    private static boolean calcCheckMinIndxElCol() {
        mins = new int[2];
        mins[0] = 3;
        for (int i = 4; i < countCol - 1; ++i) {
            if (matrix[countLimit + 1][i] < matrix[countLimit + 1][mins[0]]) {
                mins[0] = i;
            }
        }
        boolean check = false;
        for (int i = 1; i < countLimit + 1; ++i) {
            if (matrix[i][mins[0]] > 0) {
                check = true;
                break;
            }
        }
        return check;
    }
    private static void calcAttitMin() {
        mins[1] = 1;
        for (int i = 1; i < countLimit + 1; ++i) {
            matrix[i][countCol - 1] = matrix[i][mins[0]] >=0 ? matrix[i][2] / matrix[i][mins[0]]: 100000;
            if (matrix[i][countCol - 1] < matrix[mins[1]][countCol - 1]) {
                mins[1] = i;
            }
        }
    }
    private static void calcNewCoef() {
        matrix[mins[1]][0] = matrix[0][mins[0]];
        matrix[mins[1]][1] = mins[0] - 2;
        for (int i = 1; i < countLimit + 1; ++i) {
            if (i != mins[1]) {
                double elem = matrix[i][mins[0]];
                for (int j = 2; j < countCol - 1; ++j) {
                    matrix[i][j] = (matrix[i][j] * matrix[mins[1]][mins[0]] - matrix[mins[1]][j] * elem) / matrix[mins[1]][mins[0]];
                }
            }
        }
        double elem = matrix[mins[1]][mins[0]];
        for (int i = 2; i < countCol - 1; ++i) {
            matrix[mins[1]][i] = matrix[mins[1]][i] / elem;
        }
    }

    private static void printMatrix(boolean flag) {
        //System.out.printf("%" + 13 + "s", "Cij");
        Log.writeLog((String.format("%" + 13 + "s", "Cij")),true);
        //System.out.print("             ");
        Log.writeLog("             ",false);

        for (int i = 2; i < countCol - 1; ++i) {
            //System.out.printf("%" + 13 + "." + 3 + "f", matrix[0][i]);
            Log.writeLog(String.format("%" + 13 + "." + 3 + "f",matrix[0][i]),false);
        }
        //System.out.printf("%" + 13 + "s", "Тета\n");
        Log.writeLog(String.format("%" + 13 + "s", "Тета\n"),false);
        if (desire) {
            //System.out.printf("%" + 26 + "s", "Bx");
            Log.writeLog(String.format("%" + 26 + "s", "Bx"),false);
        }
        else {
            //System.out.printf("%" + 26 + "s", "By");
            Log.writeLog(String.format("%" + 26 + "s", "By"),false);
        }
        for (int i = 0; i < countCol - 3; ++i) {
            //System.out.printf("%" + 12 + "s", "A");
            Log.writeLog(String.format("%" + 12 + "s", "A"),false);
            //System.out.print(i);
            Log.writeLog(String.valueOf(i),false);
        }
        //System.out.println();
        Log.writeLog(" ",true);
        int col = flag ? countCol : countCol - 1;
        for (int i = 1; i < countLimit + 1; ++i) {
            for (int j = 0; j < col; ++j) {
                //System.out.printf("%" + 13 + "." + 3 + "f", matrix[i][j]);
                Log.writeLog(String.format("%" + 13 + "." + 3 + "f", matrix[i][j]),false);
            }
            //System.out.println();
            Log.writeLog(" ",true);
        }
        //System.out.printf("%" + 26 + "s", "Дельта j");
        Log.writeLog(String.format("%" + 26 + "s", "Дельта j"),false);
        for (int i = 2; i < countCol - 1; ++i) {
            //System.out.printf("%" + 13 + "." + 3 + "f", matrix[countLimit + 1][i]);
            Log.writeLog(String.format("%" + 13 + "." + 3 + "f", matrix[countLimit + 1][i]),false);
        }
        //System.out.println("\nДопустимое базисное решение:");
        Log.writeLog("\nДопустимое базисное решение:",true);
        //System.out.print("(");
        Log.writeLog("(",false);
        for (int i = 0; i < countCol - 4; ++i) {
            boolean check = false;
            for (int j = 1; j < countLimit + 1; ++j) {
                if (i + 1 == matrix[j][1]) {
                    if (i != countCol - 5) {
                        //System.out.print(matrix[j][2] + ", ");
                        Log.writeLog(matrix[j][2] + ", ",false);
                    }
                    else {
                        //System.out.print(matrix[j][2] + ")");
                        Log.writeLog(matrix[j][2] + ")",false);
                    }
                    check = true;
                    break;
                }
            }
            if (!check) {
                if (i != countCol - 5) {
                    //System.out.print(0.0 + ", ");
                    Log.writeLog(0.0 + ", ",false);
                }
                else {
                    //System.out.print(0.0 + ")");
                    Log.writeLog(0.0 + ")",false);
                }
            }
        }
        //System.out.println();
        Log.writeLog(" ",true);
    }
    private static void inpData(double[] aimInput, int restrictAmount, String maxMin) {
        boolean check = true;
        while (check) {
            countCoef=aimInput.length;
            //System.out.println("Введите количество коэффициентов целевой функции = "+countCoef);
            Log.writeLog("Количество коэффициентов целевой функции = "+countCoef,true);
            countLimit=restrictAmount;
            //System.out.println("Введите количество ограничений = "+countLimit);
            Log.writeLog("Количество ограничений = "+countLimit,true);
            if (countCoef < 1 || countLimit < 1) {
                //System.out.println("Количество ограничений и коэффициентов целевой функции не должно быть меньше единицы");
                Log.writeLog("Количество ограничений и коэффициентов целевой функции не должно быть меньше единицы",true);
                continue;
            }
            else {
                check = false;
            }
            String str;
            do {
                str = maxMin;
            } while (!str.equals("max") && !str.equals("min"));
            if (str.equals("max")) {
                desire = true;
            }
            else {
                desire = false;
            }
            //System.out.println("Функция стремится к min или к max? "+maxMin);
            Log.writeLog("Функция стремится к "+maxMin,true);
        }


    }
    public static void inpMatrixManual(double[] aimInput, double[][] restrictInput, String maxMin) {
        inpData(aimInput,restrictInput.length,maxMin);
        if (desire) {
            countRow = countLimit + 2;
            countCol = countCoef + countLimit + 4;
            matrix = new double[countRow][countCol];
        }
        else {
            countRow = countLimit + 2;
            countCol = countCoef + 2 * countLimit + 4;
            matrix = new double[countRow][countCol];
        }
        //System.out.println("Введите коэффициенты целевой функции");
        Log.writeLog("--- Коэффициенты целевой функции ---",true);
        matrix[0][2] = 0;
        for (int i = 3; i < countCoef + 3; ++i) {
            //System.out.print("Введите коэффициент целевой функции " + (i - 2) + ": ");
            matrix[0][i] = aimInput[i-3];
            //System.out.println(matrix[0][i]);
            Log.writeLog("коэффициент целевой функции " + (i - 2) + ": "+matrix[0][i],true);
            if (!desire) {
                matrix[0][i] = -matrix[0][i];
            }
        }
        for (int i = countCoef + 3; i < countLimit + countCoef + 3; ++i) {
            matrix[0][i] = 0;
        }
        if (!desire) {
            for (int i = countCoef + 3 + countLimit; i < 2 * countLimit + countCoef + 3; ++i) {
                matrix[0][i] = -100000;
            }
        }
        for (int i = 1; i < countLimit + 1; ++i) {
            //System.out.println("Ограничение " + i);
            Log.writeLog("--- Ограничение " + i+" ---",true);
            for (int j = 3; j < countCoef + 3; ++j) {
                matrix[i][j] = restrictInput[i-1][j-3];
                //System.out.println("Введите коэффициент " + (j-2) + ": "+matrix[i][j]);
                Log.writeLog("Коэффициент ограничения " + (j-2) + ": "+matrix[i][j],true);
            }

            matrix[i][2] = restrictInput[i-1][restrictInput[0].length-1];
            //System.out.println("Правая часть ограничения " + i + ": "+matrix[i][2]);
            Log.writeLog("Правая часть ограничения " + i + ": "+matrix[i][2],true);
            if (!desire) {
                matrix[i][countCoef + 2 + i] = -1;
                matrix[i][countCoef + 2 + i + countLimit] = 1;
            }
            else {
                matrix[i][countCoef + 2 + i] = 1;
            }
        }
        int coef = countLimit + 1;
        for (int i = 1; i < countLimit + 1; ++i) {
            matrix[i][0] = matrix[0][countCol - coef];
            matrix[i][1] = countCol - coef-- - 2;
        }
    }
}
