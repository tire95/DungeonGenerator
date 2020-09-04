/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.Dungeon;
import java.util.Random;
/**
 *
 * @author timot
 */
public class CellularAutomaton {
    private int iterations;
    private Dungeon dungeon;
    private Dungeon helpDungeon;
    
    public CellularAutomaton(int iterations, Dungeon d, double stonePercentAtStart) {
        this.iterations = iterations;
        this.dungeon = d;
        this.helpDungeon = d;
        createWalls(stonePercentAtStart);
        runAutomaton();
    }
    
    private void createWalls(double stonePercentAtStart) {
        Random rand = new Random();
        int changedCells = 0;
        int dungeonHeigth = this.dungeon.getHeigth();
        int dungeonWidth = this.dungeon.getWidth();
        int totalCells = dungeonWidth * dungeonHeigth;
        while (100*changedCells / totalCells < stonePercentAtStart) {
            int cellX = rand.nextInt(dungeonWidth);
            int cellY = rand.nextInt(dungeonHeigth);
            if (this.dungeon.cellIsFloor(cellY, cellX)) {
                this.dungeon.changeCellToStone(cellY, cellX);
                changedCells++;
            }
        }
    }
    
    private void runAutomaton() {
        int dungeonHeigth = this.dungeon.getHeigth();
        int dungeonWidth = this.dungeon.getWidth();
        for (int i = 0; i < iterations; i++) {
            for (int y = 1; y < dungeonHeigth -1 ; y++) {
                for (int x = 1; x < dungeonWidth -1 ; x++) {
                    if (this.dungeon.checkNumberOfNeighbors(y, x) >= 5) {
                        this.helpDungeon.changeCellToStone(y, x);
                    } else {
                        this.helpDungeon.changeCellToFloor(y, x);
                    }
                }
            }
            this.dungeon = this.helpDungeon;
            System.out.println("Iteration " + i);
            this.dungeon.printDungeon();
            System.out.println("");
        }
    }
    
    
    
}
