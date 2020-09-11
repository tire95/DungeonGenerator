/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

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
public class TestCellularAutomaton {
    private CellularAutomaton testCellular;
    private CellularAutomaton testCellular2;

    public TestCellularAutomaton() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        testCellular = new CellularAutomaton(2, 3, 5, 30);
        testCellular2 = new CellularAutomaton(15, 20, 30, 70);
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void testInitializeDungeon() {
        testCellular.initializeDungeon();
        int changedCells = 0;
        int dungeonY = testCellular.getDungeon().getY();
        int dungeonX = testCellular.getDungeon().getX();
        for (int y = 0; y < dungeonY; y++) {
            for (int x = 0; x < dungeonX; x++) {
                if (!testCellular.getDungeon().cellIsFloor(y, x)) {
                    changedCells++;
                }
            }
        }
        assertTrue(testCellular.getStonePercent() <= 100*changedCells/(dungeonX*dungeonY));
        
        testCellular2.initializeDungeon();
        changedCells = 0;
        dungeonY = testCellular2.getDungeon().getY();
        dungeonX = testCellular2.getDungeon().getX();
        for (int y = 0; y < dungeonY; y++) {
            for (int x = 0; x < dungeonX; x++) {
                if (!testCellular2.getDungeon().cellIsFloor(y, x)) {
                    changedCells++;
                }
            }
        }
        assertTrue(testCellular2.getStonePercent() <= 100*changedCells/(dungeonX*dungeonY));
    }
    
    @Test
    public void testIterations() {
        assertEquals(2, testCellular.getIterations());
    }
    
    @Test
    public void testReset() {
        testCellular.initializeDungeon();
        testCellular.reset();
        int dungeonY = testCellular.getDungeon().getY();
        int dungeonX = testCellular.getDungeon().getX();
        for (int y = 0; y < dungeonY; y++) {
            for (int x = 0; x < dungeonX; x++) {
                assertTrue(testCellular.getDungeon().cellIsFloor(y, x));
            }
        }
        
        testCellular2.initializeDungeon();
        testCellular2.reset();
        dungeonY = testCellular2.getDungeon().getY();
        dungeonX = testCellular2.getDungeon().getX();
        for (int y = 0; y < dungeonY; y++) {
            for (int x = 0; x < dungeonX; x++) {
                assertTrue(testCellular2.getDungeon().cellIsFloor(y, x));
            }
        }
    }
    
    @Test
    public void testRunAutomaton() {
        int dungeonY = testCellular.getDungeon().getY();
        int dungeonX = testCellular.getDungeon().getX();
        for (int i = 0; i < dungeonX; i++) {
            testCellular.getDungeon().changeCellToStone(1, i);
        }
        testCellular.runAutomaton();
        for (int y = 0; y < dungeonY; y++) {
            for (int x = 0; x < dungeonX; x++) {
                assertFalse(testCellular.getDungeon().cellIsFloor(y, x));
            }
        }
        
    }
    
}
