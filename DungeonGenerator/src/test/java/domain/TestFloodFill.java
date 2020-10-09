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


public class TestFloodFill {
    private FloodFill testFloodFill1;
    private FloodFill testFloodFill2;

    
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
        testFloodFill1 = new FloodFill(new Dungeon(10, 10), 0);
        testFloodFill2 = new FloodFill(new Dungeon(10, 10), 1);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testForestFire1() {
        
        // draw a horizontal line
        for (int i = 0; i < 10; i++) {
            testFloodFill1.getDungeon().changeCellToStone(1, i);
        }
        
        // begin forest fill above the line
        testFloodFill1.forestFire(0, 0, 0, 2);
        
        for (int i = 0; i < 10; i++) {
            
            // check that forest fire found all cells above the line
            assertFalse(testFloodFill1.getDungeon().cellIsFloor(0, i));
            assertFalse(testFloodFill1.getDungeon().cellIsStone(0, i));
            
            // check that forest fire didn't affect the line or cells below the line
            assertTrue(testFloodFill1.getDungeon().cellIsStone(1, i));
            for (int j = 2; j < 10; j++) {
                assertTrue(testFloodFill1.getDungeon().cellIsFloor(j, i));
            }
        }
    }
    
    @Test
    public void testScanFill1() {
        
        // draw a horizontal line
        for (int i = 0; i < 10; i++) {
            testFloodFill2.getDungeon().changeCellToStone(1, i);
        }
        
        // begin scan fill above the line
        testFloodFill2.scanFill(0, 0, 0, 2);
        for (int i = 0; i < 10; i++) {
            
            // check that scan fill found all cells above the line
            assertFalse(testFloodFill2.getDungeon().cellIsStone(0, i));
            
            // check that scan fill didn't affect the line or cells below the line
            assertTrue(testFloodFill2.getDungeon().cellIsStone(1, i));
            for (int j = 2; j < 10; j++) {
                assertTrue(testFloodFill2.getDungeon().cellIsFloor(j, i));
            }
        }
    }
    
    @Test
    public void testForestFire2() {
        
        // draw a diagonal line
        for (int i = 0; i < 10; i++) {
            testFloodFill1.getDungeon().changeCellToStone(i, i);
        }
        testFloodFill1.forestFire(1, 0, 0, 2);
        
        // check that forest fire didn't affect the line
        for (int i = 0; i < 10; i++) {
            assertTrue(testFloodFill1.getDungeon().cellIsStone(i, i));
        }
        
        // check that forest fire found all cells above the line
        for (int i = 1; i < 10; i++) {
            for (int j = 0; j < i-1; j++) {
                assertFalse(testFloodFill1.getDungeon().cellIsFloor(i, j));
                assertFalse(testFloodFill1.getDungeon().cellIsStone(i, j));
            }
        }
        
        // check that forest fire didn't affect the cells below the line
        for (int i = 1; i < 10; i++) {
            for (int j = 0; j < i-1; j++) {
                assertTrue(testFloodFill1.getDungeon().cellIsFloor(j, i));
            }
        }
    }
     
    @Test
    public void testScanFill2() {
        
        // draw a diagonal line
        for (int i = 0; i < 10; i++) {
            testFloodFill2.getDungeon().changeCellToStone(i, i);
        }
        testFloodFill2.scanFill(1, 0, 0, 2);
        
        // check that scan fill didn't affect the line
        for (int i = 0; i < 10; i++) {
            assertTrue(testFloodFill2.getDungeon().cellIsStone(i, i));
        }
        
        // check that scan fill found all cells above the line
        for (int i = 1; i < 10; i++) {
            for (int j = 0; j < i-1; j++) {
                assertFalse(testFloodFill2.getDungeon().cellIsFloor(i, j));
                assertFalse(testFloodFill2.getDungeon().cellIsStone(i, j));
            }
        }
        
        // check that scan fill didn't affect the cells below the line
        for (int i = 1; i < 10; i++) {
            for (int j = 0; j < i-1; j++) {
                assertTrue(testFloodFill2.getDungeon().cellIsFloor(j, i));
            }
        }
        
    }
    
