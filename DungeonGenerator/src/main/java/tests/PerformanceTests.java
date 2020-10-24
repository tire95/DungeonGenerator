/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tests;

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
    private String filenameForWalkSpawn = "Random walk spawn performance test results.csv";
    private String filenameForWalkDig = "Random walk dig performance test results.csv";
    
    /**
     * Constructor
     */
    public PerformanceTests() {
        
    }
    
    /**
     * Dungeon generation performance tests
     * @param iterations cellular automaton's iterations
     * @param stonePercent initial stone percent
     * @param spawnChance random walk's chance for spawning a new walker
     * @param digPercent random walk's percentage of cells to dig
     * @param average runs to average
     * @param maximum maximum size of the dungeon
     * @return true if write to file succeeded
     */
    public boolean generationPerformanceTests(int iterations, int stonePercent, int spawnChance, int digPercent, int average, int maximum) {
        StringBuilder sb = new StringBuilder();
        sb.append("Run on: " + java.time.LocalDate.now() + " at: " + java.time.LocalTime.now());
        sb.append("\n");
        sb.append("Given parameters: Cellular automaton; iterations: " + iterations + "; stone percent: " + stonePercent + "; Random walk; spawn chance: " + spawnChance + "; dig percent: " + digPercent + "; runs to average: " + average);
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
                this.automaton = new CellularAutomaton(iterations, currentDimension, currentDimension, stonePercent);
                startTime = System.nanoTime();
                this.automaton.initializeDungeon();
                this.automaton.runAutomaton();
                endTime = System.nanoTime();
                automatonTime += (endTime - startTime);
                this.walk = new RandomWalk(currentDimension, currentDimension, spawnChance, digPercent, 0, false);
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
     * @param iterations cellular automaton's iterations
     * @param stonePercent initial stone percent
     * @param average runs to average
     * @param maximum maximum size of the dungeon
     * @return true if write to file succeeded
     */
    public boolean floodFillPerformanceTests(int iterations, int stonePercent, int average, int maximum) {
        StringBuilder sb = new StringBuilder();
        sb.append("Run on: " + java.time.LocalDate.now() + " at: " + java.time.LocalTime.now());
        sb.append("\n");
        sb.append("Given parameters: Cellular automaton; iterations: " + iterations + "; stone percent: " + stonePercent + "; runs to average: " + average);
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
                this.automaton = new CellularAutomaton(iterations, currentDimension, currentDimension, stonePercent);
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
    
    /**
     * Random walk performance tests for spawning
     * @param digPercent random walk's percentage of cells to dig
     * @param average runs to average
     * @param maximum maximum size of the dungeon
     * @return true if write to file succeeded
     */
    public boolean randomWalkSpawnPerformanceTests(int digPercent, int average, int maximum) {
        StringBuilder sb = new StringBuilder();
        sb.append("Run on: " + java.time.LocalDate.now() + " at: " + java.time.LocalTime.now());
        sb.append("\n");
        sb.append("Given parameters: Dig percent: " + digPercent + "; runs to average: " + average);
        sb.append("\n");
        sb.append("Dimension,");
        sb.append("Spawn chance = 10%,");
        sb.append("Spawn chance = 20%,");
        sb.append("Spawn chance = 30%,");
        sb.append("Spawn chance = 40%,");
        sb.append("Spawn chance = 50%,");
        sb.append("Spawn chance = 60%,");
        sb.append("\n");
        int[] dimension = getTestDimensions();
        int index = 0;
        while ((index < dimension.length) && ((dimension[index] * dimension[index]) <= maximum)) {
            int currentDimension = dimension[index];
            long walkTime = 0;
            long startTime = 0;
            long endTime = 0;
            sb.append(currentDimension * currentDimension + ",");
            for (int s = 10; s <= 60; s += 10) {
                for (int j = 0; j < average; j++) {
                    this.walk = new RandomWalk(currentDimension, currentDimension, s, digPercent, 0, false);
                    startTime = System.nanoTime();
                    this.walk.runRandomWalk();
                    endTime = System.nanoTime();
                    walkTime += (endTime - startTime);
                }
                sb.append(walkTime / average + ",");
            }
            sb.append("\n");
            index++;
        }
        sb.append("\n");
        return writeToFile(this.filenameForWalkSpawn, sb);
    }
        
    /**
     * Random walk performance tests for digging
     * @param spawnChance random walk's chance of spawning a new walker
     * @param average runs to average
     * @param maximum maximum size of the dungeon
     * @return true if write to file succeeded
     */
    public boolean randomWalkDigPerformanceTests(int spawnChance, int average, int maximum) {
        StringBuilder sb = new StringBuilder();
        sb.append("Run on: " + java.time.LocalDate.now() + " at: " + java.time.LocalTime.now());
        sb.append("\n");
        sb.append("Given parameters: Spawn chance: " + spawnChance + "; runs to average: " + average);
        sb.append("\n");
        sb.append("Dimension,");
        sb.append("Dig percent = 10%,");
        sb.append("Dig percent = 20%,");
        sb.append("Dig percent = 30%,");
        sb.append("Dig percent = 40%,");
        sb.append("Dig percent = 50%,");
        sb.append("Dig percent = 60%,");

        sb.append("\n");
        int[] dimension = getTestDimensions();
        int index = 0;
        while ((index < dimension.length) && ((dimension[index] * dimension[index]) <= maximum)) {
            int currentDimension = dimension[index];
            long walkTime = 0;
            long startTime = 0;
            long endTime = 0;
            sb.append(currentDimension * currentDimension + ",");
            for (int d = 10; d <= 60; d += 10) {
                for (int j = 0; j < average; j++) {
                    this.walk = new RandomWalk(currentDimension, currentDimension, spawnChance, d, 0, false);
                    startTime = System.nanoTime();
                    this.walk.runRandomWalk();
                    endTime = System.nanoTime();
                    walkTime += (endTime - startTime);
                }
                sb.append(walkTime / average + ",");
            }
            sb.append("\n");
            index++;
        }
        sb.append("\n");
        return writeToFile(this.filenameForWalkDig, sb);
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