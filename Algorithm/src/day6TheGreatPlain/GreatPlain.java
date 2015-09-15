package day6TheGreatPlain;

import java.awt.Point;
import java.io.File;
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
		size = setSize = 40;
	}

	public static void main(String[] args) {
//		GreatPlain gp = new GreatPlain();
//		gp.init();
//		gp.printBoard();
//		gp.fillNumber();
//		gp.printBoard();
//		gp.printResultNum();
//		BoardViewer viewer = new BoardViewer(gp.getBoard());
//		viewer.init();
		
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

	@SuppressWarnings("boxing")
	private void init() {
		int totalNum = 0;
		while (setSize-- > 0) {
			int x = random.nextInt(size);
			int y = random.nextInt(size);
			if (board.containsKey(new Point(x, y))) {
				setSize++;
				continue;
			}
			board.put(new Point(x, y), random.nextInt(9) + 1);
			totalNum += board.get(new Point(x, y));
		}
		setAvgNumber(totalNum);
//		setTest();
		for (int x=0; x<size; x++) {
			for (int y=0; y<size; y++) {
				if (board.get(new Point(x, y)) == null) {
					board.put(new Point(x, y), 0);
				}
			}
		}
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
