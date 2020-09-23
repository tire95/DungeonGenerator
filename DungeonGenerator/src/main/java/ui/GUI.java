/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ui;

import domain.CellularAutomaton;
import domain.Dungeon;
import domain.FloodFill;
import domain.RandomWalk;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author timot
 */
public class GUI extends Application {
    private CellularAutomaton automaton;
    private RandomWalk walk;
    private FloodFill fill;
    private double resolutionX;
    private double resolutionY;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) {        
        this.fill = new FloodFill();
        stage.setTitle("Dungeon generator");
        
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        Button cellular = new Button("Cellular automaton");
        Button walk = new Button("Random walk");
        Button performanceTest = new Button("Performance testing");
        
                
        cellular.setOnAction(e -> {
            automatonView(stage);
        });
        
        walk.setOnAction(e -> {
            walkView(stage);
        });
        
        performanceTest.setOnAction(e ->{
            performanceTestView(stage);
        });
        
        hbox.getChildren().addAll(cellular, walk, performanceTest);

        

        Scene scene = new Scene(hbox, 1600, 800);
        stage.setScene(scene);
        stage.show();
        
    }
    
    private void automatonView(Stage stage) {
        
        Label widthLabel = new Label("Dungeon width");
        Spinner widthSpinner = new Spinner((int) 10, (int) 100000, (int) 100);
        Label heightLabel = new Label("Dungeon height");
        Spinner heightSpinner = new Spinner((int) 10, (int) 100000, (int) 100);
        Label iterationLabel = new Label("Cellular automaton's iterations");
        Spinner iterationSpinner = new Spinner((int) 1, (int) 10, (int) 4);
        Label stoneLabel = new Label("Stone percent at start");
        Spinner stonePercentSpinner = new Spinner((int) 1, (int) 100, (int) 45);
        
        Button createAutomaton = new Button("Create a cellular automaton");
        
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(10));
        
        Button startAutomaton = new Button("Start cellular automaton");
        Button resetAutomaton = new Button("Reset automaton");
        Button cleanUp = new Button("Clean up");
        Button floodFill = new Button("Flood fill");
        
        Canvas canvas = new Canvas(500, 500);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        box.getChildren().addAll(widthLabel, widthSpinner, heightLabel, heightSpinner, iterationLabel, iterationSpinner, stoneLabel, stonePercentSpinner, createAutomaton);
        
        createAutomaton.setOnAction(e -> {
            int iterations = (int) iterationSpinner.getValue();
            int width = (int) widthSpinner.getValue();
            int height = (int) heightSpinner.getValue();
            this.resolutionY = 500/height;
            this.resolutionX = 500/width;
            int stonePercent = (int) stonePercentSpinner.getValue();
            this.automaton = new CellularAutomaton(iterations, height, width, stonePercent);
            this.automaton.initializeDungeon();
            drawDungeon(gc, this.automaton.getDungeon());
            box.getChildren().clear();
            box.getChildren().addAll(startAutomaton, cleanUp, floodFill, resetAutomaton, canvas);
        });      
        
        startAutomaton.setOnAction(e -> {
            this.automaton.runAutomaton();
            drawDungeon(gc, this.automaton.getDungeon());
        });
        
        resetAutomaton.setOnAction(e -> {
            this.automaton.reset();
            this.automaton.initializeDungeon();
            drawDungeon(gc, this.automaton.getDungeon());
        });
                
        cleanUp.setOnAction(e -> {
            this.automaton.getDungeon().cleanUp();
            drawDungeon(gc, this.automaton.getDungeon());
        });
        
        floodFill.setOnAction(e -> {
            this.fill.setDungeon(this.automaton.getDungeon());
            this.fill.findLargestConnectedArea();
            drawDungeon(gc, this.fill.getDungeon());
        });
        
        Scene scene = new Scene(box, 1600, 800);
        stage.setScene(scene);
        stage.show();
    }
    
    private void walkView(Stage stage) {
         
        Label widthLabel = new Label("Dungeon width");
        Spinner widthSpinner = new Spinner((int) 10, (int) 100000, (int) 100);
        Label heightLabel = new Label("Dungeon height");
        Spinner heightSpinner = new Spinner((int) 10, (int) 100000, (int) 100);
        Label spawnLabel = new Label("Percent chance of spawning a new walker");
        Spinner spawnSpinner = new Spinner((int) 1, (int) 100, (int) 2);
        Label digLabel = new Label("Percent of area to dig");
        Spinner digSpinner = new Spinner((int) 1, (int) 100, (int) 45);
        Label turnLabel = new Label("Percent chance of a walker turning");
        Spinner turnSpinner = new Spinner((int) 1, (int) 100, (int) 70);
        CheckBox complexBox = new CheckBox("Check if you want to use complex walk, i.e. change turn chance");
        
        Button createWalk = new Button("Create a random walk");
        
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(10));
        Button startRandom = new Button("Start random walk");
        Button resetRandom = new Button("Reset random walk");
        Button cleanUp = new Button("Clean up");
        Button floodFill = new Button("Flood fill");
        
        Canvas canvas = new Canvas(500, 500);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        box.getChildren().addAll(widthLabel, widthSpinner, heightLabel, heightSpinner, spawnLabel, spawnSpinner, digLabel, digSpinner, turnLabel, turnSpinner, complexBox, createWalk);
        
        createWalk.setOnAction(e -> {
            int width = (int) widthSpinner.getValue();
            int height = (int) heightSpinner.getValue();
            this.resolutionY = 500/height;
            this.resolutionX = 500/width;
            int spawnChance = (int) spawnSpinner.getValue();
            int digPercent = (int) digSpinner.getValue();
            int turnChance = (int) turnSpinner.getValue();
            boolean complex = complexBox.isSelected();
            this.walk = new RandomWalk(height, width, spawnChance, digPercent, turnChance, complex);
            drawDungeon(gc, this.walk.getDungeon());
            box.getChildren().clear();
            box.getChildren().addAll(startRandom, cleanUp, floodFill, resetRandom, canvas);
        });      
                                                
        startRandom.setOnAction(e -> {
            this.walk.runRandomWalk();
            drawDungeon(gc, this.walk.getDungeon());
        });
        
        resetRandom.setOnAction(e -> {
            this.walk.initDungeon();
            drawDungeon(gc, this.walk.getDungeon());
        });
        
        cleanUp.setOnAction(e -> {
            this.walk.getDungeon().cleanUp();
            drawDungeon(gc, this.walk.getDungeon());
        });
        
        floodFill.setOnAction(e -> {
            this.fill.setDungeon(this.walk.getDungeon());
            this.fill.findLargestConnectedArea();
            drawDungeon(gc, this.fill.getDungeon());
        });
        
        Scene scene = new Scene(box, 1600, 800);
        stage.setScene(scene);
        stage.show();
    }
    
    private void performanceTestView(Stage stage) {
        ListView<String> listView = new ListView<String>();
        ObservableList<String> results = FXCollections.observableArrayList();
        int i = 1;
        while (i < 1000) {
            i *= 10;
            long automatonTime = 0;
            long walkTimeSimple = 0;
            long startTime = 0;
            long endTime = 0;
            for (int j = 0; j < 100; j++) {
                this.automaton = new CellularAutomaton(4, i, i, 50);
                startTime = System.nanoTime();
                this.automaton.initializeDungeon();
                this.automaton.runAutomaton();
                endTime = System.nanoTime();
                automatonTime += (endTime - startTime);
                this.walk = new RandomWalk(i, i, 4, 50, 0, false);
                startTime = System.nanoTime();
                this.walk.runRandomWalk();
                endTime = System.nanoTime();
                walkTimeSimple += (endTime - startTime);
            }
            results.add("Cellular automaton with dimension: " + i*i + ", average time: " + automatonTime/100);
            results.add("Simple random walk with dimension: " + i*i + ", average time: " + walkTimeSimple/100);
        }
        listView.setItems(results);
        Button restart = new Button("Restart performance test");
        
        restart.setOnAction(e -> {
            performanceTestView(stage);
        });
        
        VBox box = new VBox();
        box.getChildren().addAll(listView, restart);
        Scene scene = new Scene(box, 1600, 800);
        stage.setScene(scene);
        stage.show();
    }
    
    private void drawDungeon(GraphicsContext gc, Dungeon d) {
        gc.setFill(Color.WHITE);
        for (int y = 0; y < d.getY(); y++) {
            for (int x = 0; x < d.getX(); x++) {
                if (d.cellIsFloor(y, x)) {
                    gc.fillRect(y*this.resolutionY, x*this.resolutionX, this.resolutionY, this.resolutionX);
                }
            }
        }
        gc.setFill(Color.BLACK);
        for (int y = 0; y < d.getY(); y++) {
            for (int x = 0; x < d.getX(); x++) {
                if (d.cellIsStone(y, x)) {
                    gc.fillRect(y*this.resolutionY, x*this.resolutionX, this.resolutionY, this.resolutionX);
                }
            }
        }
        
        gc.setFill(Color.LIGHTBLUE);
        for (int y = 0; y < d.getY(); y++) {
            for (int x = 0; x < d.getX(); x++) {
                if (!d.cellIsStone(y, x) && !d.cellIsFloor(y, x)) {
                    gc.fillRect(y*this.resolutionY, x*this.resolutionX, this.resolutionY, this.resolutionX);
                }
            }
        }
    }
}
