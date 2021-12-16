package nl.pekict.year2021;

import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiFunction;

public class Day16 {

    public static void main(String[] args) {
        solution();
    }

    public static void solution() {
        // Part 1
        long startTime = System.currentTimeMillis();
        long output = 0;

        try (Scanner scanner = new Scanner(Paths.get("day16.txt"))) {
            List<BiFunction<Long, Long, Long>> operations = new ArrayList<>();
            operations.add(Long::sum);
            operations.add((a, b) -> a * b);
            operations.add((a, b) -> a < b ? a : b);
            operations.add((a, b) -> a > b ? a : b);
            operations.add((a, b) -> null);
            operations.add((a, b) -> a > b ? 1L : 0L);
            operations.add((a, b) -> a < b ? 1L : 0L);
            operations.add((a, b) -> a.equals(b) ? 1L : 0L);

            if (scanner.hasNextLine()) {
                String binary = hexToBinary(scanner.nextLine());
                output = binaryToNumber(binary, operations);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.println("Antwoord 1: " + versionSum);
        System.out.println("Antwoord 2: " + output);
        System.out.println("Tijd: " + totalTime);
    }

    public static String hexToBinary(String hex) {
        Map<Character, String> values = new HashMap<>();
        values.put('0', "0000");
        values.put('1', "0001");
        values.put('2', "0010");
        values.put('3', "0011");
        values.put('4', "0100");
        values.put('5', "0101");
        values.put('6', "0110");
        values.put('7', "0111");
        values.put('8', "1000");
        values.put('9', "1001");
        values.put('A', "1010");
        values.put('B', "1011");
        values.put('C', "1100");
        values.put('D', "1101");
        values.put('E', "1110");
        values.put('F', "1111");

        char[] pieces = hex.toCharArray();
        StringBuilder binaryOutput = new StringBuilder();
        for (Character iteratorChar : pieces) {
            if (values.get(iteratorChar) != null) {
                binaryOutput.append(values.get(iteratorChar));
            }
        }

        return binaryOutput.toString();
    }

    static int versionSum = 0;
    static int index = 0;
    public static long binaryToNumber(String bitString, List<BiFunction<Long, Long, Long>> operations) {
        int version = Integer.parseInt(bitString.substring(index, index + 3), 2);
        int idNumber = Integer.parseInt(bitString.substring(index + 3, index + 6), 2);
        versionSum += version;
        index += 6;

        if (idNumber == 4) { // Literal Value
            StringBuilder fourBits = new StringBuilder();
            boolean lastPair = false;

            while (!lastPair) {
                String fourBitsString = bitString.substring(index, index + 5);
                if (fourBitsString.charAt(0) == '0') {
                    lastPair = true;
                }
                fourBits.append(fourBitsString.substring(1));
                index += 5;
            }
            return Long.parseLong(fourBits.toString(), 2);
        } else { // Operator Package, Part 2
            int lengthTypeId = Integer.parseInt(bitString.substring(index, index + 1), 2);
            int l = 0;

            if (lengthTypeId == 0) { l = 15; }
            else { l = 11; }
            int bitLength = Integer.parseInt(bitString.substring(index + 1, index + 1 + l), 2);
            index += l + 1;

            List<Long> results = new ArrayList<>();
            if (lengthTypeId == 0) {
                while (bitLength > 0) {
                    int begin = index;
                    results.add(binaryToNumber(bitString, operations));
                    bitLength -= (index - begin);
                }
            } else {
                for (int i = 0; i < bitLength; i++) {
                    results.add(binaryToNumber(bitString, operations));
                }
            }

            return results.stream()
                    .reduce((a, b) -> operations.get(idNumber)
                            .apply(a, b)).orElseThrow();
        }
    }
}
