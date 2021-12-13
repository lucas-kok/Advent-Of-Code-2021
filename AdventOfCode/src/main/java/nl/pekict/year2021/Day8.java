package nl.pekict.year2021;

import java.nio.file.Paths;
import java.util.*;

public class Day8 {

    public static void main(String[] args) {
        solution();
    }

    public static void solution() {
        // Part 1
        long startTime = System.currentTimeMillis();
        int numbersCount = 0;
        int totalSum = 0;
        try (Scanner scanner = new Scanner(Paths.get("day8.txt"))) {

            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(" \\| ");
                String[] outputNumberStrings = parts[1].split(" ");
                
                // Part 1
                for (String string : outputNumberStrings) {
                    String[] chars = string.split("");
                    if (chars.length == 2 || chars.length == 3 || chars.length == 4 || chars.length == 7) {
                        numbersCount++;
                    }
                }
                
                // Part 2
                String[] rightOrder = new String[7]; // a = 0, g = 6
                int[] knownValues = {0, 0, 1, 7, 4, 0, 0, 8 };
                String[] knownNumberStrings = new String[9];
                
                // Calculating the right order of letters
                for (String number : parts[0].split(" ")) {
                    int charLength = number.split("").length;
                    switch (charLength) {
                        case 2: // 1
                            knownNumberStrings[1] = number;
                            break;
                        case 3: // 7
                            knownNumberStrings[7] = number;
                            break;
                        case 4: // 4
                            knownNumberStrings[4] = number;
                            break;
                        case 7: // 8
                            knownNumberStrings[8] = number;
                            break;
                        default:
                            break;
                    }
                }

                for (String number : parts[0].split(" ")) {
                    int charLength = number.split("").length;
                    if (charLength == 6) { // 0, 6 or 9
                        int numberOfMatches = 0;
                        for (String aChar : knownNumberStrings[1].split("")) {
                            if (number.contains(aChar)) {
                                numberOfMatches++;
                            }
                        }
                        
                        if (numberOfMatches == 2) { // 0, 9
                            for (String aChar : knownNumberStrings[8].split("")) {
                                if (!number.contains(aChar) && !knownNumberStrings[4].contains(aChar)) {
                                    rightOrder[4] = aChar;
                                } else if (!number.contains(aChar)) {
                                    rightOrder[3] = aChar;
                                }
                            }
                        } else { // 6
                            for (String aChar : knownNumberStrings[1].split("")) {
                                if (number.contains(aChar)) {
                                    rightOrder[5] = aChar;
                                } else {
                                    rightOrder[2] = aChar;
                                }
                            }
                        }
                    }
                }
                
                // Setting the empty letter-slots to the only letter not already known from a certain number
                for (String aChar : knownNumberStrings[7].split("")) {
                    if (!knownNumberStrings[1].contains(aChar)) {
                        rightOrder[0] = aChar;
                    }
                }
                
                for (String aChar : knownNumberStrings[4].split("")) {
                    if (!Arrays.asList(rightOrder).contains(aChar)) {
                        rightOrder[1] = aChar;
                    }
                }
                
                for (String aChar : knownNumberStrings[8].split("")) {
                    if (!Arrays.asList(rightOrder).contains(aChar)) {
                        rightOrder[6] = aChar;
                    }
                }
                
                // Converting the output to one number
                StringBuilder newString = new StringBuilder();
                for (String numberString : parts[1].split(" ")) {
                    int charLength = numberString.split("").length;
                    switch (charLength) {
                        case 2:
                        case 3:
                        case 4:
                        case 7:
                            newString.append(knownValues[charLength]);
                            break;
                        case 5: // 2,3,5
                            if (!numberString.contains(rightOrder[5])) { // 2
                                newString.append(2);
                            } else if (!numberString.contains(rightOrder[1])) { // 3
                                newString.append(3);
                            } else { // 5
                                newString.append(5);
                            }   break;
                        case 6: // 0,6,9
                            if (!numberString.contains(rightOrder[3])) { // 0
                                newString.append(0);
                            } else if (!numberString.contains(rightOrder[2])) { // 6
                                newString.append(6);
                            } else { // 9
                                newString.append(9);
                            }   break;
                        default:
                            break;
                    }
                }
                totalSum += Integer.parseInt(newString.toString());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.println("Antwoord 1: " + numbersCount);
        System.out.println("Antwoord 2: " + totalSum);
        System.out.println("Tijd: " + totalTime);
    }
}
