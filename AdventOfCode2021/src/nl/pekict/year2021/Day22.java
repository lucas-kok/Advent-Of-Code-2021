package nl.pekict.year2021;

import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Day22 {

    public static void main(String[] args) {
        solution();
    }

    public static void solution() {
        // Part 1
        long startTime = System.currentTimeMillis();
        int cubeCount = 0;

        try (Scanner scanner = new Scanner(Paths.get("day22.txt"))) {
            Set<String> positions = new HashSet<>();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                int command = line.split(" ")[0].equals("on") ? 1 : 0;
                String[] pieces = line.split(" ")[1].split(",");

                int[][] bounds = new int[][]{
                        { Integer.parseInt(pieces[0].split("x=")[1].split("\\.\\.")[0]), Integer.parseInt(pieces[0].split("x=")[1].split("\\.\\.")[1]) },
                        { Integer.parseInt(pieces[1].split("y=")[1].split("\\.\\.")[0]), Integer.parseInt(pieces[1].split("y=")[1].split("\\.\\.")[1]) },
                        { Integer.parseInt(pieces[2].split("z=")[1].split("\\.\\.")[0]), Integer.parseInt(pieces[2].split("z=")[1].split("\\.\\.")[1]) }
                };

                bounds[0] = new int[] { Math.max(bounds[0][0], -50), Math.min(bounds[0][1], 50) };
                bounds[1] = new int[] { Math.max(bounds[1][0], -50), Math.min(bounds[1][1], 50) };
                bounds[2] = new int[] { Math.max(bounds[2][0], -50), Math.min(bounds[2][1], 50) };

                for (int x = bounds[0][0]; x < bounds[0][1] + 1; x++) {
                    for (int y = bounds[1][0]; y < bounds[1][1] + 1; y++) {
                        for (int z = bounds[2][0]; z < bounds[2][1] + 1; z++) {
                            if (command == 1) {
                                positions.add(x + "." + y + "." + z);
                            } else {
                                positions.remove(x + "." + y + "." + z);
                            }
                        }
                    }
                }
            }
            cubeCount = positions.size();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.println("Antwoord 1: " + cubeCount);
        System.out.println("Antwoord 2: ");
        System.out.println("Tijd: " + totalTime + "ms");
    }
}
