package day1Another.smart.bono;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SmartTom implements Tom {
	private final int WIDTH = Board.WIDTH, HEIGHT = Board.HEIGHT;
	private final Character SMART_EMPTY = CharacterImpl.createEmpty();
	private final Character EMPTY = CharacterImpl.createEmpty();
	private final Character OBSTACLE = CharacterImpl.createObstacle();
	private final Set<Point> unblockPoints = new HashSet<Point>();
	private Character[][] thinkMap = null;

	public SmartTom() {
		// empty
	}

	private Point ptPrev = null;
	private Direction prevMove = null;
	private int continueCount = 0;
	private final int CONTINUE_MAX = 20; // 20 : 900,
	private final int PREDICT_COUNT_START = 4;
	private final int PREDICT_COUNT_END = PREDICT_COUNT_START + 10;
	private final int PREDICT_MINIMUM = 5;

	private int stepCount = 0;
	private int resetStep = 0;
	private final int RESET_AFTER_THIS = 3;

	@Override
	public Point getNextObstacle(Board board) {
		while (true) {
			Point ptObstacle = _getNextObstacle(board);

			if (prevMove != null) {
				Direction dir = SmartTomUtils.getMoveDirection(ptPrev,
						ptObstacle);
				if (prevMove == dir && isNearPoint(ptPrev, ptObstacle)) {
					continueCount++;
					if (continueCount > CONTINUE_MAX) {
						resetStep = stepCount + RESET_AFTER_THIS;
						// unblockPoints.addAll(SmartTomUtils
						// .getValidAdjoinCell3(ptObstacle));
						continueCount = 0;
						continue;
					}
				}
				prevMove = dir;
			} else if (ptPrev != null) {
				prevMove = SmartTomUtils.getMoveDirection(ptPrev, ptObstacle);
			}

			thinkMap[ptObstacle.x][ptObstacle.y] = OBSTACLE;
			if (SmartTomUtils.findFastestWay(new BonoBoard(thinkMap)).size() == 0) {
				addUnblockPoint(ptObstacle);
				thinkMap[ptObstacle.x][ptObstacle.y] = EMPTY;
				continue;
			}
			afterFind(ptObstacle);
			return ptObstacle;
		}
	}

	private void afterFind(Point ptObstacle) {
		ptPrev = ptObstacle;
		if (resetStep == stepCount) {
			// unblockPoints.clear();
			resetStep = 0;
		}
		stepCount++;
	}

	private Point _getNextObstacle(Board board) {
		Character[][] charMap = SmartTomUtils.makeCharacterMap(board);
		Point ptCreate = findSmartEmptyNeerByJerry(board);

		if (ptCreate != null) {
			return ptCreate;
		}

		ptCreate = getNextObstacleInThinkMap(board, charMap, true);
		if (isOnBorder(ptCreate.x, ptCreate.y)) {
			thinkMap[ptCreate.x][ptCreate.y] = SMART_EMPTY;
			ptCreate = getNextObstacleInThinkMap(board, charMap, false);
		}
		return ptCreate;
	}

	private void addUnblockPoint(Point pt) {
		unblockPoints.add(pt);
	}

	private boolean isNotUnblockPoint(Point pt) {
		return !unblockPoints.contains(pt);
	}

	private Point findSmartEmptyNeerByJerry(Board board) {
		if (thinkMap == null)
			return null;

		Point ptJerry = board.findJerry();

		for (int x = -2; x < 4; x++) {
			for (int y = -2; y < 4; y++) {
				if (!SmartTomUtils.isOnBoard(ptJerry.x + x, ptJerry.y + y))
					continue;
				Point pt = new Point(ptJerry.x + x, ptJerry.y + y);
				int distanceSq = (int) ptJerry.distanceSq(pt);
				if (distanceSq < 4)
					continue;

				if (thinkMap[pt.x][pt.y] == SMART_EMPTY) {
					return pt;
				}
			}
		}
		return null;
	}

	private Point getNextObstacleInThinkMap(Board board, Character[][] charMap,
			boolean searchInBorder) {
		Point ptJerry = board.findJerry();
		copyToThinkMap(charMap);

		BonoBoard thinkBoard = new BonoBoard(thinkMap, SMART_EMPTY);
		Cost[][] costMap = SmartTomUtils.createCostMap(thinkBoard);
		SmartTomUtils.calculateCostMap(thinkBoard, costMap);

		// SmartTomUtils.printCostMap(costMap);

		int cost = SmartTomUtils.getNextSmallestCost(costMap, ptJerry);

		Set<Point> checkPoints = new HashSet<Point>();
		List<Point> fastestWays = new ArrayList<Point>();
		SmartTomUtils.findJerryToDoor(fastestWays, board.findJerry(),
				board.findDoor(), costMap);

		for (int predictCount = PREDICT_COUNT_START; predictCount < PREDICT_COUNT_END; predictCount++) {
			int checkCost = cost - predictCount;
			if (checkCost > PREDICT_MINIMUM)
				checkPoints.addAll(findCheckPoints(board, fastestWays, costMap,
						checkCost));
			// checkPoints.add(fastestWays.get(checkCost));
			for (Point ptUnblock : unblockPoints) {
				checkPoints.remove(ptUnblock);
			}
		}
		if (checkPoints.size() == 0)
			return getAnyObstacle(board, costMap, ptJerry);

		int longestWay = 0;
		Point bestObstacle = null;

		for (Point testPoint : checkPoints) {
			try {
				thinkBoard.addCharacter(CharacterImpl.createObstacle(),
						testPoint);
			} catch (Exception e) {
				continue;
			}
			int wayCount = SmartTomUtils.findFastestWay(thinkBoard).size();

			if (wayCount >= longestWay) {
				longestWay = wayCount;
				if (bestObstacle == null || !isOnBorder(testPoint))
					bestObstacle = testPoint;
			}

			thinkBoard.clearObstacle(testPoint);
		}

		if (bestObstacle == null)
			return getAnyObstacle(board, costMap, ptJerry);
		return bestObstacle;
	}

	private Point getAnyObstacle(Board board, Cost[][] costMap, Point ptJerry) {
		for (int cost = 1;; cost++) {
			int sameCostCount = 0;

			for (int x = 0; x < WIDTH; x++) {
				for (int y = 0; y < HEIGHT; y++) {
					if (costMap[x][y].getCost() == cost) {
						sameCostCount++;
						Point pt = new Point(x, y);
						if (sameCostCount >= 2 && ptJerry.distanceSq(x, y) > 2
								&& isNotUnblockPoint(pt)) {
							return pt;
						}
					}
				}
			}
			if (sameCostCount == 0)
				break;
		}

		// SmartTomUtils.printCostMap(costMap);
		return findUselessObstacle(board, costMap);
	}

	private Point findUselessObstacle(Board board, Cost[][] costMap) {
		// SmartTomUtils.printCostMap(costMap);
		for (int x = 2; x < WIDTH - 2; x++) {
			for (int y = 2; y < HEIGHT - 2; y++) {
				if (costMap[x][y].getCost() == Integer.MAX_VALUE
						&& board.getCharacter(x, y).getType() == CharacterType.EMPTY) {
					Point pt = new Point(x, y);
					if (isNotUnblockPoint(pt))
						return pt;
					continue;
				}
			}
		}
		return null;
	}

	private List<Point> findCheckPoints(Board board, List<Point> fastestWays,
			Cost[][] costMap, int cost) {
		List<Point> checkPoints = new ArrayList<Point>();

		if (fastestWays.size() > cost) {
			Point checkPt = fastestWays.get(fastestWays.size() - cost);
			return SmartTomUtils.getValidAdjoinCell2(checkPt);
		}
		return checkPoints;
	}

	private boolean isOnBorder(Point pt) {
		return isOnBorder(pt.x, pt.y);
	}

	private boolean isOnBorder(int x, int y) {
		if (x == 0 || y == 0 || x == WIDTH - 1 || y == HEIGHT - 1)
			return true;
		return false;
	}

	private boolean isNearPoint(Point pt, Point ptNext) {
		if (pt == null || ptNext == null)
			return false;
		if (pt.distanceSq(ptNext) == 1)
			return true;
		return false;
	}

	private void copyToThinkMap(Character[][] map) {
		if (thinkMap == null)
			thinkMap = new Character[WIDTH][HEIGHT];

		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				if (thinkMap[x][y] == SMART_EMPTY
						&& map[x][y].getType() == CharacterType.EMPTY) {
					// maintain information about specific empty cell.
					continue;
				}
				thinkMap[x][y] = map[x][y];
			}
		}
	}
}
