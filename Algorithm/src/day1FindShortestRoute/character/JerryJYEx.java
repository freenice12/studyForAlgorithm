package day1FindShortestRoute.character;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import day1FindShortestRoute.data.History;
import day1FindShortestRoute.data.Tracker;
import day1FindShortestRoute.enums.CharacterType;
import day1FindShortestRoute.enums.Direction;
import day1FindShortestRoute.interfaces.Board;
import day1FindShortestRoute.interfaces.Character;
import day1FindShortestRoute.interfaces.Jerry;

public class JerryJYEx implements Jerry {

	private Tracker root;
	private Point outOfPosition = new Point(-1, -1);
	private Point doorPosition;
	private Point location;
	private List<Point> obstacleList;
	private Map<Point, Character> gameBoard;

	public JerryJYEx() {
	}

	public JerryJYEx(Point startPoint) {
		this.location = startPoint;
	}

	@Override
	public Point getLocation() {
		return location;
	}

	@Override
	public void setLocation(Point location) {
		this.location = location;
	}

	@SuppressWarnings("boxing")
	@Override
	public Direction getNextMove(Board board) {
		doorPosition = board.getDoorPosition();
		obstacleList = board.getObstacleList();
		gameBoard = board.getBoardMap();
		
		
		
		

		Map<Point, Integer> lengthMap = new LinkedHashMap<Point, Integer>();
		
		List<Point> checkedList = new ArrayList<Point>();
		checkedList.add(location);
		lengthMap.put(location, 0);

		root = new Tracker(0, location, board.getBoardMap());
		
		findDoor(root, lengthMap);
//		System.out.println(lengthMap);

		// checkRoute(board);
		// checkWarnArea(board.getObstacleList());
		// createJerryMap(board);
		return Direction.EAST;
	}
	
	private void findDoor(Tracker tracker, Map<Point, Integer> lengthMap) {
		if (tracker == null)
			return;
		Map<Direction, Tracker> children = tracker.createChildren(doorPosition, obstacleList, lengthMap, tracker.getLength());
		Point trackerLocation = tracker.getLocation();
		if (trackerLocation.equals(doorPosition)) {
			System.out.println("찾았다: "+trackerLocation);
			return;
		}
		
		for (Tracker child : children.values()) {
			child.setLength(tracker.getLength() + 1);
			findDoor(child, lengthMap);
		}
		
		
		
		
		
		
//		Map<Direction, Tracker> children = tracker.createChildren(tracker, doorPosition, obstacleList, lengthMap);
		
//		Tracker trackerE = tracker.getChildren(Direction.EAST);
//		Tracker trackerW = tracker.getChildren(Direction.WEST);
//		Tracker trackerN = tracker.getChildren(Direction.NORTH);
//		Tracker trackerS = tracker.getChildren(Direction.SOUTH);
//		if (trackerE != null)
//			findDoor(trackerE, lengthMap);
//		if (trackerW != null)
//			findDoor(trackerW, lengthMap);
//		if (trackerN != null)
//			findDoor(trackerN, lengthMap);
//		if (trackerS != null)
//			findDoor(trackerS, lengthMap);
//		
//		gameBoard.clear();

		// for (Entry<Direction, Tracker> childTracker : tracker.getChildren()
		// .entrySet()) {
		// findDoor(childTracker.getValue(), lengthMap);
		// }
	}

	// private void checkRoute(Board board) {
	// Map<Point, Integer> distanceMap = new HashMap<Point, Integer>();
	// int distance = 0;
	// Point fromDoor = new Point(doorPosition);
	// for ( Entry<Point, Character> entry : board.getBoardMap().entrySet()) {
	// Point point = entry.getKey();
	// Character character = entry.getValue();
	// if (character.getType().equals(CharacterType.EMPTY)) {
	// distance++;
	// }
	// }
	// }

