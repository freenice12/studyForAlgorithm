package day1FindShortestRoute.data;

import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import day1FindShortestRoute.GameBoard;
import day1FindShortestRoute.enums.CharacterType;
import day1FindShortestRoute.enums.Direction;
import day1FindShortestRoute.interfaces.Character;

public class Tracker {
	private Point location;
	private int length;
	private Tracker parent;
	private Tracker childE;
	private Tracker childW;
	private Tracker childS;
	private Tracker childN;

	private Map<Direction, Tracker> children = new HashMap<Direction, Tracker>();
	private Map<Point, Boolean> visitedMap = new HashMap<Point, Boolean>();
	private Point outOfPosition = new Point(-1, -1);
	private Map<Point, Character> boardMap;

	public Tracker() {
	}

	@SuppressWarnings("boxing")
	public Tracker(int length, Point location, Map<Point, Character> boardMap) {
		this.boardMap = boardMap;
		createVisitedMap();
		this.length = length;
		if (length == 0) {
			parent = this;
		}
		this.location = location;
		visitedMap.put(location, true);
		children = new HashMap<Direction, Tracker>();
		// createChildren(length, doorLocation, obstacleList);
	}

	@SuppressWarnings("boxing")
	private void createVisitedMap() {
		for (int i = 0; i < GameBoard.sizeX; i++) {
			for (int j = 0; j < GameBoard.sizeY; j++) {
				Point p = new Point(i, j);
				visitedMap.put(p, false);
			}
		}
	}

	// public Tracker(Point location, Point doorLocation, List<Point>
	// obstacleList) {
	// this(location);
	// }

	public void findRoute(Point doorLocation, List<Point> obstacleList,
			Map<Point, Integer> lengthMap) {
//		updateVisitedMap(lengthMap);
		// createChildren(doorLocation, obstacleList, lengthMap);
		if (hasChild()) {
			for (Entry<Direction, Tracker> entry : children.entrySet()) {
				Tracker child = entry.getValue();
				Tracker parentTracker = this;
				if (child.getLocation().equals(doorLocation)) {
					findShortRoute(parentTracker);
					return;
				}
				child.findRoute(doorLocation, obstacleList, lengthMap);
			}
		}
	}

	private void updateVisitedMap(Map<Point, Integer> lengthMap) {
		for (Point visited : lengthMap.keySet()) {
			visitedMap.put(visited, true);
		}
	}

	public boolean hasChild() {
		if (children.size() > 0)
			return true;
		return false;
	}

	public void fillNearChild(Tracker child) {

	}

	@SuppressWarnings("boxing")
	public Map<Direction, Tracker> createChildren(Tracker tracker, Point doorPosition, List<Point> obstacleList,
			Map<Point, Integer> lengthMap) {
		// length = parent.getLength() + 1;
//		if (!tracker.equals(location))
//			parent = new Tracker(length + 1, tracker, boardMap);
		lengthMap.put(location, tracker.getLength());
		updateVisitedMap(lengthMap);
			
		for (Direction direction : Direction.values()) {
			Point childPoint = getPointTo(tracker, direction, obstacleList, lengthMap);

			if (childPoint.equals(outOfPosition)
					|| childPoint.equals(parent.getLocation())
					|| lengthMap.containsKey(childPoint))
				continue;

			// addChild(direction, childPoint);

			Tracker childTracker = new Tracker(length + 1, childPoint, boardMap);
			childTracker.setParent(this);

			visitedMap.put(childPoint, true);
			lengthMap.put(childPoint, length + 1);
			// if (!lengthMap.containsKey(childTracker.getLocation())) {
			// lengthMap.put(childTracker.getLocation(), length + 1);
			// } else {
			// int minLength = (length + 1 <
			// lengthMap.get(childTracker.getLocation())) ? length + 1 :
			// lengthMap.get(childTracker.getLocation());
			// lengthMap.put(childTracker.getLocation(), minLength);
			// }
			childTracker.setVisitedMap(visitedMap);
			if (boardMap.get(childPoint).getType().equals(CharacterType.DOOR)) {
				children.clear();
				children.put(direction, childTracker);
				return children;
			}
			children.put(direction, childTracker);
		}
		return children;
	}

	private void addChild(Direction direction, Point childPoint) {
		switch (direction) {
		case EAST:
			childE = new Tracker(length + 1, childPoint, boardMap);
			break;
		case WEST:
			childW = new Tracker(length + 1, childPoint, boardMap);
			break;
		case NORTH:
			childN = new Tracker(length + 1, childPoint, boardMap);
			break;
		case SOUTH:
			childS = new Tracker(length + 1, childPoint, boardMap);
			break;
		}

	}

	private void findShortRoute(Tracker parentTracker) {
		for (Tracker tracker = parentTracker; tracker.getParent().getLength() > 0; tracker = tracker
				.getParent()) {

			System.out.println("찾았어 : " + tracker.getLocation() + " ("
					+ tracker.getLength() + ")");
		}
	}

	@SuppressWarnings("boxing")
	private Point getPointTo(Tracker tracker, Direction direction, List<Point> obstacleList, Map<Point, Integer> lengthMap) {
		Point tester = new Point(tracker.getLocation());
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
		if (tester.x < GameBoard.sizeX
				&& tester.x >= 0
				&& tester.y >= 0
				&& tester.y < GameBoard.sizeY
				&& !obstacleList.contains(tester)
				&& !lengthMap.containsKey(tester)
						) {
			return tester;
		}

		return outOfPosition;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public Tracker getParent() {
		return parent;
	}

	public void setParent(Tracker parent) {
		this.length = parent.getLength() + 1;
		this.parent = parent;
	}

	public Map<Direction, Tracker> getChildren() {
		return children;
	}

	public Tracker getChild(Direction direction) {
		return children.get(direction);
	}

	public void setChildren(Map<Direction, Tracker> children) {
		this.children = children;
	}

	public Map<Point, Boolean> getVisitedMap() {
		return visitedMap;
	}

	public void setVisitedMap(Map<Point, Boolean> visitedMap) {
		this.visitedMap = visitedMap;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Location: " + location);
		for (Direction direction : Direction.values()) {
			if (children.get(direction) != null)
				buffer.append("\n\t" + direction + ": "
						+ children.get(direction).getLocation());
		}
		buffer.append("\n");

		return buffer.toString();
	}

	public Map<Direction, Tracker> createChildren(Point doorPosition, List<Point> obstacleList,
			Map<Point, Integer> lengthMap, int parentLength) {
		length = parentLength;
		return createChildren(this, doorPosition, obstacleList, lengthMap);
	}

}
