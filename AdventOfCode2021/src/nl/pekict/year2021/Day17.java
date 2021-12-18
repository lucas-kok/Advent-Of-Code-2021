package nl.pekict.year2021;

import java.nio.file.Paths;
import java.util.Scanner;

public class Day17 {

    public static void main(String[] args) {
        solution();
    }

    public static void solution() {
        // Part 1
        long startTime = System.currentTimeMillis();
        int maxY = 0;
        int velocityCount = 0;

        try (Scanner scanner = new Scanner(Paths.get("day17.txt"))) {
            if (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] targetPosition = line.split("target area: ")[1].split(", ");
                String[] xBoundsString = targetPosition[0].replace("x=", "").split("\\.\\.");
                String[] yBoundString = targetPosition[1].replace("y=", "").split("\\.\\.");
                int[] xBounds = new int[] { Integer.parseInt(xBoundsString[0]), Integer.parseInt(xBoundsString[1]) };
                int[] yBounds = new int[] { Integer.parseInt(yBoundString[0]), Integer.parseInt(yBoundString[1]) };

                for (int x = 0; x <= xBounds[1]; x++) {
                    for (int y = yBounds[0]; y <= Math.abs(yBounds[0]) * 2; y++) {
                        int[] velocity = new int[] { x, y };
                        int[] position = new int[] { 0, 0 };
                        int currentTop = position[1];
                        while(true) {
                            position[0] += velocity[0];
                            position[1] += velocity[1];

                            if (velocity[0] > 0) { velocity[0]--; }
                            else if (velocity[0] < 0) { velocity[0]++; }
                            velocity[1]--;

                            currentTop = Math.max(currentTop, position[1]);

                            if (position[0] >= xBounds[0] && position[0] <= xBounds[1] && position[1] >= yBounds[0] && position[1] <= yBounds[1]) {
                                velocityCount++; // Part 2
                                maxY = Math.max(currentTop, maxY);
                                break;
                            } else if (position[0] > xBounds[1] || position[1] < yBounds[0]) { // Passed the target
                                break;
                            }

                        }
                    }
                }

            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.println("Antwoord 1: " + maxY);
        System.out.println("Antwoord 2: " + velocityCount);
        System.out.println("Tijd: " + totalTime);
    }
}
