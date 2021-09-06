package bk.bsu.cs;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.Random;

/**
 * Gruppe medlemmer: Björn Keymis, Cato Lea, Michael William Aleksander Lund, Trygve Johannesen
 */

public class Main extends Application {
    private final BorderPane window = new BorderPane();
    private final Pane treePane = new Pane();
    private final Random randint = new Random();
    private Spinner<Double> spLength;
    private Spinner<Integer> spAngle;
    private Spinner<Integer> spMAngle;

    /**
     * start metode for javafx
     * @param primaryStage Stage
     */
    @Override
    public void start(Stage primaryStage) {
        treePane.setPrefHeight(1000);
        treePane.setPrefHeight(600);
        window.setTop(menu());
        window.setCenter(treePane);
        primaryStage.setScene(new Scene(window));
        primaryStage.setMaximized(true);
        primaryStage.setTitle("Tree Maker!");
        primaryStage.show();
    }

    /**
     * metode som lager GUI komponent for øverste menuet med actionlisteners
     * denne inneholder randomness, levels, start lengde på grein, start vinkel, min lengde på grein, maks endring i vinkel og lag tre knappen
     * @return HBox som inneholder menu elementer
     */
    private Parent menu(){
        HBox menu = new HBox(25);
        menu.setAlignment(Pos.CENTER);
        menu.setViewOrder(-1);

        //Randomness
        VBox rdmBoxV = new VBox(5);
        rdmBoxV.setAlignment(Pos.CENTER);
        Label lblRdm= new Label("Randomness");
        HBox rdmBoxH = new HBox(5);
        RadioButton rbRdm1 = new RadioButton("Lvl 1");
        RadioButton rbRdm2 = new RadioButton("Lvl 2");
        RadioButton rbRdm3 = new RadioButton("Lvl 3");
        RadioButton rbRdm4 = new RadioButton("Lvl 4");
        ToggleGroup tgrb = new ToggleGroup();
        rbRdm1.setToggleGroup(tgrb);
        rbRdm1.setSelected(true);
        rbRdm2.setToggleGroup(tgrb);
        rbRdm3.setToggleGroup(tgrb);
        rbRdm4.setToggleGroup(tgrb);
        rdmBoxH.getChildren().addAll(rbRdm1, rbRdm2, rbRdm3, rbRdm4);
        rdmBoxV.getChildren().addAll(lblRdm, rdmBoxH);

        //levels
        VBox lvlBox = new VBox(5);
        lvlBox.setAlignment(Pos.CENTER);
        Label lblLevels = new Label("Levels: ");
        TextField tfLevels = new TextField();
        tfLevels.setPrefWidth(100);
        tfLevels.setText("15");
        tfLevels.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tfLevels.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        lvlBox.getChildren().addAll(lblLevels, tfLevels);

        //start length
        VBox sLengthBox = new VBox(5);
        sLengthBox.setAlignment(Pos.CENTER);
        Label lblSLength = new Label("Start Length");
        spLength = new Spinner<>();
        spLength.setPrefWidth(60);
        spLength.setEditable(true);
        SpinnerValueFactory<Double> spFac = new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 200);
        spFac.setValue(120.0);
        spLength.setValueFactory(spFac);
        sLengthBox.getChildren().addAll(lblSLength, spLength);

