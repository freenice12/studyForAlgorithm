package day1FindShortestRoute.character;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.LinkedBlockingDeque;

import day1FindShortestRoute.GameBoard;
import day1FindShortestRoute.data.History;
import day1FindShortestRoute.enums.CharacterType;
import day1FindShortestRoute.enums.Direction;
import day1FindShortestRoute.interfaces.Board;
import day1FindShortestRoute.interfaces.Character;
import day1FindShortestRoute.interfaces.Jerry;

public class JerryJY implements Jerry {

	private Point location;
	private Point doorLocation;
	private int blockCount = 0;
	private List<Point> route = new ArrayList<Point>();
	private Deque<Point> jerryTrackerDeque = new LinkedBlockingDeque<Point>();
	private Point outOfPosition = new Point(-1, -1);
	private List<Point> obstacleList;
	private Map<Integer, Map<Point, Character>> history = new LinkedHashMap<Integer, Map<Point, Character>>();

	public JerryJY() {
	}

	@Override
	public Map<Integer, History> getHistory() {
		printHistory();
		return null;
	}

	private void printHistory() {
		Object[][] tempMap = new Object[GameBoard.sizeX][GameBoard.sizeY];
		for (Entry<Integer, Map<Point, Character>> turnEntry : history
				.entrySet()) {
			for (Entry<Point, Character> mapPoint : turnEntry.getValue()
					.entrySet()) {
				int x = mapPoint.getKey().x;
				int y = mapPoint.getKey().y;
				// EMPTY, JERRY, DOOR, OBSTACLE
				switch (mapPoint.getValue().getType()) {
				case EMPTY:
					tempMap[y][x] = "O";
					break;
				case JERRY:
					tempMap[y][x] = "J";
					break;
				case DOOR:
					tempMap[y][x] = "E";
					break;
				case OBSTACLE:
					tempMap[y][x] = "X";
					break;
				}
			}
			StringBuffer stringBuffer = new StringBuffer();
			for (int i = GameBoard.sizeX - 1; 0 <= i; i--) {
				for (int j = 0; j < GameBoard.sizeY; j++) {
					stringBuffer.append(tempMap[i][j]).append(" ");
				}
				stringBuffer.append("\n");
			}
			stringBuffer.append("\n");
			System.out.println("==========" + turnEntry.getKey() + "========");
			System.out.println(stringBuffer.toString());

		}

	}

	public JerryJY(Point location) {
		this.location = location;
		jerryTrackerDeque.add(location);
	}

	@Override
	public Point getLocation() {
		return location;
	}

	@Override
	public void setLocation(Point location) {
		this.location = location;
		if (location == null) {
			System.out.println("No solution!");
			System.exit(0);
		}
		if (!jerryTrackerDeque.contains(location)) {
			jerryTrackerDeque.add(location);
		}
	}

	@Override
	public CharacterType getType() {
		return CharacterType.JERRY;
	}

	@Override
	public Direction getNextMove(Board board) {
		this.doorLocation = board.getDoorPosition();
		this.obstacleList = board.getObstacleList();
//		addWrongPoint();
		Direction next = check(getLongAxis());
		this.blockCount = 0;
		return next;
	}

	private Direction getLongAxis() {
		Direction checkDirection;
		if (Math.abs(location.x - doorLocation.x) > Math.abs(location.y
				- doorLocation.y)) {
			if (location.x < doorLocation.x) {
				checkDirection = Direction.EAST;
			} else {
				checkDirection = Direction.WEST;
			}
		} else {
			if (location.y < doorLocation.y) {
				checkDirection = Direction.NORTH;
			} else {
				checkDirection = Direction.SOUTH;
			}
		}
		return checkDirection;
	}

	private Direction check(Direction checkDirection) {
		if (blockCount > 2) {
			for (int index = route.size() - 1; index < route.size() && index > 10; index--)
				route.remove(index);
			System.out.println("blocked! = " + blockCount);
		}
		Point east = getPointTo(Direction.EAST);
		Point west = getPointTo(Direction.WEST);
		Point south = getPointTo(Direction.SOUTH);
		Point north = getPointTo(Direction.NORTH);

		Direction next = null;
		
		if (checkDirection.equals(Direction.EAST)) {
			if (checkNextDirection(east)) {
				addRoute(east);
				return checkDirection;
			}
			next = Direction.NORTH;
		} else if (checkDirection.equals(Direction.NORTH)) {
			if (checkNextDirection(north)) {
				addRoute(north);
				return checkDirection;
			}
			next = Direction.WEST;
		} else if (checkDirection.equals(Direction.WEST)) {
			if (checkNextDirection(west)) {
				addRoute(west);
				return checkDirection;
			}
			next = Direction.SOUTH;
		} else {
			if (checkNextDirection(south)) {
				addRoute(south);
				return checkDirection;
			}
			next = Direction.EAST;
		}
		blockCount++;
		return check(next);
	}

	private boolean checkNextDirection(Point east) {
		return !(east.equals(outOfPosition)) && !(route.contains(east));
	}

	private void addRoute(Point nextPoint) {
		route.add(nextPoint);
	}

	private Point getPointTo(Direction direction) {
		Point tester = new Point(location);
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
				&& tester.y < GameBoard.sizeY
				&& !(obstacleList.contains(tester))) {
			return tester;
		}

		return outOfPosition;
	}

	@Override
	public String toString() {
		return "Jerry is on: " + location;
	}

	@Override
	public int getHistorySize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public History getNextTurn(int turn) {
		// TODO Auto-generated method stub
		return null;
	}

}
