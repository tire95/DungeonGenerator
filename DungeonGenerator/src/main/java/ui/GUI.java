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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tests.PerformanceTests;

/**
 * Graphical user interface
 * @author timot
 */
public class GUI extends Application {
    private CellularAutomaton automaton;
    private RandomWalk walk;
    private FloodFill fill;
    private double resolutionX;
    private double resolutionY;
    private int floodFillChoice;
    private int canvasSize = 500;
    private Canvas canvas = new Canvas(canvasSize, canvasSize);
    private GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
    private PerformanceTests performanceTests = new PerformanceTests();
    
    /**
     * Method for launching JavaFX GUI
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) {        
        stage.setTitle("Dungeon generator");
        
        VBox box = getVBox();
        
        Label infoLabel = new Label("Please read the README and the USER GUIDE before continuing");
        Button cellular = new Button("Cellular automaton");
        Button walk = new Button("Random walk");
        Button performanceTest = new Button("Performance testing");        
                
        cellular.setOnAction(e -> {
            automatonView(stage);
        });
        
        walk.setOnAction(e -> {
            walkView(stage);
        });
        
        performanceTest.setOnAction(e -> {
            performanceTestView(stage);
        });
        
        
        box.getChildren().addAll(infoLabel, cellular, walk, performanceTest);

        Scene scene = new Scene(box, 1200, 800);
        stage.setScene(scene);
        stage.show();
        
    }
    
    private void automatonView(Stage stage) {
        Label widthLabel = new Label("Dungeon width");
        Spinner widthSpinner = new Spinner((int) 10, (int) this.canvasSize, (int) 100);
        Label heightLabel = new Label("Dungeon height");
        Spinner heightSpinner = new Spinner((int) 10, (int) this.canvasSize, (int) 100);
        Label iterationLabel = new Label("Cellular automaton's iterations");
        Spinner iterationSpinner = new Spinner((int) 1, (int) 10, (int) 4);
        Label stoneLabel = new Label("Stone percent at start");
        Spinner stonePercentSpinner = new Spinner((int) 1, (int) 100, (int) 45);
        Label floodFillChoiceLabel = new Label("Choose flood fill algorithm");
        ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList("Forest fire", "Scan fill"));
        cb.setValue("Forest fire");
        
        Button createAutomaton = new Button("Create a cellular automaton");
        
        BorderPane border = new BorderPane();
        border.setPadding(new Insets(10));
        
        Button mainMenu = new Button("Main menu");
        mainMenu.setOnAction(e -> {
            start(stage);
        });
        
        border.setTop(mainMenu);
        
        VBox box = getVBox();

        Button startAutomaton = new Button("Start cellular automaton");
        Button resetAutomaton = new Button("Reset automaton");
        Button cleanUp = new Button("Clean up");
        Button floodFill = new Button("Find largest connected area");
        
        box.getChildren().addAll(widthLabel, widthSpinner, heightLabel, heightSpinner, iterationLabel, iterationSpinner, 
                stoneLabel, stonePercentSpinner, floodFillChoiceLabel, cb, createAutomaton);
        border.setCenter(box);
        
        createAutomaton.setOnAction(e -> {
            int iterations = (int) iterationSpinner.getValue();
            int width = (int) widthSpinner.getValue();
            int height = (int) heightSpinner.getValue();
            this.resolutionY = this.canvasSize / height;
            this.resolutionX = this.canvasSize / width;
            int stonePercent = (int) stonePercentSpinner.getValue();
            String choice = (String) cb.getValue();
            floodFillChoice = choice.equals("Forest fire") ? 0 : 1;
            this.automaton = new CellularAutomaton(iterations, height, width, stonePercent);
            this.automaton.initializeDungeon();
            drawDungeon(this.automaton.getDungeon());
            box.getChildren().clear();
            box.getChildren().addAll(startAutomaton, cleanUp, floodFill, resetAutomaton);
            border.setCenter(this.canvas);
            border.setLeft(box);
        });      
        
        startAutomaton.setOnAction(e -> {
            this.automaton.runAutomaton();
            drawDungeon(this.automaton.getDungeon());
        });
        
        resetAutomaton.setOnAction(e -> {
            this.automaton.reset();
            automatonView(stage);
        });
                
        cleanUp.setOnAction(e -> {
            this.automaton.getDungeon().cleanUp();
            drawDungeon(this.automaton.getDungeon());
        });
        
        floodFill.setOnAction(e -> {
            this.fill = new FloodFill(this.automaton.getDungeon(), this.floodFillChoice);
            this.fill.findLargestConnectedArea();
            drawDungeon(this.fill.getDungeon());
        });
        
        Scene scene = new Scene(border, 1200, 800);
        stage.setScene(scene);
        stage.show();
    }
    
    private void walkView(Stage stage) {
         
        Label widthLabel = new Label("Dungeon width");
        Spinner widthSpinner = new Spinner((int) 10, (int) this.canvasSize, (int) 100);
        Label heightLabel = new Label("Dungeon height");
        Spinner heightSpinner = new Spinner((int) 10, (int) this.canvasSize, (int) 100);
        Label spawnLabel = new Label("Percent chance of spawning a new walker");
        Spinner spawnSpinner = new Spinner((int) 1, (int) 100, (int) 2);
        Label digLabel = new Label("Percent of area to dig");
        Spinner digSpinner = new Spinner((int) 1, (int) 100, (int) 45);
        Label turnLabel = new Label("Percent chance of a walker turning");
        Spinner turnSpinner = new Spinner((int) 1, (int) 100, (int) 70);
        CheckBox complexBox = new CheckBox("Check if you want to use complex walk, i.e. change turn chance");
        Label floodFillChoiceLabel = new Label("Choose flood fill algorithm");
        ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList("Forest fire", "Scan fill"));
        cb.setValue("Forest fire");
        
        Button createWalk = new Button("Create a random walk");
        
        BorderPane border = new BorderPane();
        border.setPadding(new Insets(10));
        
        Button mainMenu = new Button("Main menu");
        mainMenu.setOnAction(e -> {
            start(stage);
        });
        
        border.setTop(mainMenu);
        
        VBox box = getVBox();

        Button startRandom = new Button("Start random walk");
        Button resetRandom = new Button("Reset random walk");
        Button cleanUp = new Button("Clean up");
        Button floodFill = new Button("Find largest connected area");
        
        border.setCenter(box);

        
        box.getChildren().addAll(widthLabel, widthSpinner, heightLabel, heightSpinner, spawnLabel, spawnSpinner, 
                digLabel, digSpinner, turnLabel, turnSpinner, complexBox, floodFillChoiceLabel, cb, createWalk);
        
        createWalk.setOnAction(e -> {
            int width = (int) widthSpinner.getValue();
            int height = (int) heightSpinner.getValue();
            this.resolutionY = this.canvasSize / height;
            this.resolutionX = this.canvasSize / width;
            int spawnChance = (int) spawnSpinner.getValue();
            int digPercent = (int) digSpinner.getValue();
            int turnChance = (int) turnSpinner.getValue();
            boolean complex = complexBox.isSelected();
            String choice = (String) cb.getValue();
            floodFillChoice = choice.equals("Forest fire") ? 0 : 1;
            this.walk = new RandomWalk(height, width, spawnChance, digPercent, turnChance, complex);
            drawDungeon(this.walk.getDungeon());
            box.getChildren().clear();
            box.getChildren().addAll(startRandom, cleanUp, floodFill, resetRandom);
            border.setCenter(this.canvas);
            border.setLeft(box);
        });      
                                                
        startRandom.setOnAction(e -> {
            this.walk.runRandomWalk();
            drawDungeon(this.walk.getDungeon());
        });
        
        resetRandom.setOnAction(e -> {
            walkView(stage);
        });
        
        cleanUp.setOnAction(e -> {
            this.walk.getDungeon().cleanUp();
            drawDungeon(this.walk.getDungeon());
        });
        
        floodFill.setOnAction(e -> {
            this.fill = new FloodFill(this.walk.getDungeon(), this.floodFillChoice);
            this.fill.findLargestConnectedArea();
            drawDungeon(this.fill.getDungeon());
        });
        
        Scene scene = new Scene(border, 1200, 800);
        stage.setScene(scene);
        stage.show();
    }
    
    private void performanceTestView(Stage stage) {
        Button generationTest = new Button("Dungeon generation performance tests");
        Button walkSpawnTest = new Button("Random walk performance tests for spawn chance");
        Button walkDigTest = new Button("Random walk performance tests for dig percent");
        Button fillTest = new Button("Flood fill performance tests");
        VBox box = getVBox();

        box.getChildren().addAll(walkSpawnTest, walkDigTest, generationTest, fillTest);
        
        BorderPane border = new BorderPane();
        border.setPadding(new Insets(10));
        
        Button mainMenu = new Button("Main menu");
        mainMenu.setOnAction(e -> {
            start(stage);
        });
        
        border.setTop(mainMenu);
        
        generationTest.setOnAction(e -> {
            generationTestView(stage);
        });
        
        walkSpawnTest.setOnAction(e -> {
            walkSpawnTestView(stage);
        });
        
        walkDigTest.setOnAction(e -> {
            walkDigTestView(stage);
        });
        
        fillTest.setOnAction(e -> {
            fillTestView(stage);
        });
        
        border.setCenter(box);
        
        Scene scene = new Scene(border, 1200, 800);
        stage.setScene(scene);
        stage.show();
    }
    
    private void generationTestView(Stage stage) {
        Label iterationLabel = new Label("Cellular automaton's iterations");
        Spinner iterationSpinner = new Spinner((int) 1, (int) 10, (int) 4);
        Label stoneLabel = new Label("Cellular automaton's stone percent at start");
        Spinner stonePercentSpinner = new Spinner((int) 1, (int) 100, (int) 45);
        Label spawnLabel = new Label("Random walk's spawn chance for new walker");
        Spinner spawnSpinner = new Spinner((int) 1, (int) 100, (int) 2);
        Label digLabel = new Label("Random walk's dig percent");
        Spinner digSpinner = new Spinner((int) 1, (int) 100, (int) 45);
        Label averageLabel = new Label("How many runs to average");
        Spinner averageSpinner = new Spinner((int) 1, (int) 100, (int) 10);
        Label maximumLabel = new Label("Dungeon's maximum size in cells");
        ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList(100, 1024, 4096, 10000, 40000, 90000, 160000, 250000, 360000, 640000, 1000000, 1440000, 2250000, 3240000, 4000000));
        cb.setValue(10000);
        Button beginButton = new Button("Begin performance test");
        
        VBox box = getVBox();
        
        BorderPane border = new BorderPane();
        border.setPadding(new Insets(10));
        
        Button mainMenu = new Button("Main menu");
        mainMenu.setOnAction(e -> {
            start(stage);
        });
        
        border.setTop(mainMenu);
        
        border.setCenter(box);
        
        
        box.getChildren().addAll(iterationLabel, iterationSpinner, stoneLabel, stonePercentSpinner, spawnLabel, spawnSpinner, digLabel, digSpinner, averageLabel, averageSpinner, maximumLabel, cb, beginButton);
        Button restart = new Button("Back to test menu");
        
        beginButton.setOnAction(e -> {
            Label resultLabel = new Label();
            int iterations = (int) iterationSpinner.getValue();
            int stonePercent = (int) stonePercentSpinner.getValue();
            int spawnChance = (int) spawnSpinner.getValue();
            int digPercent = (int) digSpinner.getValue();
            int average = (int) averageSpinner.getValue();
            int maximum = (int) cb.getValue();
            if (this.performanceTests.generationPerformanceTests(iterations, stonePercent, spawnChance, digPercent, average, maximum)) {
                resultLabel.setText("Results succesfully written to file!");
            } else {
                resultLabel.setText("File not found");
            }
            
            box.getChildren().clear();
            box.getChildren().addAll(resultLabel, restart);
        });
        
        restart.setOnAction(e -> {
            performanceTestView(stage);
        });
        
        Scene scene = new Scene(border, 1200, 800);
        stage.setScene(scene);
        stage.show();
    }
    
    private void fillTestView(Stage stage) {
        Label iterationLabel = new Label("Cellular automaton's iterations");
        Spinner iterationSpinner = new Spinner((int) 1, (int) 10, (int) 4);
        Label stoneLabel = new Label("Cellular automaton's stone percent at start");
        Spinner stonePercentSpinner = new Spinner((int) 1, (int) 100, (int) 45);
        Label averageLabel = new Label("How many runs to average");
        Spinner averageSpinner = new Spinner((int) 1, (int) 100, (int) 10);
        Label maximumLabel = new Label("Dungeon's maximum size in cells");
        ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList(100, 1024, 4096, 10000, 40000, 90000, 160000, 250000, 360000, 640000, 1000000, 1440000, 2250000, 3240000, 4000000));
        cb.setValue(10000);        
        Button beginButton = new Button("Begin performance test");
        
        VBox box = getVBox();
        
        BorderPane border = new BorderPane(); 
        border.setPadding(new Insets(10));
        
        Button mainMenu = new Button("Main menu");
        mainMenu.setOnAction(e -> {
            start(stage);
        });
        
        border.setTop(mainMenu);
        
        border.setCenter(box);
        
        
        box.getChildren().addAll(iterationLabel, iterationSpinner, stoneLabel, stonePercentSpinner, averageLabel, averageSpinner, maximumLabel, cb, beginButton);
        Button restart = new Button("Back to test menu");
        
        beginButton.setOnAction(e -> {
            Label resultLabel = new Label();
            int iterations = (int) iterationSpinner.getValue();
            int stonePercent = (int) stonePercentSpinner.getValue();
            int average = (int) averageSpinner.getValue();
            int maximum = (int) cb.getValue();
            if (this.performanceTests.floodFillPerformanceTests(iterations, stonePercent, average, maximum)) {
                resultLabel.setText("Results succesfully written to file!");
            } else {
                resultLabel.setText("File not found");
            }
            box.getChildren().clear();
            box.getChildren().addAll(resultLabel, restart);
        });
        
        restart.setOnAction(e -> {
            performanceTestView(stage);
        });
        
        Scene scene = new Scene(border, 1200, 800);
        stage.setScene(scene);
        stage.show();
    }
    
    private void walkSpawnTestView(Stage stage) {
        Label digLabel = new Label("Random walk's dig percent");
        Spinner digSpinner = new Spinner((int) 1, (int) 100, (int) 45);
        Label averageLabel = new Label("How many runs to average");
        Spinner averageSpinner = new Spinner((int) 1, (int) 100, (int) 10);
        Label maximumLabel = new Label("Dungeon's maximum size in cells");
        ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList(100, 1024, 4096, 10000, 40000, 90000, 160000, 250000, 360000, 640000, 1000000));
        cb.setValue(10000);        
        Button beginButton = new Button("Begin performance test");
        
        VBox box = getVBox();
        
        BorderPane border = new BorderPane(); 
        border.setPadding(new Insets(10));
        
        Button mainMenu = new Button("Main menu");
        mainMenu.setOnAction(e -> {
            start(stage);
        });
        
        border.setTop(mainMenu);
        
        border.setCenter(box);
        
        
        box.getChildren().addAll(digLabel, digSpinner, averageLabel, averageSpinner, maximumLabel, cb, beginButton);
        Button restart = new Button("Back to test menu");
        
        beginButton.setOnAction(e -> {
            Label resultLabel = new Label();
            int digPercent = (int) digSpinner.getValue();
            int average = (int) averageSpinner.getValue();
            int maximum = (int) cb.getValue();
            if (this.performanceTests.randomWalkSpawnPerformanceTests(digPercent, average, maximum)) {
                resultLabel.setText("Results succesfully written to file!");
            } else {
                resultLabel.setText("File not found");
            }
            box.getChildren().clear();
            box.getChildren().addAll(resultLabel, restart);
        });
        
        restart.setOnAction(e -> {
            performanceTestView(stage);
        });
        
        Scene scene = new Scene(border, 1200, 800);
        stage.setScene(scene);
        stage.show();
    }
    
    private void walkDigTestView(Stage stage) {
        Label spawnLabel = new Label("Random walk's spawn chance for new walker");
        Spinner spawnSpinner = new Spinner((int) 1, (int) 100, (int) 2);
        Label averageLabel = new Label("How many runs to average");
        Spinner averageSpinner = new Spinner((int) 1, (int) 100, (int) 10);
        Label maximumLabel = new Label("Dungeon's maximum size in cells");
        ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList(100, 1024, 4096, 10000, 40000, 90000, 160000, 250000, 360000, 640000, 1000000));
        cb.setValue(10000);        
        Button beginButton = new Button("Begin performance test");
        
        VBox box = getVBox();
        
        BorderPane border = new BorderPane(); 
        border.setPadding(new Insets(10));
        
        Button mainMenu = new Button("Main menu");
        mainMenu.setOnAction(e -> {
            start(stage);
        });
        
        border.setTop(mainMenu);
        
        border.setCenter(box);
        
        
        box.getChildren().addAll(spawnLabel, spawnSpinner, averageLabel, averageSpinner, maximumLabel, cb, beginButton);
        Button restart = new Button("Back to test menu");
        
        beginButton.setOnAction(e -> {
            Label resultLabel = new Label();
            int spawnPercent = (int) spawnSpinner.getValue();
            int average = (int) averageSpinner.getValue();
            int maximum = (int) cb.getValue();
            if (this.performanceTests.randomWalkDigPerformanceTests(spawnPercent, average, maximum)) {
                resultLabel.setText("Results succesfully written to file!");
            } else {
                resultLabel.setText("File not found");
            }
            box.getChildren().clear();
            box.getChildren().addAll(resultLabel, restart);
        });
        
        restart.setOnAction(e -> {
            performanceTestView(stage);
        });
        
        Scene scene = new Scene(border, 1200, 800);
        stage.setScene(scene);
        stage.show();
    }
    
    private void drawDungeon(Dungeon d) {
        this.graphicsContext.clearRect(0, 0, this.canvasSize, this.canvasSize);
        this.graphicsContext.setFill(Color.WHITE);
        for (int y = 0; y < d.getY(); y++) {
            for (int x = 0; x < d.getX(); x++) {
                if (d.cellIsFloor(y, x)) {
                    this.graphicsContext.fillRect(x * this.resolutionX, y * this.resolutionY, this.resolutionX, this.resolutionY);
                }
            }
        }
        this.graphicsContext.setFill(Color.BLACK);
        for (int y = 0; y < d.getY(); y++) {
            for (int x = 0; x < d.getX(); x++) {
                if (d.cellIsStone(y, x)) {
                    this.graphicsContext.fillRect(x * this.resolutionX, y * this.resolutionY, this.resolutionX, this.resolutionY);
                }
            }
        }
        
        this.graphicsContext.setFill(Color.LIGHTBLUE);
        for (int y = 0; y < d.getY(); y++) {
            for (int x = 0; x < d.getX(); x++) {
                if (!d.cellIsStone(y, x) && !d.cellIsFloor(y, x)) {
                    this.graphicsContext.fillRect(x * this.resolutionX, y * this.resolutionY, this.resolutionX, this.resolutionY);
                }
            }
        }
    }
    
    private VBox getVBox() {
        VBox box = new VBox(2);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(20));
        return box;
    }
    
}
