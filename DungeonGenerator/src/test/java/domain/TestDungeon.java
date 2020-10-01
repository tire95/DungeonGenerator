/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author timot
 */
public class TestDungeon {
    
    private Dungeon testDungeon;

    public TestDungeon() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        testDungeon = new Dungeon(10, 5);
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void testNeighborCheck() {
        // check that a cell with no stone neighbors gives 0
        assertEquals(0, testDungeon.checkNumberOfStoneNeighbors(6, 2));
        
        // check that a corner cell has 5 "stone neighbors"
        assertEquals(5, testDungeon.checkNumberOfStoneNeighbors(0, 0));
        assertEquals(5, testDungeon.checkNumberOfStoneNeighbors(9, 4));
        
        // check that a side cell has 3 "stone neighbors"
        assertEquals(3, testDungeon.checkNumberOfStoneNeighbors(0, 2));
    }
    
    @Test
    public void testDimensionGetters() {
        assertEquals(10, testDungeon.getY());
        assertEquals(5, testDungeon.getX());
    }
    
    @Test
    public void testCellChangersAndChecker() {
        assertTrue(testDungeon.cellIsFloor(2, 2));
        assertFalse(testDungeon.cellIsStone(2, 2));
        testDungeon.changeCellToStone(2, 2);
        assertFalse(testDungeon.cellIsFloor(2, 2));
        assertTrue(testDungeon.cellIsStone(2, 2));
        testDungeon.changeCellToFloor(2, 2);
        assertTrue(testDungeon.cellIsFloor(2, 2));
        assertFalse(testDungeon.cellIsStone(2, 2));
        testDungeon.setCell(0, 0, 5);
        assertFalse(testDungeon.cellIsFloor(0, 0));
        assertFalse(testDungeon.cellIsStone(0, 0));
        testDungeon.setCell(0, 0, 0);
        assertTrue(testDungeon.cellIsFloor(0, 0));
        assertFalse(testDungeon.cellIsStone(0, 0));
    }
    
    @Test
    public void testGridSetter() {
        int[][] newGrid = new int[5][2];
        newGrid[2][1] = 1;
        testDungeon.setGrid(newGrid);
        Assert.assertArrayEquals(newGrid, testDungeon.getGrid());
    }
    
    @Test
    public void testCleanUp() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                testDungeon.changeCellToStone(i, j);
            }
        }
        testDungeon.changeCellToStone(8, 3);
        
        testDungeon.cleanUp();
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertTrue(testDungeon.cellIsStone(i, j));
            }
        }
        
        assertTrue(testDungeon.cellIsFloor(8, 3));
        
        testDungeon.setGrid(new int[20][10]);
        for (int i = 8; i < 12; i++) {
            for (int j = 5; j < 7; j++) {
                testDungeon.changeCellToStone(i, j);
            }
        }
        
        testDungeon.changeCellToStone(3, 3);
        
        testDungeon.cleanUp();
        
        for (int i = 8; i < 12; i++) {
            for (int j = 5; j < 7; j++) {
                assertTrue(testDungeon.cellIsStone(i, j));
            }
        }
        
        assertTrue(testDungeon.cellIsFloor(3, 3));
        
        
    }
    
}
