/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

import util.CellQueue;


/**
 * Flood fill algorithm
 * @author timot
 */
public class FloodFill {
    private CellQueue cells;
    private Dungeon dungeon;
    
    /**
     * Constructor
     * @param d dungeon
     */
    public FloodFill(Dungeon d) {
        this.cells = new CellQueue(16);
        this.dungeon = d;
    }
    
    /**
     * Starts the algorithm flood fill algorithm
     * @param y Starting y coordinate
     * @param x Starting x coordinate
     * @param target Cell state to be filled
     * @param replacement Cell state to replace target
     * @return number of cells filled
     */
    public int startFloodFill(int y, int x, int target, int replacement) {
        int cells = 0;
        this.dungeon.setCell(y, x, replacement);
        this.cells.enqueue(new Cell(y, x));

        while (!this.cells.isEmpty()) {
            Cell currentCell = this.cells.dequeue();
            
            // check if cell to the left is in the dungeon and of target integer
            if ((currentCell.getCurrentX() - 1 >= 0) && (this.dungeon.getCell(currentCell.getCurrentY(), currentCell.getCurrentX() - 1) == target)) {
                cells++;
                this.dungeon.setCell(currentCell.getCurrentY(), currentCell.getCurrentX() - 1, replacement);
                this.cells.enqueue(new Cell(currentCell.getCurrentY(), currentCell.getCurrentX() - 1));
            }

            // check if cell to the right is in the dungeon and of target integer
            if ((currentCell.getCurrentX() + 1 < this.dungeon.getX()) && (this.dungeon.getCell(currentCell.getCurrentY(), currentCell.getCurrentX() + 1) == target)) {
                cells++;
                this.dungeon.setCell(currentCell.getCurrentY(), currentCell.getCurrentX() + 1, replacement);
                this.cells.enqueue(new Cell(currentCell.getCurrentY(), currentCell.getCurrentX() + 1));
            }

            // check if cell above is in the dungeon and of target integer
            if ((currentCell.getCurrentY() - 1 >= 0) && (this.dungeon.getCell(currentCell.getCurrentY() - 1, currentCell.getCurrentX()) == target)) {
                cells++;
                this.dungeon.setCell(currentCell.getCurrentY() - 1, currentCell.getCurrentX(), replacement);
                this.cells.enqueue(new Cell(currentCell.getCurrentY() - 1, currentCell.getCurrentX()));
            }
            
            // check if cell below is in the dungeon and of target integer
            if ((currentCell.getCurrentY() + 1 < this.dungeon.getY()) && (this.dungeon.getCell(currentCell.getCurrentY() + 1, currentCell.getCurrentX()) == target)) {
                cells++;
                this.dungeon.setCell(currentCell.getCurrentY() + 1, currentCell.getCurrentX(), replacement);
                this.cells.enqueue(new Cell(currentCell.getCurrentY() + 1, currentCell.getCurrentX()));
            }
        }
        return cells;
    }
    
    /**
     * Find the largest connected area in the dungeon and turn everything else to stone
     */
    public void findLargestConnectedArea() {
        Cell largestAreaStart = null;
        int largestAreaCells = 0;
        for (int y = 0; y < this.dungeon.getY(); y++) {
            for (int x = 0; x < this.dungeon.getX(); x++) {
                if (this.dungeon.cellIsFloor(y, x)) {
                    int cells = startFloodFill(y, x, 0, 2);
                    if (cells > largestAreaCells) {
                        largestAreaCells = cells;
                        largestAreaStart = new Cell(y, x);
                    }
                }
            }
        }
        
        // if a largest area was found, flood fill it to a new integer and change everything else to stone
        if (largestAreaStart != null) {
            startFloodFill(largestAreaStart.getCurrentY(), largestAreaStart.getCurrentX(), 2, 3);
            for (int y = 0; y < this.dungeon.getY(); y++) {
                for (int x = 0; x < this.dungeon.getX(); x++) {
                    if (this.dungeon.getCell(y, x) != 3) {
                        this.dungeon.changeCellToStone(y, x);
                    }
                }
            }
        }
    }
    
    /**
     * Return dungeon
     * @return dungeon
     */
    public Dungeon getDungeon() {
        return this.dungeon;
    }
    
}
