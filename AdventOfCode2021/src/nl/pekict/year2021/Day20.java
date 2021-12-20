package nl.pekict.year2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Array;
import java.util.*;
import java.util.stream.Collectors;

public class Day20 {

    public static void main(String[] args) throws IOException {
        solution();
    }

    public static void solution() {
        long startTime = System.currentTimeMillis();
        int firstPixelCount = 0;
        int secondPixelCount = 0;

        try (Scanner scanner = new Scanner(Paths.get("day20.txt"))) {
            List<String> lines = new ArrayList<>();
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }

            String algoritmValues = lines.get(0);
            List<String> map = lines.subList(2, lines.size());
            char infinity = '.';
            for (int i = 0; i < 50; i++) {
                List<String> newMap = new ArrayList<>();
                for (int r = -1; r < map.size() + 1; r++) {
                    StringBuilder newRow = new StringBuilder();
                    for (int c = -1; c < map.get(0).length() + 1; c++) {
                        int index = 0;
                        int bitmask = 0B100000000;
                        for (int y = r - 1; y <= r + 1; y++) { // Positions around cel
                            for (int x = c - 1; x <= c + 1; x++) {
                                char currentCel = (y < 0 || y >= map.size() || x < 0 || x >= map.get(0).length()) ? infinity : map.get(y).charAt(x);
                                index |= currentCel == '#' ? bitmask : 0;
                                bitmask >>= 1;
                            }
                        }
                        newRow.append(algoritmValues.charAt(index));
                    }
                    newMap.add(newRow.toString());
                }
                infinity = algoritmValues.charAt(infinity == '.' ? 0 : 0B111111111);
                map = newMap;

                if (i == 1 || i == 49) {
                    int count = 0;
                    for (String row : map) {
                        String[] pieces = row.split("");
                        for (String piece : pieces) {
                            if (piece.equals("#")) {
                                count++;
                            }
                        }
                    }
                    firstPixelCount = i == 1 ? count : firstPixelCount;
                    secondPixelCount = i == 49 ? count : secondPixelCount;
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.println("Antwoord 1: " + firstPixelCount);
        System.out.println("Antwoord 2: " + secondPixelCount);
        System.out.println("Tijd: " + totalTime);
    }
}
