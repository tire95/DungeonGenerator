/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

import org.junit.After;
import org.junit.AfterClass;
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
    private RandomWalk testRandomWalk4;
    private RandomWalk testRandomWalk5;
    
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
        testRandomWalk = new RandomWalk(10, 10, 10, digPercent, 50, false);
        testRandomWalk2 = new RandomWalk(50, 100, 30, digPercent/2, 50, false);
        testRandomWalk3 = new RandomWalk(3, 20, 1, digPercent*2, 50, false);
        testRandomWalk4 = new RandomWalk(20, 45, 0, digPercent, 50, true);
        testRandomWalk5 = new RandomWalk(100, 300, 5, digPercent/2, 33, true);
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void testRunRandomWalk() {
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
        
        testRandomWalk4.runRandomWalk();
        dugCells = 0;
        dungeonX = testRandomWalk4.getDungeon().getX();
        dungeonY = testRandomWalk4.getDungeon().getY();
        for (int y = 0; y < dungeonY; y++) {
            for (int x = 0; x < dungeonX; x++) {
                if (testRandomWalk4.getDungeon().cellIsFloor(y, x)) {
                    dugCells++;
                }
            }
        }
        assertTrue(digPercent <= (100*dugCells/(dungeonX*dungeonY)));
        
        testRandomWalk5.runRandomWalk();
        dugCells = 0;
        dungeonX = testRandomWalk5.getDungeon().getX();
        dungeonY = testRandomWalk5.getDungeon().getY();
        for (int y = 0; y < dungeonY; y++) {
            for (int x = 0; x < dungeonX; x++) {
                if (testRandomWalk5.getDungeon().cellIsFloor(y, x)) {
                    dugCells++;
                }
            }
        }
        assertTrue(digPercent/2 <= (100*dugCells/(dungeonX*dungeonY)));
    }
    
}
