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
    
    public CellularAutomaton(int iterations, int heigth, int width) {
        this.iterations = iterations;
        this.dungeon = new Dungeon(heigth, width);
    }
    
    public void createWalls(double stonePercentAtStart) {
        Random rand = new Random();
        int changedCells = 0;
        int dungeonHeigth = this.dungeon.getHeigth();
        int dungeonWidth = this.dungeon.getWidth();
        int totalCells = dungeonWidth * dungeonHeigth;
        while (100 * changedCells / totalCells < stonePercentAtStart) {
            int cellX = rand.nextInt(dungeonWidth);
            int cellY = rand.nextInt(dungeonHeigth);
            if (this.dungeon.cellIsFloor(cellY, cellX)) {
                this.dungeon.changeCellToStone(cellY, cellX);
                changedCells++;
            }
        }
    }
    
    public void runAutomaton() {
        int dungeonHeigth = this.dungeon.getHeigth();
        int dungeonWidth = this.dungeon.getWidth();
        int[][] tempGrid = new int[dungeonHeigth][dungeonWidth];
        for (int y = 0; y < dungeonHeigth ; y++) {
            for (int x = 0; x < dungeonWidth ; x++) {
                tempGrid[y][x] = ((this.dungeon.checkNumberOfNeighbors(y, x) >= 5) ? 1 : 0);
            }
        }
        this.dungeon.setGrid(tempGrid);
    }
    
    public Dungeon getDungeon() {
        return this.dungeon;
    }
    
    public int getIterations() {
        return this.iterations;
    }
    
    
    
}
