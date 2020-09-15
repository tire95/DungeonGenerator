/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

/**
 * Class responsible for the logic on dungeon level.
 * The state of a cell in the grid is represented as an integer; 0 means floor (walkable), 1 means stone (non-walkable).
 * @author timot
 */
public class Dungeon {
    private int x;
    private int y;
    private int[][] grid;
    
    /**
     * Constructor for the dungeon
     * @param y dungeon's height
     * @param x dungeon's width
     */
    public Dungeon(int y, int x) {
        this.x = x;
        this.y = y;
        this.grid = new int[y][x];
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    /**
     * Returns the number of neighboring cells with the state stone (i.e. 1).
     * For a cell at an edge/corner, every "cell" that's outside of the grid's bounds is counted as a stone cell; otherwise cellular automaton wouldn't work properly
     * @param y the y coordinate of the given cell
     * @param x the x coordinate of the given cell
     * @return the number of stone neighbors for the given cell
     */
    public int checkNumberOfStoneNeighbors(int yCoord, int xCoord) {
        int neighbors = 0;
        for (int i = yCoord - 1; i <= yCoord + 1; i++) {
            for (int j = xCoord - 1; j <= xCoord + 1; j++) {
                
                // if a "cell" is outside the grid's bounds, count it as "stone"
                if (i < 0 || i >= this.y || j < 0 || j >= this.x) {
                    neighbors++;
                    
                } else if ((i != yCoord) || (j != xCoord)) {
                    neighbors += this.grid[i][j];
                }
            }
        }
        return neighbors;
    }
    
    /**
     * Returns the number of adjacent cells with the state stone (i.e. 1).
     * Adjacent cells are the ones directly above, below, and to the sides.
     * This method is used in dungeon clean up
     * @param y the y coordinate of the given cell
     * @param x the x coordinate of the given cell
     * @return the number of adjacent stone neighbors for the given cell
     */
    private int checkNumberOfAdjacentStoneNeighbors(int yCoord, int xCoord) {
        return this.grid[yCoord - 1][xCoord] + this.grid[yCoord + 1][xCoord] + this.grid[yCoord][xCoord + 1] + this.grid[yCoord][xCoord - 1];
    }
    
    public void changeCellToStone(int y, int x) {
        this.grid[y][x] = 1;
    }
    
    public void changeCellToFloor(int y, int x) {
        this.grid[y][x] = 0;
    }
    
    public boolean cellIsFloor(int y, int x) {
        return this.grid[y][x] == 0;
    }
    
    public boolean cellIsStone(int y, int x) {
        return this.grid[y][x] == 1;
    }
    
    public void setGrid(int[][] newGrid) {
        this.grid = newGrid;
    }
    
    public int[][] getGrid() {
        return this.grid;
    }
    
     /**
     * Cleans the dungeon by removing singular stone cells and stops once there's nothing to clean
     */
    public void cleanUp() {
        boolean changed = false;
        outerloop:
        while (true) {
            for (int yCoord = 1; yCoord < this.y - 1; yCoord++) {
                for (int xCoord = 1; xCoord < this.x - 1; xCoord++) {
                    if (cellIsStone(yCoord, xCoord)) {
                        if (checkNumberOfAdjacentStoneNeighbors(yCoord,xCoord) < 2) {
                            this.grid[yCoord][xCoord] = 0;
                            changed = true;
                        }
                    }
                }
            }
            if (changed) {
                changed = false;
            } else {
                break outerloop;
            }
        }
    }
    
    public void setCell(int y, int x, int i) {
        this.grid[y][x] = i;
    }
    
}
