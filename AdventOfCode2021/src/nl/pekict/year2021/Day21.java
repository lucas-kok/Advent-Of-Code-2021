package nl.pekict.year2021;

public class Day21 {

    public static void main(String[] args) {
        solution();
    }

    public static void solution() {
        // Part 1
        long startTime = System.currentTimeMillis();

        int[][] players = { // [][0] = Position, [][1] = Score
                { 8, 0 },
                { 2, 0 }
        };

        int losingPlayerMultiply = diracDice(players);

        quantumDice(0, players[0][0], players[1][0],0, 0, 1);
        long quantumWinCount = Math.max(playerWinCount[0], playerWinCount[1]);

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.println("Antwoord 1: " + losingPlayerMultiply);
        System.out.println("Antwoord 2: " + quantumWinCount);
        System.out.println("Tijd: " + totalTime + "ms");
    }

    public static int diracDice(int[][] players) {
        int[][] localPlayers = new int[][]{
                { players[0][0], players[0][1] },
                { players[1][0], players[1][1] }
        };

        int dice = 0;
        int rolCount = 0;
        int turn = 0;
        while (true) {
            for (int i = 0; i < 3; i++) {
                dice = dice < 100 ? dice + 1 : 1;
                rolCount++;
                localPlayers[turn][0] += dice;
            }

            while(localPlayers[turn][0] > 10) localPlayers[turn][0] -= 10;

            localPlayers[turn][1] += localPlayers[turn][0];
            if (localPlayers[turn][1] >= 1000 ) return localPlayers[turn == 1 ? 0 : 1][1] * rolCount;

            turn = turn == 0 ? 1 : 0;
        }
    }

    private static final int[] pathCount = { 0, 0, 0, 1, 3, 6, 7, 6, 3, 1 };
    private static final long[] playerWinCount = { 0, 0 };

    public static void quantumDice(int turn,  int p1Space, int p2Space, int p1Score, int p2Score, long paths) {
        playerWinCount[0] += p1Score >= 21 ? paths : 0;
        playerWinCount[1] += p2Score >= 21 ? paths : 0;
        if (p1Score < 21 && p2Score < 21){
            for (int i = 3; i < 10; i++) {
                int number = turn == 0 ? (p1Space + i) % 10 : (p2Space + i) % 10;
                int newSpace = number == 0 ? 10 : number;

                if (turn == 0) quantumDice(1,  newSpace, p2Space, p1Score + newSpace, p2Score,paths * pathCount[i]);
                else quantumDice(0,  p1Space, newSpace, p1Score, p2Score + newSpace, paths * pathCount[i]);
            }
        }
    }
}
