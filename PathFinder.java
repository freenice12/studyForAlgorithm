package jpa.example.demo.algo;

import java.util.Queue;
import java.util.Stack;

public class PathFinder {

    /**
     * 0011
     * 0101
     * 0000
     */

    static int[][] map = new int[][]{
            {0, 0, 1, 1},
            {0, 1, 0, 1},
            {0, 0, 0, 0}
    };

    static int endX = 3;
    static int endY = 2;
    static boolean hasFind;

    static void find(int x, int y) {
        if (hasFind) return;
        if (endX == x && endY == y) {
            System.out.println("FIND!");
            hasFind = true;
            return;
        }
        map[y][x] = 2;
        printMap();
        if (x + 1 < map[0].length && map[y][x + 1] == 0) find(x + 1, y);
        if (x - 1 >= 0 && map[y][x - 1] == 0) find(x - 1, y);
        if (y + 1 < map.length && map[y + 1][x] == 0) find(x, y + 1);
        if (y - 1 >= 0 && map[y - 1][x] == 0) find(x, y - 1);
    }

    private static void printMap() {
        System.out.println("==================================");
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        find(0, 0);
    }
}
