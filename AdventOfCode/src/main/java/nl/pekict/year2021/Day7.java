
package nl.pekict.year2021;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Day7 {

    public static void main(String[] args) {
        solution();
    }
    
    public static void solution() {
        // Part 1
        long startTime = System.currentTimeMillis();
        ArrayList<Integer> positions = new ArrayList<>();
        int[] fuelNeeded = { (int)Math.pow(10, 10), (int)Math.pow(10, 10) };
        
        try (Scanner scanner = new Scanner(Paths.get("day7.txt"))) {
            if (scanner.hasNextLine()) {
                String[] pieces = scanner.nextLine().split(",");
                for (String piece : pieces) {
                    int number = Integer.parseInt(piece);
                    positions.add(number);
                }
            }
            
            Collections.sort(positions);            
            for (int i = positions.get(0); i < positions.get(positions.size() - 1); i++) {
                int[] newFuel = { 0, 0 };
                for (Integer position : positions) {
                    newFuel[0] += Math.abs((position) - i);
                    
                    for (int j = 0; j < Math.abs((position) - i); j++) { // Part 2
                        newFuel[1] += j + 1;
                        if (newFuel[1] >= fuelNeeded[1]) {
                            j = Math.abs((position) - i);
                        }
                    }
                }
                
                if (newFuel[0] < fuelNeeded[0]) {
                    fuelNeeded[0] = newFuel[0];
                }
                
                if (newFuel[1] < fuelNeeded[1]) {
                    fuelNeeded[1] = newFuel[1];
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        
        System.out.println("Antwoord 1: " + fuelNeeded[0]);
        System.out.println("Antwoord 2: " + fuelNeeded[1]);
        System.out.println("Tijd: " + totalTime);
    }   
}
