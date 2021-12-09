package nl.pekict.year2021;

import java.nio.file.Paths;
import java.util.Scanner;

public class Day10 {

    public static void main (String[] args) {
        solution();
    }

    public static void solution() {
        // Part 1
        long startTime = System.currentTimeMillis();

        try (Scanner scanner = new Scanner(Paths.get("day10.txt"))) {

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.println("Antwoord 1: ");
        System.out.println("Antwoord 2: ");
        System.out.println("Tijd: " + totalTime);
    }
}

