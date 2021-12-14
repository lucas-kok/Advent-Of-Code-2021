package nl.pekict.year2021;

import java.nio.file.Paths;
import java.util.*;

public class Day13 {

    public static void main(String[] args) {
        solution();
    }

    public static void solution() {
        // Part 1
        long startTime = System.currentTimeMillis();
        int dotCount = 0;
        int[][] grid = new int[895][1311];

        try (Scanner scanner = new Scanner(Paths.get("day13.txt"))) {

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (line.isBlank()) { break; }

                String[] pieces = line.split(",");
                grid[Integer.parseInt(pieces[1])][Integer.parseInt(pieces[0])] = 1;
            }

            while (scanner.hasNextLine()) {
                String[] pieces = scanner.nextLine().split(("fold along "));
                String[] information = pieces[1].split("=");
                int[][] newGrid;
                if (information[0].equals("x")) {
                    newGrid = new int[grid.length][(grid[0].length - 1) / 2];
                    for (int i = 0; i < grid.length; i++) {
                        for (int j = 0; j < Integer.parseInt(information[1]); j++) {
                            if (grid[i][j] == 1) { newGrid[i][j] = 1; }
                        }

                        for (int j = grid[0].length - 1; j >= Integer.parseInt(information[1]); j--) {
                            if (grid[i][j] == 1) { newGrid[i][grid[0].length - 1 - j] = 1; }
                        }
                    }
                } else {
                    newGrid = new int[(grid.length - 1) / 2][grid[0].length];
                    for (int i = 0; i < Integer.parseInt(information[1]); i++) {
                        for (int j = 0; j < grid[0].length; j++) {
                            if (grid[i][j] == 1) { newGrid[i][j] = 1; }
                        }
                    }

                    for (int i = grid.length - 1; i >= Integer.parseInt(information[1]); i--) {
                        for (int j = 0; j < grid[0].length; j++) {
                            if (grid[i][j] == 1) { newGrid[grid.length - 1 - i][j] = 1; }
                        }
                    }
                }
                grid = newGrid;
            }

            for (int[] ints : grid) {
                for (int anInt : ints) {
                    if (anInt == 1) {
                        dotCount++;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.println("Antwoord 1: " + dotCount);
        System.out.println("Antwoord 2: ");
        for (int[] ints : grid) {
            for (int anInt : ints) {
                if (anInt == 1) {
                    System.out.print("[#]");
                } else {
                    System.out.print("   ");
                }
            }
            System.out.println();
        }

        System.out.println("Tijd: " + totalTime + "ms");
    }
}
