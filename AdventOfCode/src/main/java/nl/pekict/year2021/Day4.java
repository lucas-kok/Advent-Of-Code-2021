package nl.pekict.year2021;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Day4 {

    public static void main(String[] args) {
        solution();
    }

    public static void solution() {

        // Part 1
        ArrayList<Integer> drawingNumbers = new ArrayList<>();
        ArrayList<Integer> drawedNumbers = new ArrayList<>();

        ArrayList<int[][]> boards = new ArrayList<>();
        Map<int[][], int[][]> count = new HashMap<>(); // Keeps track of the boards checked numbers

        int winnerTotal = 0;
        int lastTotal = 0;

        try (Scanner scanner = new Scanner(Paths.get("day4.txt"))) {

            // Processing all the inputs
            int[][] newBoard = new int[5][5];
            int row = 0;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (drawingNumbers.isEmpty()) { // Adding all the drawing numbers
                    String[] pieces = line.split(",");
                    for (String piece : pieces) {
                        drawingNumbers.add(Integer.parseInt(piece));
                    }
                } else if (!line.isBlank()) { // Creating a board
                    line = line.replaceAll(" +", " ");
                    line = line.trim();
                    String pieces[] = line.split(" ");

                    for (int i = 0; i < pieces.length; i++) {
                        newBoard[row][i] = Integer.parseInt(pieces[i]);
                    }

                    row++;
                }

                if (row == 5) {
                    boards.add(newBoard);
                    count.put(newBoard, new int[2][5]);
                    
                    newBoard = new int[5][5];
                    row = 0;
                }
            }
            
            for (Integer drawingNumber : drawingNumbers) {
                
                // Checking if board has the drawed number
                for (int[][] board : boards) {
                    for (int i = 0; i < board.length; i++) { // Rows

                        for (int j = 0; j < board[i].length; j++) { // Columns
                            if (board[i][j] == drawingNumber) {
                                count.get(board)[0][j]++;
                                count.get(board)[1][i]++;
                            }
                        }
                    }
                }

                drawedNumbers.add(drawingNumber);

                // Checking for a winner
                if (lastTotal == 0) {
                    for (int[][] board : count.keySet()) {
                        int[][] countings = count.get(board);

                        for (int i = 0; i < countings.length; i++) {
                            for (int j = 0; j < countings[i].length; j++) {
                                if (i == 0 && countings[i][j] == 5 || i == 1 && countings[i][j] == 5) { // Board has a full row/column
                                    if (winnerTotal == 0) { // First Winner
                                        for (int k = 0; k < board.length; k++) { // Checking what number are not drawed, Summing them
                                            for (int l = 0; l < board[k].length; l++) {
                                                if (!drawedNumbers.contains(board[k][l])) {
                                                    winnerTotal += board[k][l];
                                                }
                                            }
                                        }
                                        winnerTotal *= drawingNumber;
                                    } else if (boards.size() == 1 && boards.indexOf(board) == 0) { // Part 2, Last Winner
                                        for (int k = 0; k < board.length; k++) { 
                                            for (int l = 0; l < board[k].length; l++) {
                                                if (!drawedNumbers.contains(board[k][l])) {
                                                    lastTotal += board[k][l];
                                                }
                                            }
                                        }
                                        lastTotal *= drawingNumber;
                                    } else {
                                        boards.remove(board);
                                    }
                                }
                            }
                        }
                    }
                }

            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        System.out.println("Antwoord 1: " + winnerTotal);
        System.out.println("Antwoord 2: " + lastTotal);
    }

}
