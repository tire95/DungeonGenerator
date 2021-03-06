/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import domain.Cell;

/**
 * Queue data structure for cells
 * @author timot
 */
public class CellQueue {
    private int lastItem;   // index of last item
    private int firstItem;  // index of first item
    private int size;       // how large the queue array is
    private Cell[] queue;
    
    /**
     * Constructor
     * @param size initial size of queue
     */
    public CellQueue(int size) {
        this.size = size;
        this.queue = new Cell[size];
        this.lastItem = this.firstItem = -1;
    }
    
    /**
     * Adds cell to queue
     * @param cell cell to be added
     */
    public void enqueue(Cell cell) {

        // if the queue is full, double the size
        if (this.lastItem == this.size - 1) {
            growQueue();
        }
        this.lastItem++;
        this.queue[this.lastItem] = cell;
    }
    
    /**
     * Takes first cell from queue
     * @return first cell
     */
    public Cell dequeue() {
        
        // if the queue is only quarter full, half the size
        if ((this.lastItem - this.firstItem <= this.size / 4) && (this.size >= 20)) {
            reduceQueue();
        }
        this.firstItem++;
        return this.queue[this.firstItem];
    }
    
    private void growQueue() {
        Cell[] oldQueue = this.queue;
        this.size *= 2;
        this.queue = new Cell[this.size];
        int f = this.firstItem;
        for (int i = 0; i < (this.lastItem - this.firstItem); i++) {
            f++;
            this.queue[i] = oldQueue[f];
        }
        this.lastItem -= this.firstItem + 1;
        this.firstItem = -1;
    }

    private void reduceQueue() {
        Cell[] oldQueue = this.queue;
        this.size /= 2;
        this.queue = new Cell[this.size];
        int f = this.firstItem;
        for (int i = 0; i < (this.lastItem - this.firstItem); i++) {
            f++;
            this.queue[i] = oldQueue[f];
        }
        this.lastItem -= this.firstItem + 1;
        this.firstItem = -1;
    }
    
    /**
     * Checks if queue is empty
     * @return true if empty
     */
    public boolean isEmpty() {
        return this.lastItem == this.firstItem;
    }
    
    /**
     * Returns number of cells in queue
     * @return size
     */
    public int size() {
        return this.lastItem - this.firstItem;
    }
    
    /**
     * Returns queue's actual size i.e. how large the array used is
     * @return actual size
     */
    public int actualSize() {
        return this.size;
    }
    
}
