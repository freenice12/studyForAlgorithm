package day4MatchedSqures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.eclipse.swt.graphics.Point;

public class GameBoard implements Board {

	// 200 -> 20187
	// 300 -> 27082
	// 500 -> 1578968ms
	public static int SIZE = 20;
	private Random rand = new Random();
	private int[][] board;
	private int totalSum;

	@Override
	public void init() {
		board = new int[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				int random = rand.nextInt(9) + 1;
				board[i][j] = random;
				totalSum += random;
			}
		}
		if (totalSum % 2 != 0) {
			int location = rand.nextInt(SIZE);
			int number = board[location][location];
			board[location][location] = number % 2 == 0 ? 1 : 0;
			totalSum -= number;
			totalSum += board[location][location];
		}
//		List<Integer> nums = Arrays.asList(3, 8, 8, 6, 2, 9, 7, 6, 7, 8, 7, 2,
//				7, 4, 7, 4, 6, 6, 8, 4, 9, 4, 3, 4, 3);
//		int index = 0;
//		board = new int[5][5];
//		for (int i = 0; i < 5; i++) {
//			for (int j = 0; j < 5; j++) {
//				totalSum += nums.get(index);
//				board[i][j] = nums.get(index++);
//			}
//		}
//		showBoard();
	}

	public int[][] getBoard() {
		return board;
	}

	public int getTotalSum() {
		return totalSum;
	}

	@Override
	public List<Rectangle> findSquares() {
		int half = totalSum / 2;
		Map<Integer, List<Rectangle>> priceRectangle = extractSqure(half);
		if (priceRectangle.equals(Collections.EMPTY_MAP))
			System.out.println("No Solution");
		try {
			return selectRectangle(priceRectangle, half);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Collections.EMPTY_LIST;
	}

	private List<Rectangle> selectRectangle(
			Map<Integer, List<Rectangle>> priceRectangle, int half)
			throws Exception {
		List<Rectangle> result = new ArrayList<>();
		if (!(priceRectangle.get(half) == null)
				&& !priceRectangle.get(half).equals(Collections.EMPTY_LIST)) {
			return priceRectangle.get(half);
		}

		if (findedNumber == 0) {
			result = findSolution(priceRectangle, half);
		} else {
			result = makeRectangles(priceRectangle.get(findedNumber),
					otherPoint);
		}

		return result;
	}

	@SuppressWarnings("boxing")
	private List<Rectangle> findSolution(
			Map<Integer, List<Rectangle>> priceRectangle, int half) {
		Set<Integer> numbers = priceRectangle.keySet();
		for (int i = half; i > (half / 2); i--) {
			int target = i;
			int gap = half - i;
			if (numbers.contains(target) && numbers.contains(gap)) {
				for (int first = 0; first < priceRectangle.get(target).size(); first++) {
					for (int second = 0; second < priceRectangle.get(gap)
							.size(); second++) {
						Rectangle rec = priceRectangle.get(target).get(first);
						Rectangle other = priceRectangle.get(gap).get(second);
						if (!rec.hasRectangle(other)) {
							List<Rectangle> result = new ArrayList<Rectangle>();
							result.add(rec);
							result.add(other);
							return result;
						}
					}
				}
			}
		}
		return Collections.EMPTY_LIST;
	}

	private List<Rectangle> makeRectangles(List<Rectangle> list, Point other)
			throws Exception {
		Rectangle rec = new Rectangle(other.x, other.y, 1);
		rec.setSum((totalSum / 2) - findedNumber);
		list.add(rec);
		return list;
	}

	public void showBoard() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("total Sum: " + totalSum);
	}

	private Map<Integer, List<Rectangle>> extractSqure(int half) {
		Map<Integer, List<Rectangle>> priceRectangle = new HashMap<Integer, List<Rectangle>>();
		// Map<Integer, List<Rectangle>> priceRectangle =
		// makePriceRectangle(half);
		// Map<Rectangle, Integer> squares = new HashMap<Rectangle, Integer>();
		int recSize = SIZE;
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				try {
					// addRectangle(squares, i, j, recSize);
					fillPriceMap(priceRectangle, i, j, recSize, half);
					if (!priceRectangle.get(half)
							.equals(Collections.EMPTY_LIST))
						return priceRectangle;
				} catch (Exception e) {
					// e.printStackTrace();
				}
			}
			if (hasSolution(priceRectangle, half, 1))
				return priceRectangle;
			recSize = SIZE;
		}
		return priceRectangle;
	}

	private int findedNumber;

	@SuppressWarnings("boxing")
	private boolean hasSolution(Map<Integer, List<Rectangle>> priceRectangle,
			int half, int gap) {
		if (priceRectangle.get(half - gap) != null
				&& !priceRectangle.get(half - gap).equals(
						Collections.EMPTY_LIST)) {
			if (findOtherFromBoard(priceRectangle.get(half - gap), gap)) {
				findedNumber = half - gap;
				return true;
			}
		}
		if ((half / 2) + 1 < gap || gap > (SIZE/10) + 1)
			return false;
		return hasSolution(priceRectangle, half, gap + 1);
	}

	private Point otherPoint;

	private boolean findOtherFromBoard(List<Rectangle> rectangles, int gap) {
		for (Rectangle rectangle : rectangles) {
			for (int i = 0; i < SIZE; i++) {
				for (int j = 0; j < SIZE; j++) {
					int target = board[i][j];
					otherPoint = new Point(i, j);
					if (!rectangle.hasPoint(otherPoint) && target == gap)
						return true;
				}
			}
		}
		return false;
	}

	@SuppressWarnings("boxing")
	private void fillPriceMap(Map<Integer, List<Rectangle>> priceRectangle,
			int i, int j, int recSize, int half) throws Exception {
		int nextSize = recSize - 1;
		if (nextSize >= 2)
			fillPriceMap(priceRectangle, i, j, nextSize, half);

		Rectangle rectangle = new Rectangle(i, j, recSize);
		int sum = computeSquare(rectangle);
		rectangle.setSum(sum);
		// System.out.println(rectangle);
		if (sum > half)
			return;

		if (priceRectangle.containsKey(sum))
			priceRectangle.get(sum).add(rectangle);
		List<Rectangle> newRectangles = new ArrayList<Rectangle>();
		newRectangles.add(rectangle);
		priceRectangle.put(sum, newRectangles);

	}

	private Map<Integer, List<Rectangle>> makePriceRectangle(int half) {
		Map<Integer, List<Rectangle>> resultMap = new HashMap<Integer, List<Rectangle>>(
				half);
		for (int price = 0; price <= half; price++) {
			resultMap.put(price, new ArrayList<Rectangle>());
		}

		return resultMap;
	}

	// @SuppressWarnings("boxing")
	// private void addRectangle(Map<Rectangle, Integer> square, int x, int y,
	// int size) throws Exception {
	// int nextSize = size - 1;
	// if (nextSize >= 2)
	// addRectangle(square, x, y, nextSize);
	// Rectangle rec = new Rectangle(x, y, size);
	// int recSum = computeSquare(rec);
	// rec.setSum(recSum);
	// square.put(rec, recSum);
	// System.out.println(x + " / " + y + " / " + size);
	// System.out.println(rec);
	//
	// }

	private int computeSquare(Rectangle rec) {
		int recSum = 0;
		for (int startY = rec.getStart().y; startY <= rec.getEnd().y; startY++) {
			for (int startX = rec.getStart().x; startX <= rec.getEnd().x; startX++) {
				recSum += board[startX][startY];
			}
		}
		return recSum;
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		GameBoard board = new GameBoard();
		board.init();
		System.out.println("Total: " + board.getTotalSum() + ". half: "
				+ (board.getTotalSum() / 2));
		System.out.println("Solution: ");
		for (Rectangle rec : board.findSquares()) {
			System.out.println("\t" + rec);
		}
		 System.out.println("time: "+(System.currentTimeMillis() - start)+"ms");
	}
}
