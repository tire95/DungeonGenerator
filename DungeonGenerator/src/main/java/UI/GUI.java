/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import domain.CellularAutomaton;
import domain.Dungeon;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
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
        
        CellularAutomaton c = new CellularAutomaton(20, 100, 100);
        c.createWalls(60);
        
        stage.setTitle("Dungeon generator");
        
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(10));
        Button startAutomaton = new Button("Start cellular automaton");
        
        GridPane grid = new GridPane();
                
        drawDungeon(grid, c);
        
        grid.setAlignment(Pos.CENTER);
        
        box.getChildren().addAll(startAutomaton, grid);
        
        startAutomaton.setOnAction(e -> {
//            int iterations = c.getIterations();
//            for (int i = 0; i < iterations; i++) {
//                c.runAutomaton();
//                System.out.println("Iteration " + i);
//                c.getDungeon().printDungeon();

////                drawDungeon(grid, c);
////                System.out.println("Iteration: " + i);
////                try {
////                    Thread.sleep(500);
////                } catch(InterruptedException ex) {
////                    Thread.currentThread().interrupt();
////                }
//            }
            c.runAutomaton();
            drawDungeon(grid, c);
        });

        Scene scene = new Scene(box, 720, 720);
        stage.setScene(scene);
        stage.show();
        
    }
    
    private void drawDungeon(GridPane g, CellularAutomaton c) {
        Dungeon d = c.getDungeon();
        g.getChildren().clear();
        for (int y = 0; y < d.getHeigth(); y++) {
            for (int x = 0; x < d.getWidth(); x++) {
                if (!d.cellIsFloor(y, x)) {
                    g.add(new Rectangle(10,10), y, x);
                } else {
                    g.add(new Rectangle(10,10,Color.WHITE), y, x);
                }
            }
        }
    }
}
