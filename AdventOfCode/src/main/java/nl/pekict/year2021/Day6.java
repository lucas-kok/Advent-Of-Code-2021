package nl.pekict.year2021;

import java.nio.file.Paths;
import java.util.Scanner;

public class Day6 {

    public static void main(String[] args) {
        solution();
    }

    public static void solution() {
        // Part 1
        long startTime = System.currentTimeMillis();
        long[][] groupedFish = new long[9][2]; // [x][0] = days, [x][1] = number of Fish
        int smallTotalFish = 0;
        long totalFish = 0;

        try (Scanner scanner = new Scanner(Paths.get("day6.txt"))) {
            String[] pieces = scanner.nextLine().split(",");
            for (String piece : pieces) {
                groupedFish[Integer.parseInt(piece)][1]++;
            }

            for (int i = 0; i < groupedFish.length; i++) {
                groupedFish[i][0] = i;
            }

            for (int i = 0; i < 256; i++) {
                long clones = 0;
                for (long[] groupedFishSelection : groupedFish) {
                    groupedFishSelection[0]--;
                    if (groupedFishSelection[0] == -1) {
                        clones = groupedFishSelection[1];
                        groupedFishSelection[0] = 8;
                    }
                }

                for (long[] groupedFishSelection : groupedFish) {
                    if (groupedFishSelection[0] == 6) {
                        if (i < 80) { smallTotalFish += clones; }
                        groupedFishSelection[1] += clones; // Part 2
                    }
                }
            }

            for (long[] groupedFishSelection : groupedFish) {
                totalFish += groupedFishSelection[1];
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        
        System.out.println("Antwoord 1: " + smallTotalFish);
        System.out.println("Antwoord 2: " + totalFish);
        System.out.println("Tijd: " + totalTime);
    }
}
