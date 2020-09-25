/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

import java.util.concurrent.ThreadLocalRandom;

/**
 * An entity used in random complexWalk and flood fill algorithms
 * @author timot
 */
public class Cell {
    private int currentX;
    private int currentY;
    private int previousX;
    private int previousY;
    private int turnChance;
    
    /**
     * Constructor for complex random complexWalk
     * @param x current x coordinate of the walker
     * @param y current y coordinate of the walker
     * @param turnChance chance of walker turning 90 degrees (percentage)
     */
    public Cell(int y, int x, int turnChance) {
        this.currentX = this.previousX = x;
        this.currentY = this.previousY = y;
        this.turnChance = turnChance;
    }

    /**
     * Constructor for simple random complexWalk and flood fill
     * @param x current x coordinate of the walker
     * @param y current y coordinate of the walker
     */    
    public Cell(int y, int x) {
        this.currentX = x;
        this.currentY = y;
    }

    /**
     * Return current x coordinate
     * @return x coordinate
     */
    public int getCurrentX() {
        return currentX;
    }

    /**
     * Return current y coordinate
     * @return y coordinate
     */
    public int getCurrentY() {
        return currentY;
    }
    
    /**
     * A walk algorithm that is uniformly distributed
     */
    public void simpleWalk() {
        int direction = ThreadLocalRandom.current().nextInt(1, 5);
        switch (direction) {
            case 1:
                this.currentX++;
                break;
            case 2:
                this.currentX--;
                break;
            case 3:
                this.currentY++;
                break;
            default:
                this.currentY--;
                break;
        }
    }
    
    /**
     * A complexWalk algorithm that depends on the turn chance
     */
    public void complexWalk() {
        boolean moveInPositive;
        
        // check whether to turn or not
        if (ThreadLocalRandom.current().nextInt(1, 101) > this.turnChance) {
            
            // no turning; check if last move was in x direction
            if (this.currentX != this.previousX) {
                
                // check if last move was in positive direction
                moveInPositive = (this.currentX > this.previousX);
                this.previousX = this.currentX;
                moveHorizontal(moveInPositive);
                
            } else {
                // last moved in y direction; check if last move was in positive direction
                moveInPositive = (this.currentY > this.previousY);
                this.previousY = this.currentY;
                moveVertical(moveInPositive);
            }
        } else {
            // turn; check if next move is in positive
            moveInPositive = ThreadLocalRandom.current().nextBoolean();
            
            // if last move was in x direction, this time move in y direction (i.e. turn 90 degrees)
            if (this.currentX != this.previousX) {
                this.previousY = this.currentY;
                moveVertical(moveInPositive);
            } else {
                
                //turn 90 degrees to x direction
                this.previousX = this.currentX;
                moveHorizontal(moveInPositive);
            }
 
        }
    }
    
    private void moveHorizontal(boolean moveInPositive) {
        this.currentX = moveInPositive ? this.currentX + 1 : this.currentX - 1;
    }
    
    private void moveVertical(boolean moveInPositive) {
        this.currentY = moveInPositive ? this.currentY + 1 : this.currentY - 1;
    }

    
}
