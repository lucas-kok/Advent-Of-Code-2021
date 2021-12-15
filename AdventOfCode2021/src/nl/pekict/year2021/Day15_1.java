package nl.pekict.year2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day15_1 {

    public static void main(String[] args) throws IOException {
        day15("day15.txt");
    }
    public static void day15(String inputFile) throws IOException {
        List<String> input = Files.lines(Paths.get(inputFile))
                .collect(Collectors.toList());

        int[][] risk = input.stream().map(line -> line.chars()
                        .map(Character::getNumericValue).toArray())
                .toArray(size -> new int[size][0]);

        int[][] minrisk = new int[risk.length * 5][risk[0].length * 5];
        for (int[] ints : minrisk) {
            Arrays.fill(ints, Integer.MAX_VALUE);
        }

        minrisk[0][0] = 0;

        boolean done = false;
        int[][] neighbors = new int[][] {new int[] {-1, 0}, new int[] {1, 0}, new int[] {0, -1}, new int[] {0, 1}};

        while (!done) {
            done = true;
            for (int r = 0; r < minrisk.length; r++) {
                for (int c = 0; c < minrisk[r].length; c++) {
                    int currentRisk = minrisk[r][c];
                    for (int[] neighbor : neighbors) {
                        int rr = r + neighbor[0];
                        int cc = c + neighbor[1];
                        if (rr >= 0 && rr < minrisk.length && cc >= 0 && cc < minrisk[r].length) {
                            if (minrisk[rr][cc] > currentRisk + calcRisk(rr, cc, risk)) {
                                done = false;
                                minrisk[rr][cc] = currentRisk + calcRisk(rr, cc, risk);
                            }
                        }
                    }
                }
            }
        }
        System.out.printf("Part 1: %d%n", minrisk[risk.length - 1][risk[0].length - 1]);
        System.out.printf("Part 2: %d%n", minrisk[minrisk.length - 1][minrisk[0].length - 1]);
    }

    public static int calcRisk(int r, int c, int[][] risk) {
        if (r < risk.length && c < risk[r].length) return risk[r][c];
        int adjRisk = 0;
        if (c >= risk[0].length) adjRisk = calcRisk(r, c - risk[0].length, risk) + 1;
        if (r >= risk.length) adjRisk = calcRisk(r - risk.length, c, risk) + 1;
        adjRisk = adjRisk % 10;
        if (adjRisk == 0) adjRisk = 1;
        return adjRisk;
    }
}
