/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

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
public class TestRandomWalk {
    private int digPercent;
    private RandomWalk testRandomWalk;
    private RandomWalk testRandomWalk2;
    private RandomWalk testRandomWalk3;
    
    public TestRandomWalk() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        digPercent = 30;
        testRandomWalk = new RandomWalk(10, 10, 10, digPercent, 50);
        testRandomWalk2 = new RandomWalk(50, 100, 30, digPercent/2, 50);
        testRandomWalk3 = new RandomWalk(3, 20, 1, digPercent*2, 50);
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void testInitDungeon() {
        testRandomWalk.initDungeon();
        assertTrue(testRandomWalk.getWalkers().isEmpty());
        int dungeonX = testRandomWalk.getDungeon().getX();
        int dungeonY = testRandomWalk.getDungeon().getY();
        for (int y = 0; y < dungeonY; y++) {
            for (int x = 0; x < dungeonX; x++) {
                assertFalse(testRandomWalk.getDungeon().cellIsFloor(y, x));
            }
        }
        
        testRandomWalk2.initDungeon();
        assertTrue(testRandomWalk2.getWalkers().isEmpty());
        dungeonX = testRandomWalk2.getDungeon().getX();
        dungeonY = testRandomWalk2.getDungeon().getY();
        for (int y = 0; y < dungeonY; y++) {
            for (int x = 0; x < dungeonX; x++) {
                assertFalse(testRandomWalk2.getDungeon().cellIsFloor(y, x));
            }
        }
        
        testRandomWalk3.initDungeon();
        assertTrue(testRandomWalk3.getWalkers().isEmpty());
        dungeonX = testRandomWalk3.getDungeon().getX();
        dungeonY = testRandomWalk3.getDungeon().getY();
        for (int y = 0; y < dungeonY; y++) {
            for (int x = 0; x < dungeonX; x++) {
                assertFalse(testRandomWalk3.getDungeon().cellIsFloor(y, x));
            }
        }
    }
    
    @Test
    public void testRunRandomWalk() {
        testRandomWalk.initDungeon();
        testRandomWalk.runRandomWalk();
        int dugCells = 0;
        int dungeonX = testRandomWalk.getDungeon().getX();
        int dungeonY = testRandomWalk.getDungeon().getY();
        for (int y = 0; y < dungeonY; y++) {
            for (int x = 0; x < dungeonX; x++) {
                if (testRandomWalk.getDungeon().cellIsFloor(y, x)) {
                    dugCells++;
                }
            }
        }
        assertTrue(digPercent <= (100*dugCells/(dungeonX*dungeonY)));
        
        testRandomWalk2.initDungeon();
        testRandomWalk2.runRandomWalk();
        dugCells = 0;
        dungeonX = testRandomWalk2.getDungeon().getX();
        dungeonY = testRandomWalk2.getDungeon().getY();
        for (int y = 0; y < dungeonY; y++) {
            for (int x = 0; x < dungeonX; x++) {
                if (testRandomWalk2.getDungeon().cellIsFloor(y, x)) {
                    dugCells++;
                }
            }
        }
        assertTrue(digPercent/2 <= (100*dugCells/(dungeonX*dungeonY)));
        
        testRandomWalk3.initDungeon();
        testRandomWalk3.runRandomWalk();
        dugCells = 0;
        dungeonX = testRandomWalk3.getDungeon().getX();
        dungeonY = testRandomWalk3.getDungeon().getY();
        for (int y = 0; y < dungeonY; y++) {
            for (int x = 0; x < dungeonX; x++) {
                if (testRandomWalk3.getDungeon().cellIsFloor(y, x)) {
                    dugCells++;
                }
            }
        }
        assertTrue(digPercent*2 <= (100*dugCells/(dungeonX*dungeonY)));
    }
    
}
