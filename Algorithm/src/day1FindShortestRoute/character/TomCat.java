package day1FindShortestRoute.character;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;







import com.sun.media.sound.InvalidDataException;

import day1FindShortestRoute.GameBoard;
import day1FindShortestRoute.enums.Direction;
import day1FindShortestRoute.interfaces.Board;
import day1FindShortestRoute.interfaces.Character;
import day1FindShortestRoute.interfaces.Tom;

public class TomCat implements Tom {
	private Map<Point, Integer> distanceMap = new HashMap<Point, Integer>();
	private Set<Point> obstacles = new HashSet<Point>();

	@Override
	public Point getNextObstacle(Board board) {
//		createDistanceMap();
//		fillDistanceMap(board.getDoorPosition(), board.getObstacleList());
//		Point tempObstaclePoint = checkRoute(board);
//
//		return tempObstaclePoint;

		Character characterI = null;
		do {
			//수정 
			int randomX = random(GameBoard.sizeX);
			int randomY = random(GameBoard.sizeY);
//			int randomX = 0;
//			int randomY = 0;
			
			//2칸이상
			Point jerryPoint = board.getJerryPosition();
			if(isEstablish(randomX, randomY, jerryPoint.x, jerryPoint.y)) {
				System.out.println("[Tom - getNextObstacle] Near !!!!" + randomX + "," + randomY);
				continue;
			}
			
			//막으면 안 된다.
			if(isAllBlock()) {
				continue;
			}
			
			try {
				characterI = board.getCharacter(randomX, randomY);
			} catch (InvalidDataException e) {
				e.printStackTrace();
			}
			
			
		} while(!(characterI instanceof Empty));
		
		switch (characterI.getType()) {
		case EMPTY:
			return characterI.getLocation();
		}
		return null;
	}

	@SuppressWarnings("boxing")
	private Point checkRoute(Board board) {
		obstacles.clear();
		Point tempObstaclePoint = getObstaclePoint(board.getJerryPosition());
		obstacles.add(tempObstaclePoint);
		obstacles.addAll(board.getObstacleList());
		int distance = distanceMap.get(board.getJerryPosition());
		for (Entry<Point, Integer> entry : distanceMap.entrySet()) {
			if (entry.getValue() == distance && !obstacles.contains(entry.getKey())) {
				continue;
			}
		}
		
		
		CheckDistanceMap(board.getJerryPosition(), board.getDoorPosition(),
				obstacles);
		return tempObstaclePoint;
	}

	@SuppressWarnings("boxing")
	private void CheckDistanceMap(Point jerryPosition, Point doorPosition,
			Set<Point> obstacleSet) {
		List<Point> obstacleList = new ArrayList<Point>();
		obstacleList.addAll(obstacleSet);
		while (!isValidMap(distanceMap.get(jerryPosition))) {
			fillDistanceMap(doorPosition, obstacleList);
			changeObstacleDistance(obstacleList);
		}
	}

	@SuppressWarnings("boxing")
	private Point getObstaclePoint(Point jerryPosition) {
		Set<Point> routeSet = new HashSet<Point>();
		int distance = distanceMap.get(jerryPosition);
		Point routeDetector = new Point(jerryPosition);
		if (isValidMap(distance)) {
			while (distance > 3) {
				distance--;
				for (Entry<Point, Integer> entry : distanceMap.entrySet()) {
					if (entry.getValue() == distance
							&& routeDetector.distance(entry.getKey()) == 1) {
						routeSet.add(entry.getKey());
						routeDetector = entry.getKey();
					}
				}
			}
		} else {
			System.out.println("No Solution!!");
			System.exit(0);
		}
		Point obstacle = routeSet.iterator().next();
		return obstacle;
	}

	private boolean isValidMap(int distance) {
		if (distance == Integer.MAX_VALUE)
			return false;
		return true;
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

	// private boolean hasGap(Point obstacle) {
	// int x = Math.abs(jerryPosition.x - obstacle.x);
	// int y = Math.abs(jerryPosition.y - obstacle.y);
	//
	// if (x > 0 && y > 0)
	// return true;
	//
	// return false;
	// }
	//
	// private int blockCounter;
	//
	// private Point getPointTo(Direction direction) {
	// Point tester = new Point(doorPosition);
	// switch (direction) {
	// case EAST:
	// tester.translate(1, 0);
	// break;
	// case WEST:
	// tester.translate(-1, 0);
	// break;
	// case NORTH:
	// tester.translate(0, 1);
	// break;
	// case SOUTH:
	// tester.translate(0, -1);
	// break;
	// }
	//
	// if (tester.x < GameBoard.sizeX && tester.x >= 0 && tester.y >= 0
	// && tester.y < GameBoard.sizeY && !obstacleList.contains(tester)) {
	// return tester;
	// }
	//
	// return outOfPosition;
	// }

	// private Point getObstaclePoint() {
	// Point tester = new Point(jerryPosition);
	// int xAxisPosition = GameBoard.sizeX - tester.x;
	// int yAxisPosition = GameBoard.sizeY - tester.y;
	//
	// if (Math.abs(doorPosition.x - tester.x) < 5
	// && Math.abs(doorPosition.y - tester.y) < 5) {
	// if (blockCounter < 3) {
	// if (doorPosition.x > tester.x) {
	// blockCounter++;
	// tester = new Point(doorPosition.x - 1, doorPosition.y);
	// } else if (doorPosition.x < tester.x) {
	// blockCounter++;
	// tester = new Point(doorPosition.x + 1, doorPosition.y);
	// } else if (doorPosition.y < tester.y) {
	// blockCounter++;
	// tester = new Point(doorPosition.x, doorPosition.y + 1);
	// } else {
	// blockCounter++;
	// tester = new Point(doorPosition.x, doorPosition.y - 1);
	// }
	// }
	// } else {
	// tester.translate(
	// (int) ((Math.random() * xAxisPosition) + (jerryPosition.x)),
	// (int) ((Math.random() * yAxisPosition) + (jerryPosition.y)));
	// }
	//
	// if (tester.x < GameBoard.sizeX && tester.x >= 0 && tester.y >= 0
	// && tester.y < GameBoard.sizeY) {
	// return tester;
	// }

	// for (Direction direction : Direction.values()) {
	// Point checkPoint = getPointTo(direction);
	// if (blocker < 3) {
	// if (checkPoint.equals(outOfPosition)) {
	// blocker++;
	// continue;
	// }
	// return checkPoint;
	// }
	// }
	//
	// return new Point((int) (Math.random() * GameBoard.sizeX),
	// (int) (Math.random() * GameBoard.sizeY));
	// }

	// private int blocker;
	
	private boolean isEstablish(int x1, int y1, int x2, int y2) {
		int x = Math.abs(x2 - x1);
		int y = Math.abs(y2 - y1);

		if(x >= 1 && y >=1)
			return false;
		
		return true;
	}

	private boolean isAllBlock() {
		return false;
	}

	
	private int random(int i) {
		return (int) (Math.random() * i);
	}
}
