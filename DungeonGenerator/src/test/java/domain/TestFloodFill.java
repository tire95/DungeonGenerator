/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import static junit.framework.Assert.assertEquals;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author timot
 */
public class TestFloodFill {
    private FloodFill testFloodFill;
    
    
    public TestFloodFill() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        testFloodFill = new FloodFill(new Dungeon(10, 10));
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void testFloodFill1() {
        for (int i = 0; i < 10; i++) {
            testFloodFill.getDungeon().changeCellToStone(1, i);
        }
        testFloodFill.startFloodFill(0, 0, 0, 2);
        for (int i = 0; i < 10; i++) {
            assertFalse(testFloodFill.getDungeon().cellIsFloor(0, i));
            assertFalse(testFloodFill.getDungeon().cellIsStone(0, i));
            assertTrue(testFloodFill.getDungeon().cellIsStone(1, i));
            for (int j = 2; j < 10; j++) {
                assertTrue(testFloodFill.getDungeon().cellIsFloor(j, i));
            }
        }
    }
    
    @Test
    public void testFloodFill2() {
        for (int i = 0; i < 10; i++) {
            testFloodFill.getDungeon().changeCellToStone(i, i);
        }
        testFloodFill.startFloodFill(1, 0, 0, 2);
        
        for (int i = 0; i < 10; i++) {
            assertTrue(testFloodFill.getDungeon().cellIsStone(i, i));
        }
        
        for (int i = 1; i < 10; i++) {
            for (int j = 0; j < i-1; j++) {
                assertFalse(testFloodFill.getDungeon().cellIsFloor(i, j));
                assertFalse(testFloodFill.getDungeon().cellIsStone(i, j));
            }
        }
        
        for (int i = 1; i < 10; i++) {
            for (int j = 0; j < i-1; j++) {
                assertTrue(testFloodFill.getDungeon().cellIsFloor(j, i));
            }
        }
        
    }
    
    @Test
    public void testFloodFill3() {
        for (int i = 0; i < 10; i++) {
            testFloodFill.getDungeon().changeCellToStone(i, i);
        }
        testFloodFill.startFloodFill(5, 4, 0, 2);
        
        for (int i = 0; i < 10; i++) {
            assertTrue(testFloodFill.getDungeon().cellIsStone(i, i));
        }
        
        for (int i = 1; i < 10; i++) {
            for (int j = 0; j < i-1; j++) {
                assertFalse(testFloodFill.getDungeon().cellIsFloor(i, j));
                assertFalse(testFloodFill.getDungeon().cellIsStone(i, j));
            }
        }
        
        for (int i = 1; i < 10; i++) {
            for (int j = 0; j < i-1; j++) {
                assertTrue(testFloodFill.getDungeon().cellIsFloor(j, i));
            }
        }
        
    }
    
    @Test
    public void testFindLargestArea() {
        for (int i = 0; i < 10; i++) {
            testFloodFill.getDungeon().changeCellToStone(1, i);
        }
        testFloodFill.findLargestConnectedArea();
        for (int i = 0; i < 10; i++) {
            assertTrue(testFloodFill.getDungeon().cellIsStone(0, i));
            assertTrue(testFloodFill.getDungeon().cellIsStone(1, i));
            for (int j = 2; j < 10; j++) {
                assertEquals(3, testFloodFill.getDungeon().getCell(j, i));
            }
        }
    }
    
    @Test
    public void testFindLargestArea2() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                testFloodFill.getDungeon().changeCellToStone(i, j);
            }
        }
        testFloodFill.findLargestConnectedArea();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                assertTrue(testFloodFill.getDungeon().cellIsStone(i, j));
            }
        }
    }
    
    @Test
    public void testFindLargetArea3() {
        for (int i = 0; i < 10; i++) {
            testFloodFill.getDungeon().changeCellToStone(6, i);
        }
        testFloodFill.findLargestConnectedArea();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 6; j++) {
                assertEquals(3, testFloodFill.getDungeon().getCell(j, i));
            }
            for (int j = 7; j < 10; j++) {
                assertTrue(testFloodFill.getDungeon().cellIsStone(j, i));
            }
        }
    }
    
}
