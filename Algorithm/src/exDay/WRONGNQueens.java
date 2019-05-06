package jpa.example.demo.algo;

public class WRONGNQueens {
    static int N = 4;
    static int[] map = new int[N];
    public static void main(String[] args) {
        backTrack(0);
    }

    private static void backTrack(int row) {
        if (row == N) {
            for (int i = 0; i < N; i++) {
                System.out.print(map[i] + " ");
            }
            System.out.println();
            return;
        }

        for (int i = 0; i < N; i++) {
            map[row] = i;
            if (isPassible(row)) backTrack(row + 1);
        }
    }

    private static boolean isPassible(int row) {
        for (int i = 0; i < row; i++) {
            if (map[i]==map[row] || Math.abs(row-i)== Math.abs(map[row]-map[i])) {
                return false;
            }
        }
        return true;
    }
}
