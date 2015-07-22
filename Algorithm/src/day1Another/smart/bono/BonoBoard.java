package day1Another.smart.bono;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class BonoBoard implements Board {
	private Character[][] characterMap = new Character[WIDTH][HEIGHT];
	private AtomicInteger step = new AtomicInteger(0);

	public BonoBoard() {
		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				characterMap[i][j] = CharacterImpl.createEmpty();
			}
		}
	}

	public BonoBoard(Character[][] initMap) {
		this(initMap, null);
	}

	public BonoBoard(Character[][] initMap, Character smartEmpty) {
		Point ptJerry = find(initMap, CharacterType.JERRY);
		Point ptNearSmartEmpty = null;

		if (smartEmpty != null) {
			int nearDistance = Integer.MAX_VALUE;
			// Find smartEmtpy near by JERRY.
			for (int x = 0; x < WIDTH; x++) {
				for (int y = 0; y < HEIGHT; y++) {
					if (smartEmpty != null && initMap[x][y] == smartEmpty) {
						Point ptSmartEmpty = new Point(x, y);
						int distance = (int) ptJerry.distanceSq(ptSmartEmpty);

						if (distance < nearDistance) {
							ptNearSmartEmpty = ptSmartEmpty;
							nearDistance = distance;
						}
					}
				}
			}
		}

		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				if (ptNearSmartEmpty != null && ptNearSmartEmpty.x == x
						&& ptNearSmartEmpty.y == y) {
					// Block near smart empty.
					characterMap[x][y] = CharacterImpl.createObstacle();
					continue;
				}
				characterMap[x][y] = initMap[x][y];
			}
		}

		if (ptNearSmartEmpty != null
				&& SmartTomUtils.findFastestWay(this).size() == 0) {
			// Can't find way to door. Maintain smartEmpty cell to empty.
			characterMap[ptNearSmartEmpty.x][ptNearSmartEmpty.y] = CharacterImpl
					.createEmpty();
		}
	}

	@Override
	public Character getCharacter(int x, int y) {
		return characterMap[x][y];
	}

	@Override
	public void addCharacter(Character character, Point pt) throws Exception {
		switch (character.getType()) {
		case DOOR:
		case JERRY:
		case OBSTACLE:
			checkIsEmpty(pt);
			break;
		case EMPTY:
			throw new Exception("Invalid Character.");
		}
		characterMap[pt.x][pt.y] = character;
		validateDuplicate();
	}

	public void clearObstacle(Point pt) {
		if (characterMap[pt.x][pt.y].getType() == CharacterType.OBSTACLE)
			characterMap[pt.x][pt.y] = CharacterImpl.createEmpty();
	}

	private void checkIsEmpty(Point pt) throws Exception {
		if (characterMap[pt.x][pt.y].getType() == CharacterType.EMPTY) {
			return;
		}
		throw new Exception(pt + " isn't EMPTY cell.");
	}

	@Override
	public int getStepCount() {
		return step.get();
	}

	@Override
	public boolean moveJerryAndCheckDone(Direction dir) throws Exception {
		step.incrementAndGet();

		Point jerryPos = findJerry();
		Point ptMove = new Point(jerryPos);
		switch (dir) {
		case EAST:
			ptMove.x += 1;
			break;
		case WEST:
			ptMove.x -= 1;
			break;
		case NORTH:
			ptMove.y -= 1;
			break;
		case SOUTH:
			ptMove.y += 1;
			break;
		}
		if (characterMap[ptMove.x][ptMove.y].getType() == CharacterType.DOOR) {
			// success condition.
			return true;
		}

		checkIsEmpty(ptMove);
		characterMap[ptMove.x][ptMove.y] = characterMap[jerryPos.x][jerryPos.y];
		characterMap[jerryPos.x][jerryPos.y] = CharacterImpl.createEmpty();
		return false;
	}

	@Override
	public void validate() throws Exception {
		validateDuplicate();
		existWayJerryToDoor();
	}

	private void validateDuplicate() throws Exception {
		Set<CharacterType> duplicateCheckMap = new HashSet<CharacterType>();

		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				CharacterType type = characterMap[x][y].getType();

				switch (type) {
				case DOOR:
				case JERRY:
					if (duplicateCheckMap.contains(type))
						throw new Exception("Duplicate " + type + ".");
					duplicateCheckMap.add(type);
					break;
				default:
					// do nothing.
				}
			}
		}
	}

	private void existWayJerryToDoor() throws Exception {
		if (SmartTomUtils.findFastestWay(this).size() == 0) {
			throw new Exception("Can't find way to door from jerry.");
		}
	}

	@Override
	public Point findDoor() {
		return find(characterMap, CharacterType.DOOR);
	}

	@Override
	public Point findJerry() {
		return find(characterMap, CharacterType.JERRY);
	}

	private Point find(Character[][] map, CharacterType type) {
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				if (map[x][y].getType() == type) {
					return new Point(x, y);
				}
			}
		}
		return null;
	}
}
