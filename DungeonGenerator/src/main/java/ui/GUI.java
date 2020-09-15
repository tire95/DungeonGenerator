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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author timot
 */
public class GUI extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) {        
        stage.setTitle("Dungeon generator");
        
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        Button cellular = new Button("Cellular automaton");
        Button walk = new Button("Random walk");
        
                
        cellular.setOnAction(e -> {
            automatonView(stage);
        });
        
        walk.setOnAction(e -> {
            walkView(stage);
        });
        
        hbox.getChildren().addAll(cellular, walk);

        

        Scene scene = new Scene(hbox, 1600, 800);
        stage.setScene(scene);
        stage.show();
        
    }
    
    private void automatonView(Stage stage) {
        CellularAutomaton c = new CellularAutomaton(3, 200, 100, 55);
        c.initializeDungeon();
        FloodFill f = new FloodFill(2);
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(10));
        Button startAutomaton = new Button("Start cellular automaton");
        Button resetAutomaton = new Button("Reset automaton");
        Button cleanUp = new Button("Clean up");
        Button floodFill = new Button("Flood fill");
        
        GridPane grid = new GridPane();
                
        drawDungeon(grid, c.getDungeon());
        
        grid.setAlignment(Pos.CENTER);
        
        box.getChildren().addAll(startAutomaton, cleanUp, floodFill, resetAutomaton, grid);
        
        startAutomaton.setOnAction(e -> {
            c.runAutomaton();
            drawDungeon(grid, c.getDungeon());
        });
        
        resetAutomaton.setOnAction(e -> {
            c.reset();
            c.initializeDungeon();
            drawDungeon(grid, c.getDungeon());
        });
                
        cleanUp.setOnAction(e -> {
            c.getDungeon().cleanUp();
            drawDungeon(grid, c.getDungeon());
        });
        
        floodFill.setOnAction(e -> {
            f.setDungeon(c.getDungeon());
            outerloop:
            for (int y = 0; y < 200; y++) {
                for (int x = 0; x < 100; x++) {
                    if (f.getDungeon().cellIsFloor(y, x)) {
                        f.startFloodFill(y, x);
                        break outerloop;
                    }
                }
            }
            drawDungeon(grid, f.getDungeon());
        });
        
        Scene scene = new Scene(box, 1600, 800);
        stage.setScene(scene);
        stage.show();
    }
    
    private void walkView(Stage stage) {
        RandomWalk w = new RandomWalk(200, 100, 10, 30, 20);
        FloodFill f = new FloodFill(2);
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(10));
        Button startRandom = new Button("Start random walk");
        Button resetRandom = new Button("Reset random walk");
        Button cleanUp = new Button("Clean up");
        Button floodFill = new Button("Flood fill");
        
        GridPane grid = new GridPane();
                
        drawDungeon(grid, w.getDungeon());
        
        grid.setAlignment(Pos.CENTER);
        
        box.getChildren().addAll(startRandom, cleanUp, floodFill, resetRandom, grid);
        
        startRandom.setOnAction(e -> {
            w.runRandomWalk();
            drawDungeon(grid, w.getDungeon());
        });
        
        resetRandom.setOnAction(e -> {
            w.initDungeon();
            drawDungeon(grid, w.getDungeon());
        });
        
        cleanUp.setOnAction(e -> {
            w.getDungeon().cleanUp();
            drawDungeon(grid, w.getDungeon());
        });
        
        floodFill.setOnAction(e -> {
            f.setDungeon(w.getDungeon());
            for (int y = 0; y < 200; y++) {
                for (int x = 0; x < 100; x++) {
                    if (f.getDungeon().cellIsFloor(y, x)) {
                        f.startFloodFill(y, x);
                        break;
                    }
                }
            }
            drawDungeon(grid, f.getDungeon());
        });
        
        Scene scene = new Scene(box, 1600, 800);
        stage.setScene(scene);
        stage.show();
    }
    
    private void drawDungeon(GridPane g, Dungeon d) {
        g.getChildren().clear();
        for (int y = 0; y < d.getY(); y++) {
            for (int x = 0; x < d.getX(); x++) {
                if (d.cellIsFloor(y, x)) {
                    g.add(new Rectangle(5, 5, Color.WHITE), y, x);
                } else if (d.cellIsStone(y, x)) {
                    g.add(new Rectangle(5, 5, Color.BLACK), y, x);
                } else {
                    g.add(new Rectangle(5, 5, Color.LIGHTBLUE), y, x);
                }
            }
        }
    }
}
