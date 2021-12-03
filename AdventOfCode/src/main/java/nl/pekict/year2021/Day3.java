package nl.pekict.year2021;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Day3 {

    public static void main(String[] args) {
        solution();
    }

    public static void solution() {
        int totalCount = 0;
        int[] zeros = new int[12];
        ArrayList<String> values = new ArrayList<>();
        ArrayList<String> valuesCopy = new ArrayList<>();

        StringBuilder gammaString = new StringBuilder();
        StringBuilder epsilonString = new StringBuilder();

        int gammaValue = 0;
        int epsilonValue = 0;

        int oxygenRating = 0;
        int carbonRating = 0;

        try (Scanner scanner = new Scanner(Paths.get("day3.txt"))) {

            // Part 1
            while (scanner.hasNextLine()) {
                
                // Counting the zero's on all indexes
                String next = scanner.nextLine();
                totalCount++;
                String[] pieces = next.split("");
                for (int i = 0; i < pieces.length; i++) {
                    if (pieces[i].equals("0")) {
                        zeros[i]++;
                    }
                }

                values.add(next);
                valuesCopy.add(next);
            }
            
            for (int zero : zeros) {
                if (zero > totalCount / 2) {
                    gammaString.append("0");
                    epsilonString.append("1");
                } else {
                    gammaString.append("1");
                    epsilonString.append("0");
                }
            }

            gammaValue = Integer.parseInt(gammaString.toString(), 2);
            epsilonValue = Integer.parseInt(epsilonString.toString(), 2);

            // Part 2
            // Calculating the Oxygen Rating
            zeros = new int[12];

            int index = 0;
            while (true) {
                // Calculating all the zero's on the current index
                for (int i = 0; i < values.size(); i++) {
                    if (values.get(i).charAt(index) == '0') {
                        zeros[index]++;
                    }
                }

                // Removing all bits with on the index a 0 when there are more, or equal 1's then 0's
                if (zeros[index] <= values.size() / 2) {
                    for (int j = 0; j < values.size(); j++) {
                        if (values.get(j).charAt(index) == '0') {
                            values.remove(j);
                            j--;
                        }
                    }
                } else {
                    for (int j = 0; j < values.size(); j++) {
                        if (values.get(j).charAt(index) == '1') {
                            values.remove(j);
                            j--;
                        }
                    }
                }

                if (values.size() == 1) {
                    break;
                }

                index++;
            }

            oxygenRating = Integer.parseInt(values.get(0).toString(), 2);

            // Calculating the Carbon Rating
            values = valuesCopy;
            index = 0;
            zeros = new int[12];
            while (true) {
                // Calculating all the zero's on the current index
                for (int i = 0; i < values.size(); i++) {
                    if (values.get(i).charAt(index) == '0') {
                        zeros[index]++;
                    }
                }

                // Removing all bits with on the index a 0 when there are more, or equal 1's then 0's
                if (zeros[index] > values.size() / 2) {
                    for (int j = 0; j < values.size(); j++) {
                        if (values.get(j).charAt(index) == '0') {
                            values.remove(j);
                            j--;
                        }
                    }
                } else {
                    for (int j = 0; j < values.size(); j++) {
                        if (values.get(j).charAt(index) == '1') {
                            values.remove(j);
                            j--;
                        }
                    }
                }

                if (values.size() == 1) {
                    break;
                }

                index++;
            }

            carbonRating = Integer.parseInt(values.get(0).toString(), 2);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("Antwoord 1: " + gammaValue * epsilonValue);
        System.out.println("Antwoord 2: " + oxygenRating * carbonRating);
    }
    
}
