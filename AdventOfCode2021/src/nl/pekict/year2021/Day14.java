package nl.pekict.year2021;

import java.nio.file.Paths;
import java.util.*;

public class Day14 {

    public static void main(String[] args) {
        solution();
    }

    public static void solution() {
        long startTime = System.currentTimeMillis();
        long smallDifference = 0;
        long bigDifference = 0;

        try (Scanner scanner = new Scanner(Paths.get("day14.txt"))) {
            // Part 1
            long[][] pairsGrid = new long[26][26];
            long[][] newPairsGrid;
            char[] letters = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
            Map<String, String> values = new HashMap<>();

            char lastChar = 'x';
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (lastChar == 'x') {
                    char[] pieces = line.toCharArray();
                    for (int i = 0; i < pieces.length - 1; i++) {
                        pairsGrid[new String(letters).indexOf(pieces[i])][new String(letters).indexOf(pieces[i + 1])]++;
                    }
                    lastChar = pieces[pieces.length - 1];
                } else if (!line.isBlank()) {
                    String[] pieces = line.split(" -> ");
                    values.put(pieces[0], pieces[1]);
                }
            }

            for (int i = 0; i < 40; i++) {
                newPairsGrid = new long[26][26];
                for (int r = 0; r < pairsGrid.length; r++) {
                    for (int c = 0; c < pairsGrid[r].length; c++) {
                        if (pairsGrid[r][c] != 0) {
                            String pair = String.valueOf(letters[r]) + letters[c];
                            char[] chars = pair.toCharArray();
                            if (values.get(pair) != null) {
                                newPairsGrid[new String(letters).indexOf(chars[0])][new String(letters).indexOf(values.get(pair).charAt(0))] += pairsGrid[r][c];
                                newPairsGrid[new String(letters).indexOf(values.get(pair).charAt(0))][new String(letters).indexOf(chars[1])] += pairsGrid[r][c];
                            }
                        }
                    }
                }
                pairsGrid = newPairsGrid;
                if (i == 9) { smallDifference = calculateDifference(pairsGrid, letters, lastChar); }
            }
            bigDifference = calculateDifference(pairsGrid, letters, lastChar); // Part 2
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.println("Antwoord 1: " + smallDifference);
        System.out.println("Antwoord 2: " + bigDifference);
        System.out.println("Tijd: " + totalTime);
    }

    public static long calculateDifference(long[][] pairsGrid, char[] letters, char lastChar) {
        long[] count = new long[26];
        for (int r = 0; r < pairsGrid.length; r++) {
            for (int c = 0; c < pairsGrid[r].length; c++) {
                count[r] += pairsGrid[r][c];
            }
        }
        count[new String(letters).indexOf(lastChar)]++;

        Arrays.sort(count);
        for (long number : count) {
            if (number != 0) {
                return count[count.length - 1] - number;
            }
        }
        return -1;
    }
}
