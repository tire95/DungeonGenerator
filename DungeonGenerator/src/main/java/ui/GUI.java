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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    
    /**
     * Main method for launching JavaFX GUI
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) {        
        stage.setTitle("Dungeon generator");
        
        VBox box = getVBox();
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
        
        
        box.getChildren().addAll(cellular, walk, performanceTest);

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
        Button floodFill = new Button("Flood fill");
        
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
        Button floodFill = new Button("Flood fill");
        
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
        Button fillTest = new Button("Flood fill performance tests");
        VBox box = getVBox();

        box.getChildren().addAll(generationTest, fillTest);
        
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
        
        fillTest.setOnAction(e -> {
            fillTestView(stage);
        });
        
        border.setCenter(box);
        
        Scene scene = new Scene(border, 1200, 800);
        stage.setScene(scene);
        stage.show();
    }
    
    private void generationTestView(Stage stage) {
        Label averageLabel = new Label("How many runs to average");
        Spinner averageSpinner = new Spinner((int) 1, (int) 100, (int) 10);
        Label maximumLabel = new Label("Dungeon's maximum size in cells");
        ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList(100, 1000, 10000, 100000, 1000000, 10000000));
        cb.setValue(1000);
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
        
        
        box.getChildren().addAll(averageLabel, averageSpinner, maximumLabel, cb, beginButton);
        Button restart = new Button("Restart performance test");
        
        beginButton.setOnAction(e -> {
            Label resultLabel = new Label();
            try (FileWriter writer = new FileWriter(new File("Test Results/Generation performance test results.csv"), true)) {
                StringBuilder sb = new StringBuilder();
                sb.append("Run on: " + java.time.LocalDate.now() + " at: " + java.time.LocalTime.now());
                sb.append("\n");
                sb.append("Algorithm,");
                sb.append("Dimension,");
                sb.append("Average time,");
                sb.append("\n");
                int average = (int) averageSpinner.getValue();
                int maximum = (int) cb.getValue();
                int x = 1;
                int y = 10;
                while (true) {
                    x *= 10;
                    long automatonTime = 0;
                    long walkTimeSimple = 0;
                    long startTime = 0;
                    long endTime = 0;
                    for (int j = 0; j < average; j++) {
                        this.automaton = new CellularAutomaton(4, y, x, 50);
                        startTime = System.nanoTime();
                        this.automaton.initializeDungeon();
                        this.automaton.runAutomaton();
                        endTime = System.nanoTime();
                        automatonTime += (endTime - startTime);
                        this.walk = new RandomWalk(y, x, 4, 50, 0, false);
                        startTime = System.nanoTime();
                        this.walk.runRandomWalk();
                        endTime = System.nanoTime();
                        walkTimeSimple += (endTime - startTime);
                    }
                    sb.append("Cellular automaton,");
                    sb.append(x*y + ",");
                    sb.append(automatonTime / average + ",");
                    sb.append("\n");
                    sb.append("Random walk,");
                    sb.append(x*y + ",");
                    sb.append(walkTimeSimple / average + ",");
                    sb.append("\n");

                    if (x * y >= maximum) {
                        break;
                    }

                    y *= 10;
                    for (int j = 0; j < average; j++) {
                        this.automaton = new CellularAutomaton(4, y, x, 50);
                        startTime = System.nanoTime();
                        this.automaton.initializeDungeon();
                        this.automaton.runAutomaton();
                        endTime = System.nanoTime();
                        automatonTime += (endTime - startTime);
                        this.walk = new RandomWalk(y, x, 4, 50, 0, false);
                        startTime = System.nanoTime();
                        this.walk.runRandomWalk();
                        endTime = System.nanoTime();
                        walkTimeSimple += (endTime - startTime);
                    }
                    sb.append("Cellular automaton,");
                    sb.append(x*y + ",");
                    sb.append(automatonTime / average + ",");
                    sb.append("\n");
                    sb.append("Random walk,");
                    sb.append(x*y + ",");
                    sb.append(walkTimeSimple / average + ",");
                    sb.append("\n");
                
                    if (x * y >= maximum) {
                        break;
                    }
                }
                sb.append("\n");
                writer.write(sb.toString());
                resultLabel.setText("Results succesfully written to file!");
            } catch (IOException ex) {
                resultLabel.setText("Exception: " + ex);
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
        Dungeon[] dungeons = createDungeonsForPerformanceTests(); 
        
        Label averageLabel = new Label("How many runs to average");
        Spinner averageSpinner = new Spinner((int) 1, (int) 1000, (int) 100);
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
        
        
        box.getChildren().addAll(averageLabel, averageSpinner, beginButton);
        Button restart = new Button("Restart performance test");
        
        beginButton.setOnAction(e -> {
            Label resultLabel = new Label();
            try (FileWriter writer = new FileWriter(new File("Test Results/Flood fill performance test results.csv"), true)) {
                StringBuilder sb = new StringBuilder();
                sb.append("Run on: " + java.time.LocalDate.now() + " at: " + java.time.LocalTime.now());
                sb.append("\n");
                sb.append("Algorithm,");
                sb.append("Dungeon Nr.,");
                sb.append("Average time,");
                sb.append("\n");
                int average = (int) averageSpinner.getValue();
                int i = 1;
                for (Dungeon d : dungeons) {
                    long forestFireTime = 0;
                    long scanFillTime = 0;
                    long startTime = 0;
                    long endTime = 0;
                    for (int j = 0; j < average; j++) {
                        FloodFill forestFire = new FloodFill(d, 0);
                        startTime = System.nanoTime();
                        forestFire.findLargestConnectedArea();
                        endTime = System.nanoTime();
                        forestFireTime += (endTime - startTime);
                        FloodFill scanFill = new FloodFill(d, 1);
                        startTime = System.nanoTime();
                        scanFill.findLargestConnectedArea();
                        endTime = System.nanoTime();
                        scanFillTime += (endTime - startTime);
                    }
                    
                    sb.append("Forest fire,");
                    sb.append(i + ",");
                    sb.append(forestFireTime / average + ",");
                    sb.append("\n");
                    sb.append("Scan fill,");
                    sb.append(i + ",");
                    sb.append(scanFillTime / average + ",");
                    sb.append("\n");

                    i++;
                }
                sb.append("\n");
                writer.write(sb.toString());
                resultLabel.setText("Results succesfully written to file!");
            } catch (IOException ex) {
                resultLabel.setText("Exception: " + ex);
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
    
    private Dungeon[] createDungeonsForPerformanceTests() {
        Dungeon dungeon1 = new Dungeon(10, 10);
        for (int i = 0; i < 10; i++) {
            dungeon1.changeCellToStone(1, i);
        }
        
        
        Dungeon dungeon2 = new Dungeon(16, 16);
        for (int i = 0; i < 16; i++) {
            dungeon2.changeCellToStone(3, i);
            dungeon2.changeCellToStone(6, i);
            dungeon2.changeCellToStone(i, 10);
        }
        
        
        Dungeon dungeon3 = new Dungeon(100, 150);
        for (int i = 0; i < 30; i++) {
            dungeon3.changeCellToStone(10, i);
            dungeon3.changeCellToStone(90, 149 - i);
        }
        for (int i = 0; i < 10; i++) {
            dungeon3.changeCellToStone(i, 29);
            dungeon3.changeCellToStone(99 - i, 120);
        }
        
        
        Dungeon dungeon4 = new Dungeon(500, 500);
        for (int i = 0; i < 360; i++) {
            int x1 = (int) (100 * Math.cos(i * Math.PI / 180));
            int y1 = (int) (100 * Math.sin(i * Math.PI / 180));
            dungeon4.changeCellToStone(250 + y1, 250 + x1);
        }
        
        
        Dungeon dungeon5 = new Dungeon(500, 500);
        for (int i = 150; i <= 350; i++) {
            for (int j = -1; j <= 1; j++) {
                dungeon5.changeCellToStone(150 + j, i);
                dungeon5.changeCellToStone(350 + j, i);
                dungeon5.changeCellToStone(i, 150 + j);
                dungeon5.changeCellToStone(i, 350 + j);
            }
        }        
        for (int i = 100; i <= 300; i++) {
            for (int j = -1; j <= 1; j++) {
                dungeon5.changeCellToStone(100 + j, i);
                dungeon5.changeCellToStone(300 + j, i);
                dungeon5.changeCellToStone(i, 100 + j);
                dungeon5.changeCellToStone(i, 300 + j);
            }
        }
        for (int i = 200; i <= 400; i++) {
            for (int j = -1; j <= 1; j++) {
                dungeon5.changeCellToStone(200 + j, i);
                dungeon5.changeCellToStone(400 + j, i);
                dungeon5.changeCellToStone(i, 200 + j);
                dungeon5.changeCellToStone(i, 400 + j);
            }
        }
        
        
        Dungeon dungeon6 = new Dungeon(800, 800);
        for (int i = 100; i <= 700; i++) {
            for (int j = -1; j <= 1; j++) {
                dungeon6.changeCellToStone(100 + j, i);
                dungeon6.changeCellToStone(700 + j, i);
                dungeon6.changeCellToStone(i, 100 + j);
                dungeon6.changeCellToStone(i, 700 + j);
            }
        }
        for (int i = 300; i <= 500; i++) {
            for (int j = -1; j <= 1; j++) {
                dungeon6.changeCellToStone(300 + j, i);
                dungeon6.changeCellToStone(500 + j, i);
                dungeon6.changeCellToStone(i, 300 + j);
                dungeon6.changeCellToStone(i, 500 + j);
            }
        }
        for (int i = 380; i <= 420; i++) {
            for (int j = -1; j <= 1; j++) {
                dungeon6.changeCellToStone(380 + j, i);
                dungeon6.changeCellToStone(420 + j, i);
                dungeon6.changeCellToStone(i, 380 + j);
                dungeon6.changeCellToStone(i, 420 + j);
            }
        }
        
        Dungeon[] dungeons = {dungeon1, dungeon2, dungeon3, dungeon4, dungeon5, dungeon6};
        
        return dungeons;
    }
    
    private VBox getVBox() {
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(10));
        return box;
    }
}
