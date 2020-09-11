/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Dungeon generation algorithm
 * @author timot
 */
public class RandomWalk {
    private Queue<Walker> walkers;
    private Dungeon dungeon;
    private int spawnChance;
    private int digPercent;
    private int turnChance;
    
    /**
     * Constructor
     * @param y dungeon's height
     * @param x dungeon's width
     * @param spawnChance chance of spawning a new walker (percentage)
     * @param digPercent percentage of stone to be digged
     * @param turnChance chance of walker turning 90 degrees (percentage)
     */
    public RandomWalk(int y, int x, int spawnChance, int digPercent, int turnChance) {
        this.walkers = new ArrayDeque<Walker>();
        this.dungeon = new Dungeon(y, x);
        this.spawnChance = spawnChance;
        this.digPercent = digPercent;
        this.turnChance = turnChance;
        initDungeon();
    }
    
    /**
     * Runs the algorithm for dungeon generation
     */
    public void runRandomWalk() {
        int dungeonX = this.dungeon.getX();
        int dungeonY = this.dungeon.getY();
        int totalCells = dungeonX * dungeonY;
        int changedCells = 0;
        this.walkers.add(new Walker(dungeonY / 2, dungeonX / 2, turnChance));
        
        while ((100 * changedCells / totalCells) < this.digPercent) {
            Walker nextWalker = this.walkers.poll();
//            nextWalker.simpleWalk();
            nextWalker.walk();
            int currentX = nextWalker.getCurrentX();
            int currentY = nextWalker.getCurrentY();
            
            // check if the walker is still in dungeon
            if (currentX < dungeonX && currentX >= 0 && currentY < dungeonY && currentY >= 0) {
                if (!this.dungeon.cellIsFloor(currentY, currentX)) {
                    this.dungeon.changeCellToFloor(currentY, currentX);
                    changedCells++;
                }
                
                // if the walker has stone cells next to it or otherwise the queue is empty, keep the walker alive
                if ((this.dungeon.checkNumberOfStoneNeighbors(currentY, currentX) > 0) || this.walkers.isEmpty()) {
                    this.walkers.add(nextWalker);
                    
                    // randomly spawn a new walker where the current walker is
                    if (ThreadLocalRandom.current().nextInt(1, 101) < this.spawnChance) {
                        this.walkers.add(new Walker(currentY, currentX, turnChance));
                    }
                }
            }
            
            // if the last walker walked out of the dungeon, spawn a new walker
            if (this.walkers.isEmpty()) {
                this.walkers.add(new Walker(dungeonY / 2, dungeonX / 2, turnChance));
            }
        }
    }
    
    public Dungeon getDungeon() {
        return this.dungeon;
    }
    
    /**
     * Initializes the dungeon by changing cells to stone and emptying the queue in case some walkers remain after last run
     */
    public void initDungeon() {
        this.walkers.clear();
        for (int y = 0; y < this.dungeon.getY(); y++) {
            for (int x = 0; x < this.dungeon.getX(); x++) {
                this.dungeon.changeCellToStone(y, x);
            }
        }
    }
    
    public Queue<Walker> getWalkers() {
        return this.walkers;
    }
    
}
