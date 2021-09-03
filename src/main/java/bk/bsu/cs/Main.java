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
        tfLevels.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tfLevels.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        Label lblMLength = new Label("Min Length");
        ComboBox<Integer> comboBox = new ComboBox<>();
        Integer[] values = {2,4,6,8,10,12,14,16,18,20};
        comboBox.setItems(FXCollections.observableArrayList(values));
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
                //makeBranchR(window.getWidth() / 2, window.getHeight(), 120, 90, minLength, defLevels, 0);
                makeBranchR(window.getWidth() / 2, window.getHeight(), 120, 90, minLength, defLevels);
            }else if (rbRdm3.isSelected()){
                //makeBranchR3(window.getWidth() / 2, window.getHeight(), 120, 90, minLength, defLevels, 0);
                makeBranchR3(window.getWidth() / 2, window.getHeight(), 120, 90, minLength, defLevels);
            }else if (rbRdm4.isSelected()){
                makeBranchR4(window.getWidth() / 2, window.getHeight(), 120, 90, minLength, defLevels);
            }else{
                makeBranch(window.getWidth() / 2, window.getHeight(), 120, 90, minLength, defLevels, 0);
            }

        });

        menu.getChildren().addAll(lblRdm, rbRdm1, rbRdm2, rbRdm3, rbRdm4, lblLevels, tfLevels, btnTree, lblMLength, comboBox);
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
     * Rekursiv metode som lager en grein med ingen random verdier, metoden vil fortsette n antall nivåer eller til min length er nådd
     * default minlength er satt till 10 pixler og kan endres ved hjelp av length variablen
     * @param x X plaseringen av første grein
     * @param y Y plaseringen av første grein
     * @param length lengde av første grein
     * @param angle vinkel av første grein
     * @param minLength minste lengde siste grein kan være
     * @param levels hjelpe variabel for nivåer må være 0
     * @param n maks antall nivåer
     */
    public void makeBranch(double x, double y, double length, double angle, double minLength, int levels, int n){
        if (minLength > length || n == levels){

        }else{
            n++;
            makeBranch(beregnX(x, length, angle), beregnY(y, length, angle), length * 0.8, angle + 20, minLength, levels, n);
            makeBranch(beregnX(x, length, angle), beregnY(y, length, angle), length * 0.8, angle + -20, minLength, levels, n);
            Line line = new Line(x, y, beregnX(x, length, angle), beregnY(y,length,angle));
            treePane.getChildren().add(line);
        }
    }
    /**
     * Rekursiv metode som lager en grein med nivå 1 random verdier (grein vinkel er tilfeldig), metoden vil fortsette n antall nivåer eller til min length er nådd
     * default minlength er satt till 10 pixler og kan endres ved hjelp av length variablen
     * @param x X plaseringen av første grein
     * @param y Y plaseringen av første grein
     * @param length lengde av første grein
     * @param angle vinkel av første grein
     * @param minLength minste lengde siste grein kan være
     * @param levels hjelpe variabel for nivåer må være 0
     */
    public void makeBranchR(double x, double y, double length, double angle, double minLength, int levels){
        if (minLength > length || curLevel == levels){

        }else{
            levels++;
            makeBranchR(beregnX(x, length, angle), beregnY(y, length, angle), length * 0.8, angle + Math.random()*30, minLength, levels);
            makeBranchR(beregnX(x, length, angle), beregnY(y, length, angle), length * 0.8, angle + Math.random()*-30, minLength, levels);
            Line line = new Line(x, y, beregnX(x, length, angle), beregnY(y,length,angle));
            treePane.getChildren().add(line);
        }
    }
    /**
     * Rekursiv metode som lager en grein med nivå 2 random verdier (grein vinkel, lengde på grein), metoden vil fortsette n antall nivåer eller til min length er nådd
     * default minlength er satt till 10 pixler og kan endres ved hjelp av length variablen
     * @param x X plaseringen av første grein
     * @param y Y plaseringen av første grein
     * @param length lengde av første grein
     * @param angle vinkel av første grein
     * @param minLength minste lengde siste grein kan være
     * @param levels hjelpe variabel for nivåer må være 0
     */
    public void makeBranchR3(double x, double y, double length, double angle, double minLength, int levels){
        if (minLength > length || curLevel == levels){

        }else{
            curLevel++;
            makeBranchR3(beregnX(x, length, angle), beregnY(y, length, angle), length * Math.random(), angle + Math.random()*30, minLength, levels);
            makeBranchR3(beregnX(x, length, angle), beregnY(y, length, angle), length * Math.random(), angle + Math.random()*-30, minLength, levels);
            Line line = new Line(x, y, beregnX(x, length, angle), beregnY(y,length,angle));
            treePane.getChildren().add(line);
        }
    }
    /**
     * Rekursiv metode som lager en grein med nivå 3 random verdier (grein vinkel, lengde på grein og antall grein (1 eller 2))), metoden vil fortsette n antall nivåer eller til min length er nådd
     * default minlength er satt till 10 pixler og kan endres ved hjelp av length variablen
     * @param x X plaseringen av første grein
     * @param y Y plaseringen av første grein
     * @param length lengde av første grein
     * @param angle vinkel av første grein
     * @param minLength minste lengde siste grein kan være
     * @param levels hjelpe variabel for nivåer må være 0
     */
    public void makeBranchR4(double x, double y, double length, double angle, double minLength, int levels){
        if (minLength > length || levels == curLevel){

        }else{
            if (randint.nextInt(4) == 1){
                makeBranchR4(beregnX(x, length, angle), beregnY(y, length, angle), length * Math.random(), angle + Math.random()*30, minLength, levels);
            }else if (randint.nextInt(4) == 2){
                makeBranchR4(beregnX(x, length, angle), beregnY(y, length, angle), length * Math.random(), angle + Math.random()*-30, minLength, levels);
            }else {
                makeBranchR4(beregnX(x, length, angle), beregnY(y, length, angle), length * Math.random(), angle + Math.random()*30, minLength, levels);
                makeBranchR4(beregnX(x, length, angle), beregnY(y, length, angle), length * Math.random(), angle + Math.random()*-30, minLength, levels);
            }
            curLevel++;
            Line line = new Line(x, y, beregnX(x, length, angle), beregnY(y,length,angle));
            treePane.getChildren().add(line);
        }
    }

}
