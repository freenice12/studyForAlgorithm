package day1FindShortestRoute;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.sun.media.sound.InvalidDataException;

import day1FindShortestRoute.character.Door;
import day1FindShortestRoute.character.Empty;
import day1FindShortestRoute.character.Obstacle;
import day1FindShortestRoute.enums.CharacterType;
import day1FindShortestRoute.enums.Direction;
import day1FindShortestRoute.exception.InvalidDirectException;
import day1FindShortestRoute.exception.NotEmptyException;
import day1FindShortestRoute.interfaces.Board;
import day1FindShortestRoute.interfaces.Character;
import day1FindShortestRoute.interfaces.Jerry;

public class GameBoard implements Board {

	private int stepCount = 0;

	private Map<Point, Character> gameMap = new HashMap<Point, Character>();
	public Map<Integer, Map<Point, Character>> history = new LinkedHashMap<Integer, Map<Point,Character>>();

	private boolean isFinish = false;

	public static int sizeX = 10;
	public static int sizeY = 10;

	public GameBoard() {
		init();
	}

	private void init() {
		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++) {
				Point p = new Point(i, j);
				gameMap.put(p, new Empty(p));
			}
		}
	}

	@Override
	public Map<Integer, Map<Point, Character>> getHistory() {
		return history;
	}

	@Override
	public Character getCharacter(int x, int y) throws InvalidDataException {
		if(x < 0 || x > sizeX)
			throw new InvalidDataException();
		if(y < 0 || y > sizeY)
			throw new InvalidDataException();

		return gameMap.get(new Point(x,y));
	}

	@Override
	public void addCharacter(Character character) throws InvalidDataException, NotEmptyException {
		if(null == character)
			return;

		if(isFinish)
			return;
		
		if(getCharacter(character.getLocation().x, character.getLocation().y).getType() != CharacterType.EMPTY)
			throw new NotEmptyException();

		if(character.getType() == CharacterType.JERRY)
			System.out.println("[GameBoard - moveJerry] Jerry : " + character.getLocation());
		
		if(character.getType() == CharacterType.OBSTACLE)
			System.out.println("[GameBoard - NextObstacle] Obstacle : " + character.getLocation());
		
		gameMap.put(character.getLocation(), character);
	}

	@Override
	public int getStepCount() {
		return stepCount;
	}

	@Override
	public void moveJerry(Direction dir) throws InvalidDataException, NotEmptyException {
		Jerry jerry = getJerry();
		Point currentPoint = jerry.getLocation();
		
		Point p = null;
		switch (dir) {
		case NORTH:
			p = new Point(currentPoint.x, currentPoint.y+1);
			break;
		case SOUTH:
			p = new Point(currentPoint.x, currentPoint.y-1);
			break;
		case EAST:
			p = new Point(currentPoint.x+1, currentPoint.y);
			break;
		case WEST:
			p = new Point(currentPoint.x-1, currentPoint.y);
			break;
		}
		stepCount++;
		
		if(getDoorPosition().equals(p)) {
			isFinish = true;
			return;
		}
		
		jerry.setLocation(p);
		
		gameMap.put(currentPoint, new Empty(currentPoint));
		addCharacter(jerry);
	}

	@Override
	public boolean validate(Direction dir) throws InvalidDataException, InvalidDirectException {
//		printBoard();
		Point currentPoint = getJerryPosition();
		int x = currentPoint.x;
		int y = currentPoint.y;

		switch (dir) {
		case NORTH:
			if(currentPoint.y + 1 > sizeY)
				return false;
			y++;
			break;
		case SOUTH:
			if(currentPoint.y - 1 < 0)
				return false;
			y--;
			break;
		case EAST:
			if(currentPoint.x + 1 > sizeX)
				return false;
			x++;
			break;
		case WEST:
			if(currentPoint.x - 1 < 0)
				return false;
			x--;
			break;
		}
		Character characterI = getCharacter(x, y);
		if(null != characterI && characterI.getType() == CharacterType.OBSTACLE)
			return false;
		
		if(isCheckCrash())
			throw new InvalidDirectException();
		
		return true;
	}

	private boolean isCheckCrash() {
		for (Point p : getObstacleList()) {
			if(getJerryPosition().equals(p.getLocation())) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean isFinish() {
		return isFinish;
	}

	@Override
	public List<Point> getObstacleList() {
		List<Point> obstacleList = new ArrayList<Point>();
		for (Character c : gameMap.values()) {
			if(c instanceof Obstacle) {
				obstacleList.add(c.getLocation());
			}
		}
		return obstacleList;
	}

	@Override
	public Point getJerryPosition() {
		return getJerry().getLocation();
	}

	private Jerry getJerry() {
		for (Character character : gameMap.values()) {
			if(character instanceof Jerry) {
				return (Jerry) character;
			}
		}
		return null;
	}

	@Override
	public Point getDoorPosition() {
		return getDoor().getLocation();
	}
	
	private Door getDoor() {
		for (Character character : gameMap.values()) {
			if(character instanceof Door) {
				return (Door) character;
			}
		}
		return null;
	}
	
//
//	private int stepCount = 0;
//
//	private Map<Point, Character> gameMap = new LinkedHashMap<Point, Character>();
//
//	private boolean isFinish = false;
//
//	public static int sizeX = 4;
//	public static int sizeY = 4;
//
//	// public static int sizeX = 100;
//	// public static int sizeY = 100;
//
//	public GameBoard() {
//		init();
//	}
//
//	private void init() {
//		for (int i = 0; i < sizeX; i++) {
//			for (int j = 0; j < sizeY; j++) {
//				Point p = new Point(i, j);
//				gameMap.put(p, new Empty(p));
//			}
//		}
//	}
//
//	@Override
//	public Character getCharacter(int x, int y) throws InvalidDataException {
//		if (x < 0 || x > sizeX)
//			throw new InvalidDataException();
//		if (y < 0 || y > sizeY)
//			throw new InvalidDataException();
//
//		return gameMap.get(new Point(x, y));
//	}
//
//	@Override
//	public void addCharacter(Character character) throws InvalidDataException,
//			NotEmptyException {
//		
//		System.out.println(getCharacter(character.getLocation().x, character.getLocation().y).getType().toString());
//		
//		if (null == character)
//			return;
//
//		if (isFinish)
//			return;
//		
//		
//		if (getCharacter(character.getLocation().x, character.getLocation().y)
//				.getType() != CharacterType.EMPTY)
//			throw new NotEmptyException();
//
//		if (character.getType() == CharacterType.JERRY)
//			System.out.println("[GameBoard - moveJerry] Jerry : "
//					+ character.getLocation());
//
//		if (character.getType() == CharacterType.OBSTACLE)
//			System.out.println("[GameBoard - NextObstacle] Obstacle : "
//					+ character.getLocation());
//
//		gameMap.put(character.getLocation(), character);
//	}
//
////	@Override
////	public void addCharacter(Character character) throws InvalidDataException,
////			NotEmptyException {
////		if (null == character)
////			return;
////
////		Point point = character.getLocation();
////		if (point.x < 0 || point.x > sizeX)
////			throw new InvalidDataException();
////		if (point.y < 0 || point.y > sizeY)
////			throw new InvalidDataException();
////
////		if (gameMap.get(point).getType() != CharacterType.EMPTY)
////			throw new NotEmptyException();
////
////		if (isFinish)
////			return;
////
////		if (character.getType() == CharacterType.JERRY)
////			System.out.println("[GameBoard - moveJerry] Jerry : "
////					+ character.getLocation());
////
////		if (character.getType() == CharacterType.OBSTACLE)
////			System.out.println("[GameBoard - NextObstacle] Obstacle : "
////					+ character.getLocation());
////
////		gameMap.put(point, character);
////		// printBoard();
////	}
//
//	@Override
//	public int getStepCount() {
//		return stepCount;
//	}
//
//	@Override
//	public void moveJerry(Direction dir) throws InvalidDirectException,
//			NotEmptyException, InvalidDataException {
//		Jerry jerry = getJerry();
//		Point currentPoint = jerry.getLocation();
//
//		Point p = null;
//		switch (dir) {
//		case NORTH:
//			p = new Point(currentPoint.x, currentPoint.y + 1);
//			break;
//		case SOUTH:
//			p = new Point(currentPoint.x, currentPoint.y - 1);
//			break;
//		case EAST:
//			p = new Point(currentPoint.x + 1, currentPoint.y);
//			break;
//		case WEST:
//			p = new Point(currentPoint.x - 1, currentPoint.y);
//			break;
//		}
//		stepCount++;
//
//		if (getDoorPosition().equals(p)) {
//			isFinish = true;
//			return;
//		}
//		jerry.setLocation(p);
//		
//		addCharacter(new Empty(currentPoint));
//		addCharacter(jerry);
//
//		
////		gameMap.put(currentPoint, new Empty(currentPoint));
//		// printBoard();
////		addCharacter(jerry);
//		
//		
//		// Jerry jerry = getJerry();
//		// Point currentPoint = jerry.getLocation();
//		//
//		// Point p = null;
//		// switch (dir) {
//		// case NORTH:
//		// if(currentPoint.y+1 < GameBoard.sizeY) {
//		// p = new Point(currentPoint.x, currentPoint.y + 1);
//		// } else {
//		// System.out.println("Can't Move to "+dir);
//		// return;
//		// }
//		// break;
//		// case SOUTH:
//		// if (currentPoint.y-1 > -1) {
//		// p = new Point(currentPoint.x, currentPoint.y - 1);
//		// } else {
//		// System.out.println("Can't Move to "+dir);
//		// return;
//		// }
//		// break;
//		// case WEST:
//		// if (currentPoint.x-1 > -1) {
//		// p = new Point(currentPoint.x - 1, currentPoint.y);
//		// } else {
//		// System.out.println("Can't Move to "+dir);
//		// return;
//		// }
//		// break;
//		// case EAST:
//		// if (currentPoint.x+1 < GameBoard.sizeX) {
//		// p = new Point(currentPoint.x + 1, currentPoint.y);
//		// } else {
//		// System.out.println("Can't Move to "+dir);
//		// return;
//		// }
//		// break;
//		// }
//		// stepCount++;
//		//
//		// gameMap.put(currentPoint, new Empty(currentPoint));
//		//
//		// jerry.setLocation(p);
//		// gameMap.put(p, jerry);
//		//
//		// System.out.println("[GameBoard - moveJerry] Jerry : " + p);
//		//
//		// if (null == getDoor())
//		// isFinish = true;
//	}
//
	@Override
	public void printBoard() {
		Map<Point, Character> historyMap = new HashMap<Point, Character>(gameMap);
		history.put(stepCount, historyMap);
		Object[][] tempMap = new Object[sizeX][sizeY];
		for (Entry<Point, Character> mapPoint : gameMap.entrySet()) {
			int x = mapPoint.getKey().x;
			int y = mapPoint.getKey().y;
			// EMPTY, JERRY, DOOR, OBSTACLE
			if (mapPoint.getValue().getType() == CharacterType.EMPTY)
				tempMap[y][x] = "O";
			else if (mapPoint.getValue().getType() == CharacterType.JERRY)
				tempMap[y][x] = "J";
			else if (mapPoint.getValue().getType() == CharacterType.OBSTACLE)
				tempMap[y][x] = "X";
			else
				tempMap[y][x] = "E";
		}
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = sizeX - 1; 0 <= i; i--) {
			for (int j = 0; j < sizeY; j++) {
				stringBuffer.append(tempMap[i][j]).append(" ");
			}
			stringBuffer.append("\n");
		}
		stringBuffer.append("\n");
		System.out.print(stringBuffer.toString());
	}
//
//	private boolean isCheckCrash() {
//		for (Point p : getObstacleList()) {
//			if (getJerry().getLocation().equals(p.getLocation())) {
//				return true;
//			}
//		}
//		return false;
//	}
//
//	@Override
//	public List<Point> getObstacleList() {
//		List<Point> obstacleList = new ArrayList<Point>();
//		for (Character c : gameMap.values()) {
//			if (c instanceof Obstacle) {
//				obstacleList.add(c.getLocation());
//			}
//		}
//		return obstacleList;
//	}
//
//	private Jerry getJerry() {
//		for (Character character : gameMap.values()) {
//			if (character instanceof Jerry) {
//				return (Jerry) character;
//			}
//		}
//		return null;
//	}
//
//	private Door getDoor() {
//		for (Character character : gameMap.values()) {
//			if (character instanceof Door) {
//				return (Door) character;
//			}
//		}
//		return null;
//	}
//
//	@Override
//	public boolean validate(Direction dir) throws InvalidDataException, InvalidDirectException {
//		Point currentPoint = getJerryPosition();
//		int x = currentPoint.x;
//		int y = currentPoint.y;
//
//		switch (dir) {
//		case NORTH:
//			if (currentPoint.y + 1 > sizeY)
//				return false;
//			y++;
//			break;
//		case SOUTH:
//			if (currentPoint.y - 1 < 0)
//				return false;
//			y--;
//			break;
//		case WEST:
//			if (currentPoint.x - 1 < 0)
//				return false;
//			x--;
//			break;
//		case EAST:
//			if (currentPoint.x + 1 > sizeX)
//				return false;
//			x++;
//			break;
//		}
//		Character characterI = getCharacter(x, y);
//		
//		if(null != characterI && characterI.getType() == CharacterType.OBSTACLE)
//			return false;
//		
//		if(isCheckCrash())
//			throw new InvalidDirectException();
//		
//		return true;
//		
//		
////		try {
////			characterI = getCharacter(x, y);
////		} catch (InvalidDataException e) {
////			e.printStackTrace();
////		}
////
////		if (null != characterI
////				&& characterI.getType() == CharacterType.OBSTACLE)
////			return false;
////
////		if (isCheckCrash())
////			return false;
////
////		return true;
//	}
//
//	@Override
//	public boolean isFinish() {
//		return isFinish;
//	}
//
//	@Override
//	public Point getJerryPosition() {
//		return getJerry().getLocation();
//	}
//
//	@Override
//	public Point getDoorPosition() {
//		return getDoor().getLocation();
//	}

	@Override
	public Map<Point, Character> getBoardMap() {
		return gameMap;
	}
}
