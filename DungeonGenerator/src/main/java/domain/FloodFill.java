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
    private int fillAlgorithm;
    
    /**
     * Constructor
     * @param d dungeon
     * @param f flood fill algorithm to be used; 0 = forest fire, 1 = scan fill
     */
    public FloodFill(Dungeon d, int f) {
        this.cells = new CellQueue(16);
        this.dungeon = d;
        this.fillAlgorithm = f;
    }
    
    /**
     * Starts the "forest fire" flood fill algorithm
     * @param y Starting y coordinate
     * @param x Starting x coordinate
     * @param target Cell state to be filled
     * @param replacement Cell state to replace target
     * @return number of cells filled
     */
    public int forestFire(int y, int x, int target, int replacement) {
        int cells = 0;
        this.dungeon.setCell(y, x, replacement);
        this.cells.enqueue(new Cell(y, x));

        while (!this.cells.isEmpty()) {
            Cell currentCell = this.cells.dequeue();
            int currentY = currentCell.getCurrentY();
            int currentX = currentCell.getCurrentX();
            
            // check if cell to the left is in the dungeon and of target integer
            if ((currentX - 1 >= 0) && (this.dungeon.getCell(currentY, currentX - 1) == target)) {
                cells++;
                this.dungeon.setCell(currentY, currentX - 1, replacement);
                this.cells.enqueue(new Cell(currentY, currentX - 1));
            }

            // check if cell to the right is in the dungeon and of target integer
            if ((currentX + 1 < this.dungeon.getX()) && (this.dungeon.getCell(currentY, currentX + 1) == target)) {
                cells++;
                this.dungeon.setCell(currentY, currentX + 1, replacement);
                this.cells.enqueue(new Cell(currentY, currentX + 1));
            }

            // check if cell above is in the dungeon and of target integer
            if ((currentY - 1 >= 0) && (this.dungeon.getCell(currentY - 1, currentX) == target)) {
                cells++;
                this.dungeon.setCell(currentY - 1, currentX, replacement);
                this.cells.enqueue(new Cell(currentY - 1, currentX));
            }
            
            // check if cell below is in the dungeon and of target integer
            if ((currentY + 1 < this.dungeon.getY()) && (this.dungeon.getCell(currentY + 1, currentX) == target)) {
                cells++;
                this.dungeon.setCell(currentY + 1, currentX, replacement);
                this.cells.enqueue(new Cell(currentY + 1, currentX));
            }
        }
        return cells;
    }
    
    /**
     * Starts the "scan fill" flood fill algorithm 
     * @param y Starting y coordinate
     * @param x Starting x coordinate
     * @param target Cell state to be filled
     * @param replacement Cell state to replace target
     * @return number of cells filled
     */
    public int scanFill(int y, int x, int target, int replacement) {
        int cells = 0;
        this.dungeon.setCell(y, x, replacement);
        this.cells.enqueue(new Cell(y, x));

        while (!this.cells.isEmpty()) {
            Cell currentCell = this.cells.dequeue();
            int yCoord = currentCell.getCurrentY();
            int left = currentCell.getCurrentX();
            int right = currentCell.getCurrentX();
            while ((left - 1 >= 0) && this.dungeon.getCell(yCoord, left - 1) == target) {
                left--;
            }
            while ((right + 1 < this.dungeon.getX()) && this.dungeon.getCell(yCoord, right + 1) == target) {
                right++;
            }
            
            for (int i = left; i <= right; i++) {
                this.dungeon.setCell(yCoord, i, replacement);
                cells++;
                if ((yCoord + 1 < this.dungeon.getY()) && this.dungeon.getCell(yCoord + 1, i) == target) {
                    this.cells.enqueue(new Cell(yCoord + 1, i));
                }
                if ((yCoord - 1 >= 0) && this.dungeon.getCell(yCoord - 1, i) == target) {
                    this.cells.enqueue(new Cell(yCoord - 1, i));
                }
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
                    int cells = 0;
                    if (this.fillAlgorithm == 0) {
                        cells = forestFire(y, x, 0, 2);
                    } else {
                        cells = scanFill(y, x, 0, 2);
                    }
                    if (cells > largestAreaCells) {
                        largestAreaCells = cells;
                        largestAreaStart = new Cell(y, x);
                    }
                }
            }
        }
        
        // if a largest area was found, flood fill it to a new integer and change everything else to stone
        if (largestAreaStart != null) {
            if (this.fillAlgorithm == 0) {
                forestFire(largestAreaStart.getCurrentY(), largestAreaStart.getCurrentX(), 2, 3);
            } else {
                scanFill(largestAreaStart.getCurrentY(), largestAreaStart.getCurrentX(), 2, 3);
            }
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