        //start angle
        VBox sAngleBox = new VBox(5);
        sAngleBox.setAlignment(Pos.CENTER);
        Label lblSAngle = new Label("Start Angle");
        spAngle = new Spinner<>();
        spAngle.setPrefWidth(60);
        spAngle.setEditable(true);
        SpinnerValueFactory<Integer> spFacA = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 180);
        spFacA.setValue(90);
        spAngle.setValueFactory(spFacA);
        sAngleBox.getChildren().addAll(lblSAngle, spAngle);

        //max Change Angle
        VBox mAngleBox = new VBox(5);
        sAngleBox.setAlignment(Pos.CENTER);
        Label lblMAngle = new Label("Change Angle");
        spMAngle = new Spinner<>();
        spMAngle.setPrefWidth(60);
        spMAngle.setEditable(true);
        SpinnerValueFactory<Integer> spFacMA = new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 40);
        spFacMA.setValue(20);
        spMAngle.setValueFactory(spFacMA);
        mAngleBox.getChildren().addAll(lblMAngle, spMAngle);

        //minLength
        VBox mLengthBox = new VBox(5);
        mLengthBox.setAlignment(Pos.CENTER);
        Label lblMLength = new Label("Min Length");
        Spinner<Double> spMLength = new Spinner<>();
        spMLength.setPrefWidth(60);
        spMLength.setEditable(true);
        SpinnerValueFactory<Double> spFacML = new SpinnerValueFactory.DoubleSpinnerValueFactory(2, 100);
        spFacML.setValue(10.0);
        spMLength.setValueFactory(spFacML);
        mLengthBox.getChildren().addAll(lblMLength, spMLength);


        //button + action
        Button btnTree = new Button("Make Tree");
        btnTree.setOnAction(e->{
            int defLevels = 25;
            double minLength = spMLength.getValue();
            double startLength = spLength.getValue();
            double startAngle = spAngle.getValue();
            if (tfLevels.getText().length() > 0){
                defLevels = Integer.parseInt(tfLevels.getText());
            }
            treePane.getChildren().clear();
            if (rbRdm2.isSelected()){
                makeBranchRan(window.getWidth() / 2, window.getHeight(), startLength, startAngle, minLength,1, defLevels, 60);
            }else if (rbRdm3.isSelected()){
                makeBranchRan(window.getWidth() / 2, window.getHeight(), startLength, startAngle, minLength,1, defLevels, 90);
            }else if (rbRdm4.isSelected()){
                makeBranchRan(window.getWidth() / 2, window.getHeight(), startLength, startAngle, minLength,1, defLevels, 100);
            }else{
                makeBranchRan(window.getWidth() / 2, window.getHeight(), startLength, startAngle, minLength,1, defLevels, 30);
            }

        });

        menu.getChildren().addAll(rdmBoxV, lvlBox, mLengthBox, sLengthBox, sAngleBox, mAngleBox, btnTree);
        return menu;
    }

    /**
     * hjelpe metode for beregning av neste X verdi
     * @param x double verdi som er siste x
     * @param lengde double verdi avstand til neste x
     * @param vinkel double verdi vinkel som x befinner seg i
     * @return double verdi neste x
     */
    public double beregnX(double x, double lengde, double vinkel){
        return x + lengde * Math.cos(Math.toRadians(vinkel));
    }

    /**
     * hjelpe metode for beregning av neste Y verdi
     * @param y double verdi som er siste y
     * @param lengde double verdi avstand til neste y
     * @param vinkel double verdi vinkel som y befinner seg i
     * @return double verdi neste y
     */
    public double beregnY(double y, double lengde, double vinkel){
        return y - lengde * Math.sin(Math.toRadians(vinkel));
    }

    /**
     * rekursiv metode for å lage grein på treet metoden henter verdiene max change angle ut av GUI komponenten
     * @param x double: start x plassering
     * @param y double: start y plassering
     * @param length double: start lengde
     * @param angle double: start vinkel for treet
     * @param minLength double: start lengde for treet
     * @param levels int: max antall nivåer
     * @param chance int: tilfeldighets sjanse
     */
    public void makeBranchRan(double x, double y, double length, double angle, double minLength, int curlvl, int levels, int chance){
        if (minLength > length || levels == curlvl){
            System.out.println("Done");
        }else{
            System.out.println(curlvl);
            int rolledValue = randint.nextInt(chance);
            if (rolledValue < 30){
                makeBranchRan(beregnX(x, length, angle), beregnY(y, length, angle), length * 0.8, angle + spMAngle.getValue(), minLength, curlvl+1, levels, chance);
                makeBranchRan(beregnX(x, length, angle), beregnY(y, length, angle), length * 0.8, angle + -spMAngle.getValue(), minLength, curlvl+1, levels, chance);
            }else if (rolledValue > 30 && rolledValue < 60){
                makeBranchRan(beregnX(x, length, angle), beregnY(y, length, angle), length * 0.8, angle + Math.random()*spMAngle.getValue(), minLength, curlvl+1, levels, chance);
                makeBranchRan(beregnX(x, length, angle), beregnY(y, length, angle), length * 0.8, angle + Math.random()*-spMAngle.getValue(), minLength, curlvl+1, levels, chance);
            }else if (rolledValue > 60 && rolledValue < 90){
                makeBranchRan(beregnX(x, length, angle), beregnY(y, length, angle), length * Math.random(), angle + Math.random()*-spMAngle.getValue(), minLength, curlvl+1, levels, chance);
                makeBranchRan(beregnX(x, length, angle), beregnY(y, length, angle), length * Math.random(), angle + Math.random()*spMAngle.getValue(), minLength, curlvl+1, levels, chance);
            }else if (rolledValue > 90 && rolledValue < 95){
                makeBranchRan(beregnX(x, length, angle), beregnY(y, length, angle), length * Math.random(), angle + Math.random()*spMAngle.getValue(), minLength, curlvl+1, levels, chance);
            }else{
                makeBranchRan(beregnX(x, length, angle), beregnY(y, length, angle), length * Math.random(), angle + Math.random()*-spMAngle.getValue(), minLength, curlvl+1, levels, chance);
            }
            Line line = new Line(x, y, beregnX(x, length, angle), beregnY(y,length,angle));
            treePane.getChildren().add(line);
        }
    }

}
