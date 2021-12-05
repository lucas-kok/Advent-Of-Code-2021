package nl.pekict.year2021;

import java.nio.file.Paths;
import java.util.Scanner;

public class Day1 {

    public static void main(String[] args) {
        solution();
    }

    public static void solution() {
        int increasingNumberCount = 0;
        int previous = -1;

        int increasingRowCount = 0;
        int index = 0;
        int[] numbers = new int[4];

        try (Scanner scanner = new Scanner(Paths.get("day1.txt"))) {
            while (scanner.hasNextLine()) {

                int number = Integer.valueOf(scanner.nextLine());
                numbers[index] = number;

                // Part 1
                if (previous == -1) {
                    previous = number;
                } else {
                    if (number > previous) {
                        increasingNumberCount++;
                    }

                    previous = number;
                }

                // Part 2
                if (index == 3) {
                    int first = numbers[0] + numbers[1] + numbers[2];
                    int second = numbers[1] + numbers[2] + numbers[3];

                    if (second > first) {
                        increasingRowCount++;
                    }
                    numbers = new int[]{numbers[1], numbers[2], numbers[3], 0};
                } else {
                    index++;
                }

            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("Antwoord 1: " + increasingNumberCount);
        System.out.println("Antwoord 2: " + increasingRowCount);
    }
}
