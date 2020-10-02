/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

import java.util.concurrent.ThreadLocalRandom;
import util.CellQueue;

/**
 * Dungeon generation algorithm
 * @author timot
 */
public class RandomWalk {
    private CellQueue walkers;
    private Dungeon dungeon;
    private int spawnChance;
    private int digPercent;
    private int turnChance;
    private boolean useComplexWalk;
    
    /**
     * Constructor
     * @param y dungeon's height
     * @param x dungeon's width
     * @param spawnChance chance of spawning a new walker (percentage)
     * @param digPercent percentage of stone to be digged
     * @param turnChance chance of walker turning 90 degrees (percentage)
     * @param complexWalk true if complexWalk is used, false is simpleWalk is used
     */
    public RandomWalk(int y, int x, int spawnChance, int digPercent, int turnChance, boolean complexWalk) {
        this.walkers = new CellQueue(16);
        this.dungeon = new Dungeon(y, x);
        this.spawnChance = spawnChance;
        this.digPercent = digPercent;
        this.turnChance = turnChance;
        this.useComplexWalk = complexWalk;
        initDungeon();
    }
    
    /**
     * Runs the algorithm for dungeon generation
     */
    public void runSimpleWalk() {
        int dungeonX = this.dungeon.getX();
        int dungeonY = this.dungeon.getY();
        int totalCells = dungeonX * dungeonY;
        int changedCells = 0;
        this.walkers.enqueue(new Cell(dungeonY / 2, dungeonX / 2));
        
        while ((100 * changedCells / totalCells) < this.digPercent) {
            Cell nextWalker = this.walkers.dequeue();
            nextWalker.simpleWalk();
            int currentX = nextWalker.getCurrentX();
            int currentY = nextWalker.getCurrentY();
            
            // check if the walker is still in dungeon
            if (this.dungeon.cellIsInDungeon(currentY, currentX)) {
                if (this.dungeon.cellIsStone(currentY, currentX)) {
                    this.dungeon.changeCellToFloor(currentY, currentX);
                    changedCells++;
                }
                
                // if the walker has stone cells next to it or otherwise the queue is empty, keep the walker alive
                if ((this.dungeon.checkNumberOfStoneNeighbors(currentY, currentX) > 0) || this.walkers.isEmpty()) {
                    this.walkers.enqueue(nextWalker);
                    
                    // randomly spawn a new walker where the current walker is
                    if (ThreadLocalRandom.current().nextInt(1, 101) < this.spawnChance) {
                        this.walkers.enqueue(new Cell(currentY, currentX));
                    }
                }
            }
            
            // if the last walker walked out of the dungeon, spawn a new walker
            if (this.walkers.isEmpty()) {
                this.walkers.enqueue(new Cell(dungeonY / 2, dungeonX / 2));
            }
        }
    }
    
    /**
     * Runs the algorithm for dungeon generation
     */
    public void runComplexWalk() {
        int dungeonX = this.dungeon.getX();
        int dungeonY = this.dungeon.getY();
        int totalCells = dungeonX * dungeonY;
        int changedCells = 0;
        this.walkers.enqueue(new Cell(dungeonY / 2, dungeonX / 2, turnChance));
        
        while ((100 * changedCells / totalCells) < this.digPercent) {
            Cell nextWalker = this.walkers.dequeue();
            nextWalker.complexWalk();
            int currentX = nextWalker.getCurrentX();
            int currentY = nextWalker.getCurrentY();
            
            // check if the walker is still in dungeon
            if (this.dungeon.cellIsInDungeon(currentY, currentX)) {
                if (this.dungeon.cellIsStone(currentY, currentX)) {
                    this.dungeon.changeCellToFloor(currentY, currentX);
                    changedCells++;
                }
                
                // if the walker has stone cells next to it or otherwise the queue is empty, keep the walker alive
                if ((this.dungeon.checkNumberOfStoneNeighbors(currentY, currentX) > 0) || this.walkers.isEmpty()) {
                    this.walkers.enqueue(nextWalker);
                    
                    // randomly spawn a new walker where the current walker is
                    if (ThreadLocalRandom.current().nextInt(1, 101) < this.spawnChance) {
                        this.walkers.enqueue(new Cell(currentY, currentX, turnChance));
                    }
                }
            }
            
            // if the last walker walked out of the dungeon, spawn a new walker
            if (this.walkers.isEmpty()) {
                this.walkers.enqueue(new Cell(dungeonY / 2, dungeonX / 2, turnChance));
            }
        }
    }
    
    /**
     * Returns dungeon
     * @return dungeon
     */
    public Dungeon getDungeon() {
        return this.dungeon;
    }
    
    /**
     * Initializes the dungeon by changing cells to stone and emptying the queue in case some walkers remain after last run
     */
    private void initDungeon() {
        this.walkers = new CellQueue(16);
        for (int y = 0; y < this.dungeon.getY(); y++) {
            for (int x = 0; x < this.dungeon.getX(); x++) {
                this.dungeon.changeCellToStone(y, x);
            }
        }
    }
    
    /**
     * Check if this random walk uses complex walk
     * @return true if complex walk is used
     */
    public boolean getUseComplexWalk() {
        return this.useComplexWalk;
    }
    
}
