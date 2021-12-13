package nl.pekict.year2021;

import java.nio.file.Paths;
import java.util.*;

public class Day11 {

    public static void main(String[] args) {
        solution();
    }

    public static void solution() {
        // Part 1
        long startTime = System.currentTimeMillis();
        int flashCount = 0;
        int syncIndex = 0;

        try (Scanner scanner = new Scanner(Paths.get("day11.txt"))) {
            int[][] grid = new int[10][10];

            int row = 0;
            while (scanner.hasNextLine()) {
                String[] pieces = scanner.nextLine().split("");
                for (int i = 0; i < pieces.length; i++) {
                    grid[row][i] = Integer.parseInt(pieces[i]);
                }
                row++;
            }

            int subCount = 0;
            while (subCount != 100) { // Part 1 & 2
                subCount = 0;
                for (int i = 0; i < grid.length; i++) {
                    for (int j = 0; j < grid[i].length; j++) {
                        if (grid[i][j] == -1) {
                            grid[i][j] += 2;
                        } else {
                            grid[i][j]++;
                        }
                    }
                }

                boolean repeating = true;
                while (repeating) { // Stops when no new squid flashed
                    repeating = false;
                    for (int i = 0; i < grid.length; i++) {
                        for (int j = 0; j < grid[i].length; j++) {
                            if (grid[i][j] > 9) {
                                grid[i][j] = -1;
                                repeating = true;

                                if (syncIndex < 100) { flashCount++; }
                                subCount++;

                                int[][] locations = {
                                        { i - 1, j - 1},
                                        { i - 1, j },
                                        { i -  1, j + 1},
                                        { i, j - 1},
                                        { i, j + 1},
                                        { i + 1, j - 1},
                                        { i + 1, j },
                                        {i + 1, j + 1}
                                };

                                for (int[] location : locations) {
                                    if ((location[0] >= 0 && location[0] < grid.length) && (location[1] >= 0 && location[1] < grid.length)) {
                                        if (grid[location[0]][location[1]] != -1) {
                                            grid[location[0]][location[1]]++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                syncIndex++;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.println("Antwoord 1: " + flashCount);
        System.out.println("Antwoord 2: " + syncIndex);
        System.out.println("Tijd: " + totalTime + "ms");
    }
}
