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
    
    /**
     * Return x i.e. width of dungeon
     * @return x
     */
    public int getX() {
        return this.x;
    }
    
    /**
     * Return y i.e. height of dungeon
     * @return y
     */
    public int getY() {
        return this.y;
    }
    
    /**
     * Returns the number of neighboring cells with the state stone (i.e. 1).
     * For a cell at an edge/corner, every "cell" that's outside of the grid's bounds is counted as a stone cell; otherwise cellular automaton wouldn't work properly
     * @param yCoord the y coordinate of the given cell
     * @param xCoord the x coordinate of the given cell
     * @return the number of stone neighbors for the given cell
     */
    public int checkNumberOfStoneNeighbors(int yCoord, int xCoord) {
        int neighbors = 0;
        for (int i = yCoord - 1; i <= yCoord + 1; i++) {
            for (int j = xCoord - 1; j <= xCoord + 1; j++) {
                
                // if a "cell" is outside the grid's bounds, count it as "stone"
                if (!cellIsInDungeon(i, j)) {
                    neighbors++;
                } else if (cellIsStone(i, j)) {
                    neighbors++;
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
        int neighbors = 0;
        if (cellIsStone(yCoord - 1, xCoord)) {
            neighbors++;
        }
        if (cellIsStone(yCoord + 1, xCoord)) {
            neighbors++;
        }
        if (cellIsStone(yCoord, xCoord - 1)) {
            neighbors++;
        }
        if (cellIsStone(yCoord, xCoord + 1)) {
            neighbors++;
        }
        return neighbors;
    }
    
    /**
     * Change cell's state to stone i.e. 1
     * @param y y coordinate of cell
     * @param x x coordinate of cell
     */
    public void changeCellToStone(int y, int x) {
        this.grid[y][x] = 1;
    }
    
    /**
     * Change cell's state to floor i.e. 0
     * @param y y coordinate of cell
     * @param x x coordinate of cell
     */
    public void changeCellToFloor(int y, int x) {
        this.grid[y][x] = 0;
    }
    
    /**
     * Checks if given cell is floor
     * @param y y coordinate of cell
     * @param x x coordinate of cell
     * @return true if cell is floor
     */
    public boolean cellIsFloor(int y, int x) {
        return this.grid[y][x] == 0;
    }
    
    /**
     * Checks if given cell is stone
     * @param y y coordinate of cell
     * @param x x coordinate of cell
     * @return true if cell is stone
     */
    public boolean cellIsStone(int y, int x) {
        return this.grid[y][x] == 1;
    }
    
    /**
     * Sets dungeon grid to new grid
     * @param newGrid new grid for dungeon
     */
    public void setGrid(int[][] newGrid) {
        this.grid = newGrid;
    }
    
    /**
     * Returns dungeon grid
     * @return grid
     */
    public int[][] getGrid() {
        return this.grid;
    }
    
    /**
     * Cleans the dungeon by removing singular stone cells and stops once there's nothing to clean
     */
    public void cleanUp() {
        boolean changed = true;
        outerloop:
        while (changed) {
            changed = false;
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
        }
    }
    
    /**
     * Sets cell's state to given integer i
     * @param y y coordinate of cell
     * @param x x coordinate of cell
     * @param i the new state of cell
     */
    public void setCell(int y, int x, int i) {
        this.grid[y][x] = i;
    }
    
    /**
     * Return cell's state
     * @param y y coordinate of cell
     * @param x x coordinate of cell
     * @return cels state
     */
    public int getCell(int y, int x) {
        return this.grid[y][x];
    }
    
    /**
     * Check if given cell is in dungeon
     * @param y y coordinate of cell
     * @param x x coordinate of cell
     * @return true if cell is in dungeon, false otherwise
     */
    public boolean cellIsInDungeon(int y, int x) {
        return (x >= 0) && (x < this.x) && (y >= 0) && (y < this.y);
    }
    
}
