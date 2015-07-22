package day1FindShortestRoute.character;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import day1FindShortestRoute.GameBoard;
import day1FindShortestRoute.data.History;
import day1FindShortestRoute.enums.CharacterType;
import day1FindShortestRoute.enums.Direction;
import day1FindShortestRoute.interfaces.Board;
import day1FindShortestRoute.interfaces.Character;
import day1FindShortestRoute.interfaces.Jerry;

public class JerryMouse implements Jerry {

	private Point location;
	private List<Point> shortestRoute;
	private int turn;
	private Map<Integer, History> historyMap = new LinkedHashMap<Integer, History>();
	private Map<Point, Integer> distanceMap = new HashMap<Point, Integer>();

	public JerryMouse(Point startPoint) {
		location = startPoint;
	}

	@Override
	public Point getLocation() {
		return location;
	}

	@Override
	public CharacterType getType() {
		return CharacterType.JERRY;
	}

	@Override
	public void setLocation(Point p) {
		location = p;
	}

	@Override
	public Direction getNextMove(Board board) {
		createDistanceMap();
		fillDistanceMap(board.getDoorPosition(), board.getObstacleList());
//		printDistanceMap();
		shortestRoute = getShortestRoute();
		saveHistory(board.getBoardMap());
		Direction next = getDirection(shortestRoute.get(0));
		shortestRoute.remove(board.getDoorPosition());
		// printHistory();
		return next;

	}

	@SuppressWarnings("boxing")
	private void createDistanceMap() {
		for (int i = 0; i < GameBoard.sizeX; i++) {
			for (int j = 0; j < GameBoard.sizeY; j++) {
				Point p = new Point(i, j);
				distanceMap.put(p, Integer.MAX_VALUE);
			}
		}
	}

	@SuppressWarnings("boxing")
	private void fillDistanceMap(Point doorLocation, List<Point> obstacles) {
		int distance = 0;
		Point initPoint = new Point(doorLocation);
		Set<Point> childrenSet = new HashSet<Point>();
		childrenSet.add(initPoint);
		while (!childrenSet.isEmpty()) {
			for (Point child : childrenSet) {
				distanceMap.put(child, distance);
			}
			childrenSet.clear();
			for (Point distancePoint : distanceMap.keySet()) {
				if (distanceMap.get(distancePoint) == distance) {
					addChildren(distancePoint, childrenSet, obstacles);
				}
			}
			distance++;
		}
		changeObstacleDistance(obstacles);
	}

	private void addChildren(Point startPoint, Set<Point> childSet,
			List<Point> obstacles) {
		for (Direction direction : Direction.values()) {
			addChildPoint(startPoint, direction, childSet, obstacles);
		}
	}

	@SuppressWarnings("boxing")
	private void addChildPoint(Point initPoint, Direction direction,
			Set<Point> childSet, List<Point> obstacles) {
		Point tester = new Point(initPoint);
		switch (direction) {
		case EAST:
			tester.translate(1, 0);
			break;
		case WEST:
			tester.translate(-1, 0);
			break;
		case NORTH:
			tester.translate(0, 1);
			break;
		case SOUTH:
			tester.translate(0, -1);
			break;
		}

		if (tester.x < GameBoard.sizeX && tester.x >= 0 && tester.y >= 0
				&& tester.y < GameBoard.sizeY && !(obstacles.contains(tester))
				&& distanceMap.get(tester) == Integer.MAX_VALUE) {
			childSet.add(tester);
		}
	}

	@SuppressWarnings("boxing")
	private void changeObstacleDistance(List<Point> obstacles) {
		for (Point obstacle : obstacles) {
			distanceMap.put(obstacle, -1);
		}
	}

	@SuppressWarnings("boxing")
	private List<Point> getShortestRoute() {
		List<Point> routeList = new ArrayList<Point>();
		int distance = distanceMap.get(location);
		validMap(distance);
		Point routeDetector = new Point(location);
		while (distance > 0) {
			distance--;
			for (Entry<Point, Integer> entry : distanceMap.entrySet()) {
				if (entry.getValue() == distance
						&& routeDetector.distance(entry.getKey()) == 1) {
					routeList.add(entry.getKey());
					routeDetector = entry.getKey();
				}
			}
		}
		return routeList;
	}

	private void validMap(int distance) {
		if (distance == Integer.MAX_VALUE) {
			System.out.println("No Solution!!");
			System.exit(0);
		}
	}

	@SuppressWarnings("boxing")
	private void saveHistory(Map<Point, Character> boardMap) {
		Map<Point, Character> gameBoard = new HashMap<Point, Character>(boardMap);
		History history = new History(gameBoard, shortestRoute);
		historyMap.put(turn, history);
		turn++;
	}

	private Direction getDirection(Point point) {
		if (location.x < point.x) {
			return Direction.EAST;
		}
		if (location.x > point.x) {
			return Direction.WEST;
		}
		if (location.y < point.y) {
			return Direction.NORTH;
		}
		return Direction.SOUTH;
	}

	@Override
	public Map<Integer, History> getHistory() {
		return historyMap;
	}

	@Override
	public String toString() {
		return "Jerry is on: " + location;
	}

	public Map<Point, Integer> getDistanceMap() {
		return distanceMap;
	}

	@Override
	public int getHistorySize() {
		return historyMap.size();
	}

	@Override
	@SuppressWarnings("boxing")
	public History getNextTurn(int index) {
		return historyMap.get(index);
	}

}
