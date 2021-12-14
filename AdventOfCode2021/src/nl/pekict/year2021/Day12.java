package nl.pekict.year2021;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Day12 {

    public static void main(String[] args) {
        solution();
    }

    public static void solution() {
        long startTime = System.currentTimeMillis();

        try (Scanner scanner = new Scanner(Paths.get("day12.txt"))) {
            ArrayList<String> lines = new ArrayList<>();
            ArrayList<String> paths = new ArrayList<>();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lines.add(scanner.nextLine());
                String[] pieces = line.split("-");

                if (pieces[0].equals("start")) {
                    paths.add(pieces[0] + "." + pieces[1]);
                } else if (pieces[1].equals("start")) {
                    paths.add(pieces[1] + "." + pieces[0]);
                }
            }

            System.out.println(paths);
        } catch (Exception e) {
            System.out.printf("Error: " + e.getMessage());
        }

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.println("Antwoord 1: ");
        System.out.println("Antwoord 2: ");
        System.out.println("Tijd: " + totalTime);
    }
}