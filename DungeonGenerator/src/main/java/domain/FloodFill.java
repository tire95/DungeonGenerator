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
    private int replacement;
    
    /**
     * Constructor
     * @param replacement integer to replace floor cells (i.e. with int=0)
     */
    public FloodFill(int replacement) {
        this.cells = new CellQueue(16);
        this.replacement = replacement;
    }
    
    /**
     * Starts the algorithm
     * @param y Starting y coordinate
     * @param x Starting x coordinate
     */
    public void startFloodFill(int y, int x) {
        this.dungeon.setCell(y, x, this.replacement);
        this.cells.enqueue(new Cell(y, x));

        while (!this.cells.isEmpty()) {
            Cell currentCell = this.cells.dequeue();
            if (currentCell.getCurrentX() - 1 >= 0 && this.dungeon.cellIsFloor(currentCell.getCurrentY(), currentCell.getCurrentX() - 1)) {
                this.dungeon.setCell(currentCell.getCurrentY(), currentCell.getCurrentX() - 1, this.replacement);
                this.cells.enqueue(new Cell(currentCell.getCurrentY(), currentCell.getCurrentX() - 1));
            }

            if ((currentCell.getCurrentX() + 1 < this.dungeon.getX()) && this.dungeon.cellIsFloor(currentCell.getCurrentY(), currentCell.getCurrentX() + 1)) {
                this.dungeon.setCell(currentCell.getCurrentY(), currentCell.getCurrentX() + 1, this.replacement);
                this.cells.enqueue(new Cell(currentCell.getCurrentY(), currentCell.getCurrentX() + 1));
            }

            if ((currentCell.getCurrentY() - 1 >= 0) && this.dungeon.cellIsFloor(currentCell.getCurrentY() - 1, currentCell.getCurrentX())) {
                this.dungeon.setCell(currentCell.getCurrentY() - 1, currentCell.getCurrentX(), this.replacement);
                this.cells.enqueue(new Cell(currentCell.getCurrentY() - 1, currentCell.getCurrentX()));
            }

            if ((currentCell.getCurrentY() + 1 < this.dungeon.getY()) && this.dungeon.cellIsFloor(currentCell.getCurrentY() + 1, currentCell.getCurrentX())) {
                this.dungeon.setCell(currentCell.getCurrentY() + 1, currentCell.getCurrentX(), this.replacement);
                this.cells.enqueue(new Cell(currentCell.getCurrentY() + 1, currentCell.getCurrentX()));
            }

        }
    }
    
    /**
     * Sets algorithms dungeon
     * @param dungeon dungeon
     */
    public void setDungeon(Dungeon dungeon) {
        this.dungeon = dungeon;
    }
    
    /**
     * Return dungeon
     * @return dungeon
     */
    public Dungeon getDungeon() {
        return this.dungeon;
    }
    
}
