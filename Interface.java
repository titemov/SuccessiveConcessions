import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Line;

public class Interface extends Application {

    double [][] aimInput;
    String[] maxMin = new String[5];
    double [][] restrictInput;
    //double[][] restrictRightInput = new double[5][1];
    double compromiseInput;


    @Override
    public void start(Stage stage){

        Group mainGroup = new Group();

        Group inputGroup = new Group();

        Label aimLabel = new Label("Введите коэффициенты функций цели:");
        aimLabel.setLayoutX(10);
        aimLabel.setLayoutY(10);
        inputGroup.getChildren().add(aimLabel);

        ObservableList<String> striving = FXCollections.observableArrayList("max", "min");

        Label[][] aimLabels = new Label[5][5];
        TextField[][] aimTF = new TextField[5][5];
        ComboBox<String>[][] aimCB = new ComboBox[5][1];

        for (int i=0;i<aimLabels.length;i++){
            for (int n=0;n<aimLabels[0].length;n++){
                aimTF[i][n]= new TextField("0");
                aimTF[i][n].setLayoutX(10+(50*n));
                aimTF[i][n].setLayoutY(30+(30*i));
                aimTF[i][n].setMaxHeight(20);
                aimTF[i][n].setMaxWidth(30);

                aimLabels[i][n] = new Label();
                aimLabels[i][n].setLayoutX(10+30+(50*n));
                aimLabels[i][n].setLayoutY(35+(30*i));
                if (n!=4) {
                    aimLabels[i][n].setText("x" + (n + 1) + "+");
                }else{
                    aimLabels[i][n].setText("x" + (n + 1) + " -> ");
                }

                inputGroup.getChildren().addAll(aimTF[i][n],aimLabels[i][n]);

            }
            aimCB[i][0] = new ComboBox<String>(striving);
            aimCB[i][0].setLayoutX(270);
            aimCB[i][0].setLayoutY(30+(30*i));
            aimCB[i][0].setValue("max");
            aimCB[i][0].setMaxWidth(75);
            aimCB[i][0].setMinWidth(75);
            inputGroup.getChildren().add(aimCB[i][0]);
        }

        Label restrictLabel = new Label("Введите коэффициенты ограничений:");
        restrictLabel.setLayoutX(375);
        restrictLabel.setLayoutY(10);
        inputGroup.getChildren().add(restrictLabel);

        Label[][] restrictLabels = new Label[5][5];
        TextField[][] restrictTF = new TextField[5][6];

        for (int i=0;i<restrictLabels.length;i++){
            for (int n=0;n<restrictLabels[0].length;n++) {
                restrictLabels[i][n] = new Label();
                restrictLabels[i][n].setLayoutX(375+30+(50*n));
                restrictLabels[i][n].setLayoutY(35+(30*i));
                if (n!=4) {
                    restrictLabels[i][n].setText("x" + (n + 1) + "+");
                }else{
                    restrictLabels[i][n].setText("x" + (n + 1) + " ≥");
                }
                inputGroup.getChildren().add(restrictLabels[i][n]);
            }
        }

        for (int i=0;i<restrictTF.length;i++){
            for (int n=0;n<restrictTF[0].length;n++) {
                restrictTF[i][n] = new TextField("0");
                if (n!=5) {
                    restrictTF[i][n].setLayoutX(375 + (50 * n));
                }else{
                    restrictTF[i][n].setLayoutX(375 + 10 + (50 * n));
                }
                restrictTF[i][n].setLayoutY(30+(30*i));
                restrictTF[i][n].setMaxHeight(20);
                restrictTF[i][n].setMaxWidth(30);
                inputGroup.getChildren().add(restrictTF[i][n]);
            }
        }

        Label compromiseLabel = new Label();
        compromiseLabel.setLayoutX(10);
        compromiseLabel.setLayoutY(210);
        compromiseLabel.setText("Введите размер уступки:");
        inputGroup.getChildren().add(compromiseLabel);

        TextField compromiseTF = new TextField("0");
        compromiseTF.setLayoutX(150);
        compromiseTF.setLayoutY(205);
        compromiseTF.setMaxWidth(30);
        inputGroup.getChildren().add(compromiseTF);

        Label resultLabel = new Label(" ");//
        resultLabel.setLayoutX(375);
        resultLabel.setLayoutY(210);
        inputGroup.getChildren().add(resultLabel);

        Button enterBtn = new Button("Ввести всё");
        enterBtn.setLayoutX(270);
        enterBtn.setLayoutY(205);
        enterBtn.setMaxWidth(75);
        enterBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                double[][] aimTemp = new double[5][5];
                double[][] restrictTemp = new double[5][6];
                //double[][] restrictRightTemp = new double[5][1];
                for (int i=0; i<aimTF.length; i++){
                    for (int n=0;n<aimTF[0].length;n++){
                        aimTemp[i][n]=Double.parseDouble(aimTF[i][n].getText());
                    }
                }
                for (int i=0; i<restrictTF.length; i++) {
                    for (int n = 0; n < restrictTF[0].length; n++) {
                        //-1 because right side of equation in restrictRightInput
                        restrictTemp[i][n]=Double.parseDouble(restrictTF[i][n].getText());
                    }
                }
//                for (int i=0; i<restrictTF.length; i++) {
//                    for (int n = 0; n < restrictTF[0].length-(restrictTF[0].length-1); n++) {
//                        //-1 because right side of equation in restrictRightInput
//                        restrictRightInput[i][n]=Double.parseDouble(restrictTF[i][restrictTF[0].length-1].getText());
//                    }
//                }
                for(int i = 0;i<maxMin.length;i++){
                    maxMin[i]= String.valueOf(aimCB[i][0].getValue());
                    //System.out.println(maxMin[i]);
                }
                compromiseInput=Double.parseDouble(compromiseTF.getText());
                aimInput=Backend.arrayOptimization(aimTemp,false);
                if (aimInput==null){
                    resultLabel.setText("Ошибка! Нет функций цели.");
                    return;
                }
                restrictInput=Backend.arrayOptimization(restrictTemp,true);
                if (restrictInput==null){
                    resultLabel.setText("Ошибка! Нет ограничений.");
                    return;
                }

                resultLabel.setText(" ");
                Matrix.printmat(aimInput);
                Matrix.printmat(restrictInput);
                //Matrix.printmat(restrictRightInput);
            }
        });
        inputGroup.getChildren().add(enterBtn);

        TextArea textArea = new TextArea();
        textArea.setLayoutX(10);
        textArea.setLayoutY(250);
        textArea.setMinHeight(300);
        textArea.setMaxHeight(300);
        textArea.setMinWidth(765);
        textArea.setMaxWidth(765);


        mainGroup.getChildren().addAll(inputGroup,textArea);

        Scene scene = new Scene(mainGroup, Color.SNOW);
        stage.setScene(scene);
        stage.setTitle("TPR");
        stage.setWidth(800);
        stage.setHeight(600);
        stage.setResizable(false);
        stage.show();
    }

    public static void show(){
        Application.launch();
    }
}
