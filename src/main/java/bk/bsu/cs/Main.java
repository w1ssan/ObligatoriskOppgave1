package bk.bsu.cs;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.Random;

public class Main extends Application {
    private final BorderPane window = new BorderPane();
    private final Pane treePane = new Pane();
    private int curLevel = 0;
    private final Random randint = new Random();

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
        primaryStage.show();
    }

    /**
     * metode som lager GUI komponent for øverste menuet med actionlisteners
     * @return HBox som inneholder menu elementer
     */
    private Parent menu(){
        HBox menu = new HBox(25);
        menu.setAlignment(Pos.CENTER);
        menu.setViewOrder(-1);
        Label lblRdm= new Label("Randomness");
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
        Label lblLevels = new Label("Levels: ");
        TextField tfLevels = new TextField();
        tfLevels.setText("25");
        tfLevels.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tfLevels.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        Label lblMLength = new Label("Min Length");
        ComboBox<Integer> comboBox = new ComboBox<>();
        Integer[] values = {2,4,6,8,10,12,14,16,18,20};
        comboBox.setItems(FXCollections.observableArrayList(values));
        comboBox.setPromptText("10");
        Button btnTree = new Button("Make Tree");
        btnTree.setOnAction(e->{
            int defLevels = 25;
            int minLength = 10;
            curLevel = 0;
            if (tfLevels.getText().length() > 0){
                defLevels = Integer.parseInt(tfLevels.getText());
            }
            if (comboBox.getValue() != null){
                minLength = comboBox.getValue();
            }
            treePane.getChildren().clear();
            if (rbRdm2.isSelected()){
                makeBranchRan(window.getWidth() / 2, window.getHeight(), 120, 90, minLength, defLevels, 60);
            }else if (rbRdm3.isSelected()){
                makeBranchRan(window.getWidth() / 2, window.getHeight(), 120, 90, minLength, defLevels, 90);
            }else if (rbRdm4.isSelected()){
                makeBranchRan(window.getWidth() / 2, window.getHeight(), 120, 90, minLength, defLevels, 100);
            }else{
                makeBranchRan(window.getWidth() / 2, window.getHeight(), 120, 90, minLength, defLevels, 30);
            }

        });

        menu.getChildren().addAll(lblRdm, rbRdm1, rbRdm2, rbRdm3, rbRdm4, lblLevels, tfLevels, lblMLength, comboBox, btnTree);
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
     * rekursiv metode for å lage grein på treet
     * @param x double: start x plassering
     * @param y double: start y plassering
     * @param length double: start lengde
     * @param angle double: start vinkel for treet
     * @param minLength double: start lengde for treet
     * @param levels int: max antall nivåer
     * @param chance int: tilfeldighets sjanse
     */
    public void makeBranchRan(double x, double y, double length, double angle, double minLength, int levels, int chance){
        if (minLength > length || levels == curLevel){
        }else{
            int rolledValue = randint.nextInt(chance);
            if (rolledValue < 30){
                makeBranchRan(beregnX(x, length, angle), beregnY(y, length, angle), length * 0.8, angle + 20, minLength, levels, chance);
                makeBranchRan(beregnX(x, length, angle), beregnY(y, length, angle), length * 0.8, angle + -20, minLength, levels, chance);
            }else if (rolledValue > 30 && rolledValue < 60){
                makeBranchRan(beregnX(x, length, angle), beregnY(y, length, angle), length * 0.8, angle + Math.random()*30, minLength, levels, chance);
                makeBranchRan(beregnX(x, length, angle), beregnY(y, length, angle), length * 0.8, angle + Math.random()*-30, minLength, levels, chance);
            }else if (rolledValue > 60 && rolledValue < 90){
                makeBranchRan(beregnX(x, length, angle), beregnY(y, length, angle), length * Math.random(), angle + Math.random()*-30, minLength, levels, chance);
                makeBranchRan(beregnX(x, length, angle), beregnY(y, length, angle), length * Math.random(), angle + Math.random()*30, minLength, levels, chance);
            }else if (rolledValue > 90 && rolledValue < 95){
                makeBranchRan(beregnX(x, length, angle), beregnY(y, length, angle), length * Math.random(), angle + Math.random()*30, minLength, levels, chance);
            }else{
                makeBranchRan(beregnX(x, length, angle), beregnY(y, length, angle), length * Math.random(), angle + Math.random()*-30, minLength, levels, chance);
            }
            curLevel++;
            Line line = new Line(x, y, beregnX(x, length, angle), beregnY(y,length,angle));
            treePane.getChildren().add(line);
        }
    }

}
