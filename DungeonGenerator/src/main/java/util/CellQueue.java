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
    private int rear;
    private int front;
    private int size;
    private Cell[] queue;
    
    /**
     * Constructor
     * @param size initial size of queue
     */
    public CellQueue(int size) {
        this.size = size;
        this.queue = new Cell[size];
        this.rear = this.front = -1;
    }
    
    /**
     * Adds cell to queue
     * @param cell cell to be added
     */
    public void enqueue(Cell cell) {

        if (this.rear == this.size - 1) {
            growQueue();
        }
        this.rear++;
        this.queue[this.rear] = cell;
    }
    
    /**
     * Takes first cell from queue
     * @return first cell
     */
    public Cell dequeue() {
        if ((this.rear - this.front <= this.size / 4) && (this.size >= 20)) {
            reduceQueue();
        }
        this.front++;
        return this.queue[this.front];
    }
    
    private void growQueue() {
        Cell[] oldQueue = this.queue;
        this.size *= 2;
        this.queue = new Cell[this.size];
        int f = this.front;
        for (int i = 0; i < (this.rear - this.front); i++) {
            f++;
            this.queue[i] = oldQueue[f];
        }
        this.rear -= this.front + 1;
        this.front = -1;
    }

    private void reduceQueue() {
        Cell[] oldQueue = this.queue;
        this.size = this.size / 2;
        this.queue = new Cell[this.size];
        int f = this.front;
        for (int i = 0; i < (this.rear - this.front); i++) {
            f++;
            this.queue[i] = oldQueue[f];
        }
        this.rear -= this.front + 1;
        this.front = -1;
    }
    
    /**
     * Checks if queue is empty
     * @return true if empty
     */
    public boolean isEmpty() {
        return this.rear == this.front;
    }
    
    /**
     * Returns number of cells in queue
     * @return size
     */
    public int size() {
        return this.rear - this.front;
    }
    
    /**
     * Returns queue's actual size i.e. how large the array used is
     * @return actual size
     */
    public int actualSize() {
        return this.size;
    }
    
}
