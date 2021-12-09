package nl.pekict.year2021;

import java.nio.file.Paths;
import java.util.Scanner;

public class Day5 {

    public static void main(String[] args) {
        solution();
    }

    public static void solution() {
        // Part 1
        long startTime = System.currentTimeMillis();
        int[][] simpleDataMap = new int[999][999];
        int[][] fullDataMap = new int[999][999]; // With vertical Lines
        
        int overlappingTotal[] = new int[2]; // [0] = Part 1 [1] = Part 2
        
        try (Scanner scanner = new Scanner(Paths.get("day5.txt"))) {
            while (scanner.hasNextLine()) {
                String[] newCoords = scanner.nextLine().split(" -> ");
                int[] beginCoord = { Integer.parseInt(newCoords[0].split(",")[0]), Integer.parseInt(newCoords[0].split(",")[1]) }; // [0] = x, [1] = y
                int[] endCoord = { Integer.parseInt(newCoords[1].split(",")[0]), Integer.parseInt(newCoords[1].split(",")[1]) };

                int xDomain = Math.abs(beginCoord[0] - endCoord[0]);
                int yDomain = Math.abs(beginCoord[1] - endCoord[1]);

                if (xDomain == 0) { // Vertical Line
                    int yOffset = beginCoord[1];
                    if (beginCoord[1] > endCoord[1]) {
                        yOffset = endCoord[1];
                    }

                    for (int i = yOffset; i < yDomain + yOffset + 1; i++) {
                        simpleDataMap[i][beginCoord[0]]++;
                        fullDataMap[i][beginCoord[0]]++;
                    }
                } else if (yDomain == 0) { // Horizontal Line
                    int xOffset = beginCoord[0];
                    if (beginCoord[0] > endCoord[0]) {
                        xOffset = endCoord[0];
                    }

                    for (int i = xOffset; i < xDomain + xOffset + 1; i++) {
                        simpleDataMap[beginCoord[1]][i]++;
                        fullDataMap[beginCoord[1]][i]++;
                    }
                } else { // Part 2, Vertical Line
                    int[] currentCoord = beginCoord;
                    int[] lastCoords = endCoord;

                    if (beginCoord[0] > endCoord[0]) {
                        currentCoord = endCoord;
                        lastCoords = beginCoord;
                    }

                    int direction = (currentCoord[1] - lastCoords[1]) / (currentCoord[0] - lastCoords[0]);
                    while (currentCoord[0] != lastCoords[0] && currentCoord[1] != lastCoords[1]) {
                        fullDataMap[currentCoord[1]][currentCoord[0]]++;
                        currentCoord[0]++;
                        currentCoord[1] += direction;
                    }
                }
            }
            
            for (int i = 0; i < fullDataMap.length; i++) {
                for (int j = 0; j < fullDataMap[i].length; j++) {
                    if (simpleDataMap[i][j] >= 2) { overlappingTotal[0]++; }
                    if (fullDataMap[i][j] >= 2) { overlappingTotal[1]++; }
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        
        System.out.println("Antwoord 1: " + overlappingTotal[0]);
        System.out.println("Antwoord 2: " + overlappingTotal[1]);
        System.out.println("Tijd: " + totalTime);
    }
}
