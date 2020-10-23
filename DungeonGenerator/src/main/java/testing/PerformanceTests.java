/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package testing;

import domain.CellularAutomaton;
import domain.Dungeon;
import domain.FloodFill;
import domain.RandomWalk;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Performance tests for the algorithms
 * @author timot
 */
public class PerformanceTests {
    private CellularAutomaton automaton;
    private RandomWalk walk;
    private FloodFill forestFire;
    private FloodFill scanFill;
    private String filenameForGeneration = "Generation performance test results.csv";
    private String filenameForFill = "Flood fill performance test results.csv";
    
    /**
     * Constructor
     */
    public PerformanceTests() {
        
    }
    
    /**
     * Dungeon generation performance tests
     * @param average runs to average
     * @param maximum maximum size of the dungeon
     * @return true if write to file succeeded
     */
    public boolean generationPerformanceTests(int average, int maximum) {
        StringBuilder sb = new StringBuilder();
        sb.append("Run on: " + java.time.LocalDate.now() + " at: " + java.time.LocalTime.now());
        sb.append("\n");
        sb.append("Dimension,");
        sb.append("Cellular automaton,");
        sb.append("Random walk,");
        sb.append("\n");
        int[] dimension = getTestDimensions();
        int index = 0;
        while ((index < dimension.length) && ((dimension[index] * dimension[index]) <= maximum)) {
            int currentDimension = dimension[index];
            long automatonTime = 0;
            long walkTimeSimple = 0;
            long startTime = 0;
            long endTime = 0;
            for (int j = 0; j < average; j++) {
                this.automaton = new CellularAutomaton(4, currentDimension, currentDimension, 50);
                startTime = System.nanoTime();
                this.automaton.initializeDungeon();
                this.automaton.runAutomaton();
                endTime = System.nanoTime();
                automatonTime += (endTime - startTime);
                this.walk = new RandomWalk(currentDimension, currentDimension, 4, 50, 0, false);
                startTime = System.nanoTime();
                this.walk.runRandomWalk();
                endTime = System.nanoTime();
                walkTimeSimple += (endTime - startTime);
            }
            sb.append(currentDimension * currentDimension + "," + automatonTime / average + "," + walkTimeSimple / average + ",");
            sb.append("\n");
            index++;
        }
        sb.append("\n");
        return writeToFile(this.filenameForGeneration, sb);
    }
    
    /**
     * Flood fill performance tests
     * @param average runs to average
     * @param maximum maximum size of the dungeon
     * @return true if write to file succeeded
     */
    public boolean floodFillPerformanceTests(int average, int maximum) {
        StringBuilder sb = new StringBuilder();
        sb.append("Run on: " + java.time.LocalDate.now() + " at: " + java.time.LocalTime.now());
        sb.append("\n");
        sb.append("Dimension,");
        sb.append("Forest fire,");
        sb.append("Scan fill,");
        sb.append("\n");
        int[] dimension = getTestDimensions();
        int index = 0;
        while ((index < dimension.length) && ((dimension[index] * dimension[index]) <= maximum)) {
            int currentDimension = dimension[index];
            long forestFireTime = 0;
            long scanFillTime = 0;
            long startTime = 0;
            long endTime = 0;
            for (int j = 0; j < average; j++) {
                this.automaton = new CellularAutomaton(4, currentDimension, currentDimension, 50);
                this.automaton.initializeDungeon();
                this.automaton.runAutomaton();
                Dungeon forestFireDungeon = this.automaton.getDungeon();
                Dungeon copyDungeon = new Dungeon(forestFireDungeon.getX(), forestFireDungeon.getY(), forestFireDungeon.getGrid());
                this.forestFire = new FloodFill(forestFireDungeon, 0);
                this.scanFill = new FloodFill(copyDungeon, 1);
                startTime = System.nanoTime();
                this.forestFire.findLargestConnectedArea();
                endTime = System.nanoTime();
                forestFireTime += (endTime - startTime);
                startTime = System.nanoTime();
                this.scanFill.findLargestConnectedArea();
                endTime = System.nanoTime();
                scanFillTime += (endTime - startTime);
            }
            sb.append(currentDimension * currentDimension + "," + forestFireTime / average + "," + scanFillTime / average + ",");
            sb.append("\n");
            index++;
        }
        sb.append("\n");
        return writeToFile(filenameForFill, sb);
    }
    
    private int[] getTestDimensions() {
        int[] dimensions = {10, 32, 64, 100, 200, 300, 400, 500, 600, 800, 1000, 1200, 1500, 1800, 2000};
        return dimensions;
    }
    
    private boolean writeToFile(String filename, StringBuilder sb) {
        try (FileWriter writer = new FileWriter(new File(filename), true)) {
            writer.write(sb.toString());
            return true;
        } catch (IOException ex) {
            return false;
        } 
    }
    
}