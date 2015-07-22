package day1Another.smart.bono;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class SmartTomUtils {
	public static List<Point> findFastestWay(Board board) {
		Cost[][] costMap = createCostMap(board);
		Point ptDoor = board.findDoor();

		// Make costMap.
		calculateCostMap(board, costMap);

		List<Point> fastestWays = new ArrayList<Point>();
		if (findJerryToDoor(fastestWays, board.findJerry(), ptDoor, costMap)) {
			return fastestWays;
		}
		return Collections.emptyList();
	}

	public static Cost[][] createCostMap(Board board) {
		Cost[][] costMap = new Cost[Board.WIDTH][Board.HEIGHT];
		for (int i = 0; i < Board.WIDTH; i++) {
			for (int j = 0; j < Board.HEIGHT; j++) {
				costMap[i][j] = CharacterImpl.createCopy(board.getCharacter(i,
						j).getType());
			}
		}
		return costMap;
	}

	public static void calculateCostMap(Board board, Cost[][] costMap) {
		Point ptDoor = board.findDoor();
		Set<Point> startPoint = new HashSet<Point>();
		startPoint.add(ptDoor);
		costMap[ptDoor.x][ptDoor.y].setCost(0);

		calculateCostMap(board, costMap, startPoint);
	}

	private static Set<Point> calculateCostMap(Board board, Cost[][] costMap,
			Set<Point> prevPoints) {
		while (true) {
			prevPoints = calculateCost(board, costMap, prevPoints);
			if (prevPoints.size() == 0)
				break;
		}
		return prevPoints;
	}

	private static Set<Point> calculateCost(Board board, Cost[][] costMap,
			Set<Point> prevPoints) {
		Set<Point> nextPoints = new HashSet<Point>();
		for (Point pt : prevPoints) {
			for (Point adjoinPt : getValidAdjoinCell(pt)) {
				int newCost = costMap[pt.x][pt.y].getCost() + 1;
				if (board.getCharacter(adjoinPt.x, adjoinPt.y).getType() == CharacterType.EMPTY
						&& costMap[adjoinPt.x][adjoinPt.y].getCost() > newCost) {
					costMap[adjoinPt.x][adjoinPt.y].setCost(newCost);
					nextPoints.add(adjoinPt);
				}
			}
		}
		return nextPoints;
	}

	public static boolean findJerryToDoor(List<Point> routes, Point ptPrev,
			Point ptDoor, Cost[][] costMap) {
		routes.add(ptPrev);
		if (ptPrev.equals(ptDoor)) {
			return true;
		}

		Point ptNext = null;
		int cost = costMap[ptPrev.x][ptPrev.y].getCost();
		for (Point adjoinPt : getValidAdjoinCell(ptPrev)) {
			int findCost = costMap[adjoinPt.x][adjoinPt.y].getCost();
			if (costMap[adjoinPt.x][adjoinPt.y].getCost() < cost) {
				cost = findCost;
				ptNext = adjoinPt;
			}
		}
		if (ptNext == null || cost == Integer.MAX_VALUE) {
			return false; // can't find way to door.
		}
		return findJerryToDoor(routes, ptNext, ptDoor, costMap);
	}

	public static int getNextSmallestCost(Cost[][] costMap, Point pt) {
		int nextCost = Integer.MAX_VALUE;
		for (Point adjoinPt : getValidAdjoinCell(pt)) {
			if (costMap[adjoinPt.x][adjoinPt.y].getCost() == Integer.MAX_VALUE)
				continue;

			int newCost = costMap[adjoinPt.x][adjoinPt.y].getCost() + 1;
			if (newCost < nextCost) {
				nextCost = newCost;
			}
		}
		return nextCost;
	}

	public static List<Point> getValidAdjoinCell(Point pt) {
		List<Point> validPoints = new ArrayList<Point>();

		Point ptCell = null;
		for (Direction dir : Direction.values()) {
			switch (dir) {
			case NORTH:
				ptCell = new Point(pt.x, pt.y - 1);
				break;
			case EAST:
				ptCell = new Point(pt.x + 1, pt.y);
				break;
			case SOUTH:
				ptCell = new Point(pt.x, pt.y + 1);
				break;
			case WEST:
				ptCell = new Point(pt.x - 1, pt.y);
				break;
			}
			if (isOnBoard(ptCell))
				validPoints.add(ptCell);
		}
		return validPoints;
	}

	public static List<Point> getValidAdjoinCell2(Point pt) {
		List<Point> validPoints = new ArrayList<Point>();
		List<Point> points = new ArrayList<Point>();

		for(int x = -1; x <= 1; x ++) {
			for(int y = -1; y <= 1; y ++) {
				points.add(new Point(pt.x + x, pt.y + y));
			}
		}

		for (Point validPt : points) {
			if (isOnBoard(validPt))
				validPoints.add(validPt);
		}
		return validPoints;
	}

	public static List<Point> getValidAdjoinCell3(Point pt) {
		List<Point> validPoints = new ArrayList<Point>();
		List<Point> points = new ArrayList<Point>();

		for(int x = -2; x <= 2; x ++) {
			for(int y = -2; y <= 2; y ++) {
				points.add(new Point(pt.x + x, pt.y + y));
			}
		}

		for (Point validPt : points) {
			if (isOnBoard(validPt))
				validPoints.add(validPt);
		}
		return validPoints;
	}
	
	public static Direction findAdjoinEmpty(Board board, Point pt) {
		int index = 0;
		for (Point adjoinPt : getValidAdjoinCell(pt)) {
			if (board.getCharacter(adjoinPt.x, adjoinPt.y).getType() == CharacterType.EMPTY) {
				return Direction.values()[index];
			}
			index++;
		}
		return null;
	}

	public static boolean isOnBoard(Point pt) {
		return isOnBoard(pt.x, pt.y);
	}

	public static boolean isOnBoard(int x, int y) {
		if (x < 0 || x >= Board.WIDTH || y < 0 || y >= Board.HEIGHT)
			return false;
		return true;
	}

	public static Direction getDirection(Point ptFrom, Point ptTo) {
		if (ptFrom.x < ptTo.x)
			return Direction.EAST;
		if (ptFrom.x > ptTo.x)
			return Direction.WEST;
		if (ptFrom.y < ptTo.y)
			return Direction.SOUTH;
		return Direction.NORTH;
	}

	public static List<Direction> getMoveOrder(Point startPoint, Point endPoint) {
		List<Direction> moveOrder = new ArrayList<Direction>();
		int distanceWidth = Math.abs(startPoint.x - endPoint.x);
		int distanceHeight = Math.abs(startPoint.y - endPoint.y);

		if (distanceWidth > distanceHeight) {
			if (startPoint.y < endPoint.y) {
				moveOrder.add(Direction.EAST);
				moveOrder.add(Direction.WEST);
			} else {
				moveOrder.add(Direction.WEST);
				moveOrder.add(Direction.EAST);
			}
			moveOrder.add(Direction.NORTH);
			moveOrder.add(Direction.SOUTH);
		} else {
			if (startPoint.x < endPoint.x) {
				moveOrder.add(Direction.SOUTH);
				moveOrder.add(Direction.NORTH);
			} else {
				moveOrder.add(Direction.NORTH);
				moveOrder.add(Direction.SOUTH);
			}
			moveOrder.add(Direction.EAST);
			moveOrder.add(Direction.WEST);
		}
		return moveOrder;
	}

	public static Character[][] makeCharacterMap(Board board) {
		Character[][] boardMap = new Character[Board.WIDTH][Board.HEIGHT];
		for (int i = 0; i < Board.WIDTH; i++) {
			for (int j = 0; j < Board.HEIGHT; j++) {
				boardMap[i][j] = board.getCharacter(i, j);
			}
		}
		return boardMap;
	}

	public static Direction getMoveDirection(Point ptSrc, Point ptDest) {
		if (ptSrc.x < ptDest.x)
			return Direction.EAST;
		if (ptSrc.x > ptDest.x)
			return Direction.WEST;
		if (ptSrc.y < ptDest.y)
			return Direction.SOUTH;
		return Direction.NORTH;
	}

	public static void printBoard(Board board) {
		for (int x = 0; x < Board.WIDTH; x++) {
			for (int y = 0; y < Board.HEIGHT; y++) {
				CharacterType type = board.getCharacter(x, y).getType();
				switch (type) {
				case DOOR:
					System.out.print("[D]");
					break;
				case JERRY:
					System.out.print("[J]");
					break;
				case EMPTY:
					System.out.print("[ ]");
					break;
				case OBSTACLE:
					System.out.print("[#]");
					break;
				}
			}
			System.out.println();
		}
	}

	public static void printCostMap(Cost[][] costMap) {
		for (int i = 0; i < Board.WIDTH; i++) {
			for (int j = 0; j < Board.HEIGHT; j++) {
				if (costMap[i][j].getCost() == Integer.MAX_VALUE) {
					if (costMap[i][j] instanceof Character) {
						CharacterType type = ((Character) costMap[i][j])
								.getType();
						switch (type) {
						case DOOR:
							System.out.print("[D] ");
							break;
						case JERRY:
							System.out.print("[J] ");
							break;
						case EMPTY:
							System.out.print("[ ] ");
							break;
						case OBSTACLE:
							System.out.print("[#] ");
							break;
						}
					} else
						System.out.print("    ");
				} else
					System.out.print(String.format("%03d ",
							costMap[i][j].getCost()));
			}
			System.out.println();
		}
	}

	public static boolean isAdjoinCells(Point pt, Point ptOther) {
		if (pt.distanceSq(ptOther) <= 2)
			return true;
		return false;
	}
}
