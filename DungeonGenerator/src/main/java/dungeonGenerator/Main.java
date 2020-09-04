package dungeonGenerator;

import domain.CellularAutomaton;
import domain.Dungeon;
import java.util.Scanner;

public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        Scanner s = new Scanner(System.in);
//        System.out.println("Dungeon heigth: ");
//        int heigth = s.nextInt();
//        System.out.println("Dungeon width: ");
//        int width = s.nextInt();
//        System.out.println("Stone percent: ");
//        int percent = s.nextInt();
//        System.out.println("Iterations: ");
//        int iterations = s.nextInt();
//        System.out.println("");
//        
//        Dungeon d = new Dungeon(heigth, width);
//        CellularAutomaton c = new CellularAutomaton(iterations, d, percent);

        Dungeon d = new Dungeon(3,3);
        d.printDungeon();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                d.changeCellToStone(i, j);
            } 
        }
        d.printDungeon();
        System.out.println(d.checkNumberOfNeighbors(1, 1));
    }
}
