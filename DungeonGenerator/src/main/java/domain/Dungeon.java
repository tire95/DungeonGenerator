/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author timot
 */
public class Dungeon {
    private int width;
    private int heigth;
    private int[][] grid;
    
    public Dungeon (int heigth, int width) {
        this.width = width;
        this.heigth = heigth;
        this.grid = new int[heigth][width];
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeigth() {
        return this.heigth;
    }
    
    public int checkNumberOfNeighbors(int y, int x) {
        int neighbors = 0;
        for (int i = y-1; i <= y+1; i++) {
            for (int j = x-1; j <= x+1; j++) {
                if ((i != y || j != x)) {
                    neighbors += this.grid[i][j];
                }
            }
        }
        return neighbors;
    }
    
    public void changeCellToStone(int y, int x) {
        this.grid[y][x] = 1;
    }
    
    public void changeCellToFloor(int y, int x) {
        this.grid[y][x] = 0;
    }
    
    public void printDungeon() {
        for (int[] y : this.grid)
        {
           for (int x : y)
           {
                System.out.print(x);
           }
           System.out.println();
        }
    }
    
    public boolean cellIsFloor(int y, int x) {
        return this.grid[y][x] == 0;
    }
    
}
