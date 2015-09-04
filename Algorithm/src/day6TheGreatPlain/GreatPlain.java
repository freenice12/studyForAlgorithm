package day6TheGreatPlain;

import java.awt.Point;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class GreatPlain {

	private Random random = new Random();
	private Map<Point, Integer> board = new HashMap<>();
	private int setSize;
	private int size;
	private int avgNum;
	
	public GreatPlain() {
		size = setSize = 41;
	}

	public static void main(String[] args) {
		GreatPlain gp = new GreatPlain();
		gp.init();
		gp.printBoard();
		gp.fillNumber();
		gp.printBoard();
		gp.printResultNum();
		BoardViewer viewer = new BoardViewer(gp.getBoard());
		viewer.init();
	}


	@SuppressWarnings("boxing")
	private void printResultNum() {
		int result = 0;
		for (int x=0; x<size; x++) {
			for (int y=0; y<size; y++) {
				int target = board.get(new Point(x, y));
				if (target != avgNum) {
					result += getDiffFrom(target, x, y);
				}
			}
		}
		System.out.println("The result is : " + result);
	}

	@SuppressWarnings("boxing")
	private int getDiffFrom(int target, int x, int y) {
		int diff = 0;
		Point E = new Point(x+1, y);
		Point W = new Point(x-1, y);
		Point S = new Point(x, y-1);
		Point N = new Point(x, y+1);
		List<Point> checkList = Arrays.asList(E, W, S, N);
		for (Point point : checkList) {
			if (board.containsKey(point)) {
				diff += Math.abs(board.get(point) - target);
			}
				
		}
		return diff;
	}

	private Map<Point, Integer> getBoard() {
		return board;
	}

	private void printBoard() {
		for (int x=0; x<size; x++) {
			for (int y=0; y<size; y++) {
				System.out.print(board.get(new Point(x, y)) + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

//	int[][] board = new int[40][40];
//	board[0] = new int[][]{0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//	board[1] = new int[][]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 6, 0};
//	board[2] = new int[][]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//	board[3] = new int[][]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//	board[4] = new int[][]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//	board[5] = new int[][]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//	board[6] = new int[][]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//	board[7] = new int[][]{0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//	board[8] = new int[][]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//	board[9] = new int[][]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	//		            	0, 1, 2, 3, 4, 5, 6, 7, 8, 9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39
//	board[10] = new int[][]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//	board[11] = new int[][]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//	board[12] = new int[][]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0};
//	board[13] = new int[][]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//	board[14] = new int[][]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//	board[15] = new int[][]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//	board[16] = new int[][]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//	board[17] = new int[][]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//	board[18] = new int[][]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//	board[19] = new int[][]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//	board[20] = new int[][]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//	board[21] = new int[][]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//	board[22] = new int[][]{0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//	board[23] = new int[][]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//	board[24] = new int[][]{0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//	board[25] = new int[][]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//	board[26] = new int[][]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//	board[27] = new int[][]{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//	board[28] = new int[][]{0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//	board[29] = new int[][]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0};
//	board[30] = new int[][]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0};
//	board[31] = new int[][]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//	board[32] = new int[][]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0};
//	board[33] = new int[][]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 5, 1, 0, 0, 0};
//	board[34] = new int[][]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//	board[35] = new int[][]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//	board[36] = new int[][]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0, 2, 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0};
//	board[37] = new int[][]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//	board[38] = new int[][]{0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//	board[39] = new int[][]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

	@SuppressWarnings("boxing")
	private void init() {
		int totalNum = 0;
//		while (setSize-- > 0) {
//			int x = random.nextInt(size);
//			int y = random.nextInt(size);
//			if (board.containsKey(new Point(x, y))) {
//				setSize++;
//				continue;
//			}
//			board.put(new Point(x, y), random.nextInt(9) + 1);
//			totalNum += board.get(new Point(x, y));
//		}
//		setAvgNumber(totalNum);
		setTest();
		for (int x=0; x<size; x++) {
			for (int y=0; y<size; y++) {
				if (board.get(new Point(x, y)) == null) {
					board.put(new Point(x, y), 0);
				}
			}
		}
	}

	@SuppressWarnings("boxing")
	private void setTest() {
		board.put(new Point(0, 7), 4);
		board.put(new Point(1, 10), 3);
		board.put(new Point(1, 32), 4);
		board.put(new Point(1, 38), 6);
		board.put(new Point(5, 27), 5);
		board.put(new Point(6, 26), 2);
		board.put(new Point(7, 5), 7);
		board.put(new Point(8, 25), 1);
		board.put(new Point(11, 24), 6);
		board.put(new Point(12, 19), 8);
		
		board.put(new Point(12, 36), 5);
		board.put(new Point(13, 25), 4);
		board.put(new Point(14, 30), 1);
		board.put(new Point(22, 7), 4);
		board.put(new Point(24, 2), 6);
		board.put(new Point(24, 30), 3);
		board.put(new Point(25, 12), 2);
		board.put(new Point(26, 17), 5);
		board.put(new Point(26, 29), 5);
		board.put(new Point(27, 7), 1);
		
		board.put(new Point(27, 16), 8);
		board.put(new Point(27, 28), 5);
		board.put(new Point(28, 8), 6);
		board.put(new Point(28, 16), 4);
		board.put(new Point(29, 36), 3);
		board.put(new Point(30, 37), 5);
		board.put(new Point(32, 20), 7);
		board.put(new Point(32, 28), 5);
		board.put(new Point(32, 36), 2);
		board.put(new Point(33, 11), 8);
		
		board.put(new Point(33, 26), 3);
		board.put(new Point(33, 35), 5);
		board.put(new Point(33, 36), 1);
		board.put(new Point(35, 18), 3);
		board.put(new Point(35, 23), 2);
		board.put(new Point(36, 17), 8);
		board.put(new Point(36, 20), 2);
		board.put(new Point(36, 24), 8);
		board.put(new Point(36, 35), 3);
		board.put(new Point(37, 23), 5);
		
		board.put(new Point(38, 3), 8);
		int total = 0;
		for (Integer num : board.values()) {
			total += num;
		}
		setAvgNumber(total);
	}

	private void setAvgNumber(int totalNum) {
		avgNum = totalNum/size + 1;
	}
	
	@SuppressWarnings("boxing")
	private void fillNumber() {
		for (int x=0; x<size; x++) {
			for (int y=0; y<size; y++) {
				if (board.get(new Point(x, y)) == 0) {
					board.put(new Point(x, y), avgNum);
				}
			}
		}
	}

}
