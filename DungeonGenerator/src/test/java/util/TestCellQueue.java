/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import domain.Cell;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author timot
 */
public class TestCellQueue {
    private CellQueue testCellQueue;
    
    public TestCellQueue() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.testCellQueue = new CellQueue(10);
        for (int i = 0; i < 8; i++) {
            this.testCellQueue.enqueue(new Cell(i, i));
        }
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void testDequeue() {
        for (int i = 0; i < 8; i++) {
            assertFalse(this.testCellQueue.isEmpty());
            assertEquals(8-i, this.testCellQueue.size());
            Cell next = this.testCellQueue.dequeue();
            assertEquals(i, next.getCurrentX());
            assertEquals(i, next.getCurrentY());
        }
        assertEquals(0, this.testCellQueue.size());
        assertTrue(this.testCellQueue.isEmpty());
    }
    
    @Test
    public void testEnqueue() {
        this.testCellQueue.enqueue(new Cell(10, 10));
        assertEquals(9, this.testCellQueue.size());
        for (int i = 0; i < 8; i++) {
            this.testCellQueue.dequeue();
        }
        assertEquals(1, this.testCellQueue.size());
        Cell next = this.testCellQueue.dequeue();
        assertEquals(10, next.getCurrentX());
        assertTrue(this.testCellQueue.isEmpty());
        assertEquals(0, this.testCellQueue.size());

    }
    
    @Test
    public void testGrowQueue() {
        for (int i = 0; i < 5; i++) {
            this.testCellQueue.enqueue(new Cell(i, i));
        }
        assertEquals(20, this.testCellQueue.actualSize());
        assertEquals(13, this.testCellQueue.size());
        
        for (int i = 0; i < 10; i++) {
            this.testCellQueue.enqueue(new Cell(i, i));
        }
        
        assertEquals(40, this.testCellQueue.actualSize());
        assertEquals(23, this.testCellQueue.size());
        
        for (int i = 0; i < 8; i++) {
            assertFalse(this.testCellQueue.isEmpty());
            Cell next = this.testCellQueue.dequeue();
            assertEquals(i, next.getCurrentX());
            assertEquals(i, next.getCurrentY());
        }
        
        for (int i = 0; i < 5; i++) {
            assertFalse(this.testCellQueue.isEmpty());
            Cell next = this.testCellQueue.dequeue();
            assertEquals(i, next.getCurrentX());
            assertEquals(i, next.getCurrentY());
        }
        
        for (int i = 0; i < 10; i++) {
            assertFalse(this.testCellQueue.isEmpty());
            Cell next = this.testCellQueue.dequeue();
            assertEquals(i, next.getCurrentX());
            assertEquals(i, next.getCurrentY());
        }
    }
    
    @Test
    public void testReduceQueue() {
        for (int i = 0; i < 20; i++) {
            this.testCellQueue.enqueue(new Cell(i, i));
        }
        assertEquals(28, this.testCellQueue.size());
        assertEquals(40, this.testCellQueue.actualSize());
        for (int i = 0; i < 18; i++) {
            assertEquals(28 - i, this.testCellQueue.size());
            assertEquals(40, this.testCellQueue.actualSize());
            this.testCellQueue.dequeue();
        }
        assertEquals(10, this.testCellQueue.size());
        this.testCellQueue.dequeue();
        assertEquals(20, this.testCellQueue.actualSize());
        for (int i = 0; i < 5; i++) {
            assertEquals(9 - i, this.testCellQueue.size());
            assertEquals(20, this.testCellQueue.actualSize());
            this.testCellQueue.dequeue();
        }
        assertEquals(10, this.testCellQueue.actualSize());
        for (int i = 0; i < 4; i++) {
            assertEquals(4 - i, this.testCellQueue.size());
            assertEquals(10, this.testCellQueue.actualSize());
            this.testCellQueue.dequeue();
        }
        assertEquals(10, this.testCellQueue.actualSize());
        assertTrue(this.testCellQueue.isEmpty());
    }
    
}