	// private void checkWarnArea(List<Point> obstacleList) {
	// for (Point obstacle : obstacleList) {
	// Set<Point> warnPointSet = new HashSet<Point>();
	// checkNearPoint(obstacle, obstacleList, warnPointSet);
	// }
	//
	// }
	//
	// private Set<Point> checkedObtacles = new HashSet<>();
	// private Map<Point, Character> jerryMap = new HashMap<Point, Character>();
	// private List<Point> warnPointList = new ArrayList<>();
	//
	// private void checkNearPoint(Point obstacle, List<Point> obstacleList,
	// Set<Point> warnPointSet) {
	// Point checker = new Point(obstacle);
	// for (DirectionEx direction : DirectionEx.values()) {
	// Point nearPoint = getPointTo(checker, direction, obstacleList);
	// if (!nearPoint.equals(outOfPosition)
	// && !checkedObtacles.contains(nearPoint)) {
	// checkedObtacles.add(nearPoint);
	// checkNearPoint(nearPoint, obstacleList, warnPointSet);
	// }
	// }
	// if (checkedObtacles.size() > 0)
	// setWarnArea(obstacleList, warnPointSet);
	// }
	//
	// private Point getPointTo(Point location, DirectionEx direction,
	// List<Point> obstacleList) {
	// Point tester = new Point(location);
	// switch (direction) {
	// case E:
	// tester.translate(1, 0);
	// break;
	// case W:
	// tester.translate(-1, 0);
	// break;
	// case N:
	// tester.translate(0, 1);
	// break;
	// case S:
	// tester.translate(0, -1);
	// break;
	// case NE:
	// tester.translate(1, 1);
	// break;
	// case NW:
	// tester.translate(-1, 1);
	// break;
	// case SE:
	// tester.translate(1, -1);
	// break;
	// case SW:
	// tester.translate(-1, -1);
	// break;
	// }
	//
	// if (tester.x < GameBoard.sizeX && tester.x >= 0 && tester.y >= 0
	// && tester.y < GameBoard.sizeY && obstacleList.contains(tester)) {
	// return tester;
	// }
	// return outOfPosition;
	// }
	//
	// private void setWarnArea(List<Point> obstacleList, Set<Point>
	// warnPointSet) {
	// int left = 0;
	// int right = 0;
	// int top = 0;
	// int bottom = 0;
	// boolean isFirst = true;
	// for (Point obtacle : checkedObtacles) {
	// if (isFirst) {
	// left = obtacle.x;
	// right = obtacle.x;
	// top = obtacle.y;
	// bottom = obtacle.y;
	// isFirst = false;
	// }
	// left = (left > obtacle.x) ? obtacle.x : left;
	// right = (right < obtacle.x) ? obtacle.x : right;
	// top = (top < obtacle.y) ? obtacle.y : top;
	// bottom = (bottom > obtacle.y) ? obtacle.y : bottom;
	// }
	// fillWarnArea(new Point(left, bottom), new Point(right, top),
	// obstacleList, warnPointSet);
	// }
	//
	// private void fillWarnArea(Point leftBottom, Point rightTop,
	// List<Point> obstacleList, Set<Point> warnPointSet) {
	// for (int xAxis = leftBottom.x; xAxis <= rightTop.x; xAxis++) {
	// for (int yAxis = leftBottom.y; yAxis <= rightTop.y; yAxis++) {
	// Point warnPoint = new Point(xAxis, yAxis);
	// if (obstacleList.contains(warnPoint)) {
	// continue;
	// }
	// warnPointSet.add(warnPoint);
	// }
	// }
	// warnPointSet.remove(doorPosition);
	// warnPointList.addAll(warnPointSet);
	// }
	//
	// private void createJerryMap(Board board) {
	// for (int i = 0; i < GameBoard.sizeX; i++) {
	// for (int j = 0; j < GameBoard.sizeY; j++) {
	// Point p = new Point(i, j);
	// jerryMap.put(p, new Empty(p));
	// }
	// }
	// jerryMap.put(doorPosition, new Door(doorPosition));
	// for (Point obstacle : board.getObstacleList()) {
	// jerryMap.put(obstacle, new Obstacle(obstacle));
	// }
	// jerryMap.put(location, new JerryJY(location));
	// // Map<Point, Character> tempMap = new HashMap<Point,
	// // Character>(jerryMap);
	// // history.put(turn, tempMap);
	// // turn++;
	// printMap();
	// }
	//
	// public void printMap() {
	// Object[][] tempMap = new Object[GameBoard.sizeX][GameBoard.sizeY];
	// for (Entry<Point, Character> mapPoint : jerryMap.entrySet()) {
	// int x = mapPoint.getKey().x;
	// int y = mapPoint.getKey().y;
	// // EMPTY, JERRY, DOOR, OBSTACLE
	// switch (mapPoint.getValue().getType()) {
	// case EMPTY:
	// tempMap[y][x] = "O";
	// break;
	// case JERRY:
	// tempMap[y][x] = "J";
	// break;
	// case DOOR:
	// tempMap[y][x] = "E";
	// break;
	// case OBSTACLE:
	// tempMap[y][x] = "X";
	// break;
	// }
	// }
	// // if (!testerLocation.equals(null))
	// // tempMap[testerLocation.y][testerLocation.x] = "T";
	//
	// for (Point warn : warnPointList ) {
	// tempMap[warn.y][warn.x] = "W";
	// }
	//
	// StringBuffer stringBuffer = new StringBuffer();
	// for (int i = GameBoard.sizeX - 1; 0 <= i; i--) {
	// for (int j = 0; j < GameBoard.sizeY; j++) {
	// stringBuffer.append(tempMap[i][j]).append(" ");
	// }
	// stringBuffer.append("\n");
	// }
	// stringBuffer.append("\n");
	// System.out.print(stringBuffer.toString());
	// }
	//
	// private void printHistory() {
	// Object[][] tempMap = new Object[GameBoard.sizeX][GameBoard.sizeY];
	// for (Entry<Integer, Map<Point, Character>> turnEntry : history
	// .entrySet()) {
	// for (Entry<Point, Character> mapPoint : turnEntry.getValue()
	// .entrySet()) {
	// int x = mapPoint.getKey().x;
	// int y = mapPoint.getKey().y;
	// // EMPTY, JERRY, DOOR, OBSTACLE
	// switch (mapPoint.getValue().getType()) {
	// case EMPTY:
	// tempMap[y][x] = "O";
	// break;
	// case JERRY:
	// tempMap[y][x] = "J";
	// break;
	// case DOOR:
	// tempMap[y][x] = "E";
	// break;
	// case OBSTACLE:
	// tempMap[y][x] = "X";
	// break;
	// }
	// }
	// StringBuffer stringBuffer = new StringBuffer();
	// for (int i = GameBoard.sizeX - 1; 0 <= i; i--) {
	// for (int j = 0; j < GameBoard.sizeY; j++) {
	// stringBuffer.append(tempMap[i][j]).append(" ");
	// }
	// stringBuffer.append("\n");
	// }
	// stringBuffer.append("\n");
	// System.out.println("==========" + turnEntry.getKey() + "========");
	// System.out.println(stringBuffer.toString());
	//
	// }
	//
	// }

	@Override
	public CharacterType getType() {
		return CharacterType.JERRY;
	}

	@Override
	public Map<Integer, History> getHistory() {
		return null;
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
