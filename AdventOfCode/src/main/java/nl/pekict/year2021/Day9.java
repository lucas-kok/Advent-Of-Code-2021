
package nl.pekict.year2021;

import java.nio.file.Paths;
import java.util.*;

public class Day9 {

    public static void main(String[] args) {
        solution();
    }
    
    public static void solution() {
        // Part 1
        Map<String, Integer> depthMap = new HashMap();
        ArrayList<String> basins = new ArrayList<>();
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

            // Part 2, Not the right answer
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

                for (int i = 0; i < locations.length; i++) {
                    if (depthMap.get(locations[i]) != null && currentNumber >= depthMap.get(locations[i])) {
                        smallest = false;
                    }
                }

                if (smallest) {
                    totalSum += 1 + currentNumber;
                    int basinSize = 0;

                    int xPosition = Integer.parseInt(pieces[1]);
                    int newYPosition = Integer.parseInt(pieces[0]);
                    int yPosition = Integer.parseInt(pieces[0]);
                    while (true) { // Calculating the point the most to the right
                        if (depthMap.get(yPosition + "-" + (xPosition + 1)) != null && depthMap.get(yPosition + "-" + (xPosition + 1)) != 9) {
                            xPosition++;
                            while (depthMap.get(yPosition - 1 + "-" + xPosition) != null && depthMap.get(yPosition - 1 + "-" + xPosition) != 9) {
                                yPosition--;
                            }
                        } else if (depthMap.get(yPosition + 1 + "-" + xPosition) != null && depthMap.get(yPosition + 1 + "-" + xPosition) != 9){
                            yPosition++;
                        } else {
                            String key1 = yPosition + "-" + (xPosition + 1);
                            if (depthMap.get(key1) == null || depthMap.get(key1) == 9){
                                break;
                            }
                        }
                    }

                    while (true) { // Walking back to the most left point
                        while (depthMap.get(yPosition + 1 + "-" + xPosition) != null && depthMap.get(yPosition + 1 + "-" + xPosition) != 9 ) {
                            yPosition++;
                        }

                        while (depthMap.get(yPosition + "-" + xPosition) != null && depthMap.get(yPosition + "-" + xPosition) != 9) {
                            String key1 = yPosition + "-" + (xPosition - 1);
                            if (depthMap.get(key1) != null && depthMap.get(key1) != 9) {
                                newYPosition = yPosition;
                            }

                            basinSize++;
                            yPosition--;
                        }

                        xPosition--;
                        yPosition = newYPosition;
                        if (depthMap.get(yPosition + "-" + xPosition) == null || depthMap.get(yPosition + "-" + xPosition) == 9) {
                            break;
                        }
                    }
                    basinSizes.add(basinSize);
                }
            }

            Collections.sort(basinSizes, Collections.reverseOrder());
            for (int i = 0; i < 3; i++) {
                totalMultiply *= basinSizes.get(i);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("Antwoord 1: " + totalSum);
        System.out.println("Antwoord 2: " + totalMultiply);
    }
}
