package nl.pekict.year2021;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Day10 {

    public static void main (String[] args) {
        solution();
    }

    public static void solution() {
        // Part 1
        long startTime = System.currentTimeMillis();
        int syntaxErrorScore = 0;
        ArrayList<Long> incorrectValues = new ArrayList<>();

        try (Scanner scanner = new Scanner(Paths.get("day10.txt"))) {
            while (scanner.hasNextLine()) {
                String[] pieces = scanner.nextLine().split("");
                String[] openChunks = new String[110];

                String[] flippedSymbols = { ")", "]", "}", ">" ,"(", "[", "{", "<" };
                int[] values = { 3, 57, 1197, 25137 };
                boolean incorrect = true;

                int openIndex = 0;

                for (String piece : pieces) {
                    if (piece.equals("(") || piece.equals("[") || piece.equals("{") || piece.equals("<")) {
                        openChunks[openIndex] = piece;
                        openIndex++;
                    } else  if (openChunks[openIndex - 1].equals(flippedSymbols[Arrays.asList(flippedSymbols).indexOf(piece) + 4])){
                        openChunks[openIndex - 1] = null;
                        openIndex--;
                    } else if (incorrect) {
                        syntaxErrorScore += values[Arrays.asList(flippedSymbols).indexOf(piece)];
                        incorrect = false;
                    }
                }

                // Part 2
                if (incorrect) {
                    long incorrectValue = 0;
                    for (int i = openIndex; i > -1; i--) {
                        String openChunk = openChunks[i];
                        if (openChunk != null) {
                            incorrectValue *= 5;
                            incorrectValue += Arrays.asList(flippedSymbols).indexOf(openChunk) - 3;
                        }
                    }
                    incorrectValues.add(incorrectValue);
                }
            }
            Collections.sort(incorrectValues);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.println("Antwoord 1: " + syntaxErrorScore);
        System.out.println("Antwoord 2: " + incorrectValues.get(incorrectValues.size() / 2));
        System.out.println("Tijd: " + totalTime + "ms");
    }
}

