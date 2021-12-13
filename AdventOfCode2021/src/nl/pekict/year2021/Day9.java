
package nl.pekict.year2021;

import java.nio.file.Paths;
import java.util.*;

public class Day9 {

    public static void main(String[] args) {
        solution();
    }
    
    public static void solution() {
        // Part 1
        long startTime = System.currentTimeMillis();
        Map<String, Integer> depthMap = new HashMap();
        ArrayList<Integer> basinSizes = new ArrayList<>();
        int totalSum = 0;
        int totalMultiply = 1;

        try (Scanner scanner = new Scanner(Paths.get("day9.txt"))) {
            int index = 0;
            while (scanner.hasNextLine()) {
                String[] pieces = scanner.nextLine().split("");
                for (int i = 0; i < pieces.length; i++) {
                    depthMap.put(index + "-" + i, Integer.parseInt(pieces[i]));
                }
                index++;
            }

            Map<String, Integer> ignoringPositions = new HashMap();
            for (String key : depthMap.keySet()) {
                int currentNumber = depthMap.get(key);
                String[] pieces = key.split("-");
                String[] locations = {
                        pieces[0] + "-" + (Integer.parseInt(pieces[1]) + 1),
                        pieces[0] + "-" + (Integer.parseInt(pieces[1]) - 1),
                        Integer.parseInt(pieces[0]) - 1 + "-" + pieces[1],
                        Integer.parseInt(pieces[0]) + 1 + "-" + pieces[1]
                };
                boolean smallest = true;

                for (String s : locations) {
                    if (depthMap.get(s) != null && currentNumber >= depthMap.get(s)) {
                        smallest = false;
                    }
                }

                if (smallest) { // Is a Basin
                    totalSum += 1 + currentNumber;

                    // Part 2, Calculating the size of the basin
                    int basinSize = 0;
                    ArrayList<String> possibleCoords = new ArrayList<>();
                    possibleCoords.add(key);

                    while (possibleCoords.size() != 0) {
                        String possiblePoint = possibleCoords.get(0);
                        if (ignoringPositions.get(possiblePoint) == null) {
                            String[] currentPieces = possiblePoint.split("-");
                            String[] currentSurroundingLocations = {
                                    currentPieces[0] + "-" + (Integer.parseInt(currentPieces[1]) + 1),
                                    currentPieces[0] + "-" + (Integer.parseInt(currentPieces[1]) - 1),
                                    Integer.parseInt(currentPieces[0]) - 1 + "-" + currentPieces[1],
                                    Integer.parseInt(currentPieces[0]) + 1 + "-" + currentPieces[1]
                            };

                            boolean isSmaller = false;
                            for (String location : currentSurroundingLocations) {
                                if (depthMap.get(location) != null && depthMap.get(location) < 9) {
                                    isSmaller = true;
                                    possibleCoords.add(location);
                                }
                            }

                            ignoringPositions.put(possiblePoint , 0);
                            possibleCoords.remove(possiblePoint);
                            if (isSmaller) {
                                basinSize++;
                            }

                        } else {
                            possibleCoords.remove(possiblePoint);
                        }
                    }
                    basinSizes.add(basinSize);
                }
            }

            basinSizes.sort(Collections.reverseOrder());
            for (int i = 0; i < 3; i++) {
                totalMultiply *= basinSizes.get(i);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.println("Antwoord 1: " + totalSum);
        System.out.println("Antwoord 2: " + totalMultiply);
        System.out.println("Tijd: " + totalTime);
    }
}
