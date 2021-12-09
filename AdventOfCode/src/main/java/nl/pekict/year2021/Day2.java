package nl.pekict.year2021;

import java.nio.file.Paths;
import java.util.Scanner;

public class Day2 {

    public static void main(String[] args) {
        solution();
    }

    public static void solution() {
        // Part 1
        long startTime = System.currentTimeMillis();
        int[] location = new int[2];
        int aim = 0;
        int aimDepth = 0;

        try (Scanner scanner = new Scanner(Paths.get("day2.txt"))) {
            while (scanner.hasNextLine()) {
                String[] inputPieces = scanner.nextLine().split(" ");
                String command = inputPieces[0];
                int value = Integer.valueOf(inputPieces[1]);

                switch (command) {
                    case "forward":
                        location[0] += value;
                        aimDepth += value * aim; // Part 2
                        break;
                    case "up":
                        location[1] -= value;
                        aim -= value;
                        break;
                    case "down":
                        location[1] += value;
                        aim += value;
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.println("Antwoord 1: " + location[0] * location[1]);
        System.out.println("Antwoord 2: " + location[0] * aimDepth);
        System.out.println("Tijd: " + totalTime);
    }
}