    @Test
    public void testForestFire3() {
        
        // draw a diagonal line
        for (int i = 0; i < 10; i++) {
            testFloodFill1.getDungeon().changeCellToStone(i, i);
        }
        testFloodFill1.forestFire(5, 4, 0, 2);
        
        // check that forest fire didn't affect the line
        for (int i = 0; i < 10; i++) {
            assertTrue(testFloodFill1.getDungeon().cellIsStone(i, i));
        }
        
        // check that forest fire found all cells below the line
        for (int i = 1; i < 10; i++) {
            for (int j = 0; j < i-1; j++) {
                assertFalse(testFloodFill1.getDungeon().cellIsFloor(i, j));
                assertFalse(testFloodFill1.getDungeon().cellIsStone(i, j));
            }
        }
        
        // check that forest fire didn't affect the cells above the line
        for (int i = 1; i < 10; i++) {
            for (int j = 0; j < i-1; j++) {
                assertTrue(testFloodFill1.getDungeon().cellIsFloor(j, i));
            }
        }
    }
    
    @Test
    public void testScanFill3() {
        // draw a diagonal line
        for (int i = 0; i < 10; i++) {
            testFloodFill2.getDungeon().changeCellToStone(i, i);
        }
        testFloodFill2.scanFill(5, 4, 0, 2);
        
        // check that scan fill didn't affect the line
        for (int i = 0; i < 10; i++) {
            assertTrue(testFloodFill2.getDungeon().cellIsStone(i, i));
        }
        
        // check that scan fill found all the cells below the line
        for (int i = 1; i < 10; i++) {
            for (int j = 0; j < i-1; j++) {
                assertFalse(testFloodFill2.getDungeon().cellIsFloor(i, j));
                assertFalse(testFloodFill2.getDungeon().cellIsStone(i, j));
            }
        }
        
        // check that scan fill didn't affect the cells above the line
        for (int i = 1; i < 10; i++) {
            for (int j = 0; j < i-1; j++) {
                assertTrue(testFloodFill2.getDungeon().cellIsFloor(j, i));
            }
        }
    }
    
    @Test
    public void testFindLargestAreaForForestFire1() {
        for (int i = 0; i < 10; i++) {
            testFloodFill1.getDungeon().changeCellToStone(1, i);
        }
        testFloodFill1.findLargestConnectedArea();
        for (int i = 0; i < 10; i++) {
            assertTrue(testFloodFill1.getDungeon().cellIsStone(0, i));
            assertTrue(testFloodFill1.getDungeon().cellIsStone(1, i));
            for (int j = 2; j < 10; j++) {
                assertEquals(3, testFloodFill1.getDungeon().getCell(j, i));
            }
        }
    }
    
    @Test
    public void testFindLargestAreaForScanFill1() {    
        for (int i = 0; i < 10; i++) {
            testFloodFill2.getDungeon().changeCellToStone(1, i);
        }
        testFloodFill2.findLargestConnectedArea();
        for (int i = 0; i < 10; i++) {
            assertTrue(testFloodFill2.getDungeon().cellIsStone(0, i));
            assertTrue(testFloodFill2.getDungeon().cellIsStone(1, i));
            for (int j = 2; j < 10; j++) {
                assertEquals(3, testFloodFill2.getDungeon().getCell(j, i));
            }
        }
    }
    
    @Test
    public void testFindLargestAreaForForestFire2() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                testFloodFill1.getDungeon().changeCellToStone(i, j);
            }
        }
        testFloodFill1.findLargestConnectedArea();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                assertTrue(testFloodFill1.getDungeon().cellIsStone(i, j));
            }
        }
    }
    
    @Test
    public void testFindLargestAreaForScanFill2() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                testFloodFill2.getDungeon().changeCellToStone(i, j);
            }
        }
        testFloodFill2.findLargestConnectedArea();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                assertTrue(testFloodFill2.getDungeon().cellIsStone(i, j));
            }
        }
    }
    
    @Test
    public void testFindLargestAreaForForestFire3() {
        for (int i = 0; i < 10; i++) {
            testFloodFill1.getDungeon().changeCellToStone(6, i);
        }
        testFloodFill1.findLargestConnectedArea();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 6; j++) {
                assertEquals(3, testFloodFill1.getDungeon().getCell(j, i));
            }
            for (int j = 7; j < 10; j++) {
                assertTrue(testFloodFill1.getDungeon().cellIsStone(j, i));
            }
        }   
    }
    
    @Test
    public void testFindLargestAreaForScanFill3() {
        for (int i = 0; i < 10; i++) {
            testFloodFill2.getDungeon().changeCellToStone(6, i);
        }
        testFloodFill2.findLargestConnectedArea();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 6; j++) {
                assertEquals(3, testFloodFill2.getDungeon().getCell(j, i));
            }
            for (int j = 7; j < 10; j++) {
                assertTrue(testFloodFill2.getDungeon().cellIsStone(j, i));
            }
        }
    }
    
}
