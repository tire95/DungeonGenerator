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
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
        CellularAutomaton c = new CellularAutomaton(3, 250, 250, 50);
        c.initializeDungeon();
        FloodFill f = new FloodFill(2);
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(10));
        Button startAutomaton = new Button("Start cellular automaton");
        Button resetAutomaton = new Button("Reset automaton");
        Button cleanUp = new Button("Clean up");
        Button floodFill = new Button("Flood fill");
        
        Canvas canvas = new Canvas(500, 500);
        GraphicsContext gc = canvas.getGraphicsContext2D();
                        
        drawDungeon(gc, c.getDungeon());
                
        box.getChildren().addAll(startAutomaton, cleanUp, floodFill, resetAutomaton, canvas);
        
        startAutomaton.setOnAction(e -> {
            c.runAutomaton();
            drawDungeon(gc, c.getDungeon());
        });
        
        resetAutomaton.setOnAction(e -> {
            c.reset();
            c.initializeDungeon();
            drawDungeon(gc, c.getDungeon());
        });
                
        cleanUp.setOnAction(e -> {
            c.getDungeon().cleanUp();
            drawDungeon(gc, c.getDungeon());
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
            drawDungeon(gc, f.getDungeon());
        });
        
        Scene scene = new Scene(box, 1600, 800);
        stage.setScene(scene);
        stage.show();
    }
    
    private void walkView(Stage stage) {
        RandomWalk w = new RandomWalk(250, 250, 10, 30, 20);
        FloodFill f = new FloodFill(2);
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(10));
        Button startRandom = new Button("Start random walk");
        Button resetRandom = new Button("Reset random walk");
        Button cleanUp = new Button("Clean up");
        Button floodFill = new Button("Flood fill");
        
        Canvas canvas = new Canvas(500, 500);
        GraphicsContext gc = canvas.getGraphicsContext2D();
                        
        drawDungeon(gc, w.getDungeon());
                
        box.getChildren().addAll(startRandom, cleanUp, floodFill, resetRandom, canvas);
        
        startRandom.setOnAction(e -> {
            w.runRandomWalk();
            drawDungeon(gc, w.getDungeon());
        });
        
        resetRandom.setOnAction(e -> {
            w.initDungeon();
            drawDungeon(gc, w.getDungeon());
        });
        
        cleanUp.setOnAction(e -> {
            w.getDungeon().cleanUp();
            drawDungeon(gc, w.getDungeon());
        });
        
        floodFill.setOnAction(e -> {
            f.setDungeon(w.getDungeon());
            outerloop:
            for (int y = 0; y < 200; y++) {
                for (int x = 0; x < 100; x++) {
                    if (f.getDungeon().cellIsFloor(y, x)) {
                        f.startFloodFill(y, x);
                        break outerloop;
                    }
                }
            }
            drawDungeon(gc, f.getDungeon());
        });
        
        Scene scene = new Scene(box, 1600, 800);
        stage.setScene(scene);
        stage.show();
    }
    
    private void drawDungeon(GraphicsContext gc, Dungeon d) {
        gc.setFill(Color.WHITE);
        for (int y = 0; y < d.getY(); y++) {
            for (int x = 0; x < d.getX(); x++) {
                if (d.cellIsFloor(y, x)) {
                    gc.fillRect(y*2, x*2, 2, 2);
                }
            }
        }
        gc.setFill(Color.BLACK);
        for (int y = 0; y < d.getY(); y++) {
            for (int x = 0; x < d.getX(); x++) {
                if (d.cellIsStone(y, x)) {
                    gc.fillRect(y*2, x*2, 2, 2);
                }
            }
        }
        
        gc.setFill(Color.LIGHTBLUE);
        for (int y = 0; y < d.getY(); y++) {
            for (int x = 0; x < d.getX(); x++) {
                if (!d.cellIsStone(y, x) && !d.cellIsFloor(y, x)) {
                    gc.fillRect(y*2, x*2, 2, 2);
                }
            }
        }
    }
}
