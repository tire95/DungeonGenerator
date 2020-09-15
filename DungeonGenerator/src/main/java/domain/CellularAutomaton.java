/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

import java.util.Random;

/**
 * Dungeon generation algorithm
 * @author timot
 */
public class CellularAutomaton {
    private int iterations;
    private Dungeon dungeon;
    private int stonePercentAtStart;
    
    /**
     * Constructor
     * @param iterations how many times the automaton runs
     * @param dungeonY dungeon's height
     * @param dungeonX dungeon's width
     * @param stonePercentAtStart percentage of stone cells at the start
     */
    public CellularAutomaton(int iterations, int dungeonY, int dungeonX, int stonePercentAtStart) {
        this.iterations = iterations;
        this.dungeon = new Dungeon(dungeonY, dungeonX);
        this.stonePercentAtStart = stonePercentAtStart;
    }
    
    /**
     * Initializes dungeon by changing random cells to stone until a wanted stone percent is achieved
     */
    public void initializeDungeon() {
        Random rand = new Random();
        int changedCells = 0;
        int dungeonHeigth = this.dungeon.getY();
        int dungeonWidth = this.dungeon.getX();
        int totalCells = dungeonWidth * dungeonHeigth;
        while (100 * changedCells / totalCells < this.stonePercentAtStart) {
            int cellX = rand.nextInt(dungeonWidth);
            int cellY = rand.nextInt(dungeonHeigth);
            if (this.dungeon.cellIsFloor(cellY, cellX)) {
                this.dungeon.changeCellToStone(cellY, cellX);
                changedCells++;
            }
        }
    }
    
    /**
     * Runs the algorithm for dungeon generation
     */
    public void runAutomaton() {
        int dungeonY = this.dungeon.getY();
        int dungeonX = this.dungeon.getX();
        int[][] tempGrid = new int[dungeonY][dungeonX];
        for (int i = 0; i < this.iterations; i++) {
            for (int y = 0; y < dungeonY; y++) {
                for (int x = 0; x < dungeonX; x++) {
                    // if enough of neighboring cells are stone, change cell to stone, otherwise change cell to floor
                    tempGrid[y][x] = ((this.dungeon.checkNumberOfStoneNeighbors(y, x) >= 5) ? 1 : 0);
                }
            }
            this.dungeon.setGrid(tempGrid);
            tempGrid = new int[dungeonY][dungeonX];

        }


    }
    
    public Dungeon getDungeon() {
        return this.dungeon;
    }
    
    public int getIterations() {
        return this.iterations;
    }
    
    public int getStonePercent() {
        return this.stonePercentAtStart;
    }
    
    /**
     * Resets the dungeon for a new run
     */
    public void reset() {
        this.dungeon = new Dungeon(this.dungeon.getY(), this.dungeon.getX());
    }
    
    
    
}
