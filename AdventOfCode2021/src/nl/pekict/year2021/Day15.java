package nl.pekict.year2021;

import java.nio.file.Paths;
import java.util.Scanner;

public class Day15 {

    public static void main(String[] args) {
        solution();
    }

    // Doesn't give the right solution, offset of a few
    public static void solution() {
        // Part 1
        long startTime = System.currentTimeMillis();
        int smallMapCost = 0;
        int bigMapCost = 0;

        try (Scanner scanner = new Scanner(Paths.get("day15.txt"))) {
            int[][] map = new int[500][500];
            int[][] valuesMap = new int[500][500];

            int rowIndex = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] pieces = line.split("");
                for (int i = 0; i < pieces.length; i++) {
                    int newNumber = Integer.parseInt(pieces[i]);
                    for (int j = 0; j < 5; j++) {
                        if (newNumber > 9) {
                            newNumber = 1;
                        }
                        map[rowIndex][i + (j * 100)] = newNumber;
                        newNumber++;
                    }
                }
                rowIndex++;
            }

            for (int i = 0; i < 100; i++) {
                for (int j = 0; j < 500; j++) {
                    int number = map[i][j];
                    for (int k = 1; k < 5; k++) {
                        number++;
                        if (number > 9) {
                            number = 1;
                        }
                        map[i + (k * 100)][j] = number;
                    }
                }
            }

            for (int i = 1; i < valuesMap[0].length; i++) {
                valuesMap[0][i] = valuesMap[0][i - 1] + map[0][i];
                valuesMap[i][0] = valuesMap[i - 1][0] + map[i][0];
            }

            for (int i = 1; i < valuesMap.length; i++) {
                for (int j = 1; j < valuesMap[i].length; j++) {
                    int value1 = valuesMap[i][j - 1] + map[i][j];
                    int value2 = valuesMap[i - 1][j] + map[i][j];

                    valuesMap[i][j] = Math.min(value1, value2);
                }
            }
            smallMapCost = valuesMap[99][99]; // Part 2
            bigMapCost = valuesMap[valuesMap.length - 1][valuesMap.length - 3];
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.println("Antwoord 1: " + smallMapCost);
        System.out.println("Antwoord 2: " + bigMapCost);
        System.out.println("Tijd: " + totalTime);
    }
}
