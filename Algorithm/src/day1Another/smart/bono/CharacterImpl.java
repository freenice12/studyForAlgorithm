package day1Another.smart.bono;

public class CharacterImpl implements Character, Cost {
	private final CharacterType type;
	private int cost = Integer.MAX_VALUE;

	private CharacterImpl(CharacterType type) {
		this.type = type;
	}

	@Override
	public CharacterType getType() {
		return type;
	}

	public static Character createObstacle() {
		return new CharacterImpl(CharacterType.OBSTACLE);
	}

	public static Character createJerry() {
		return new CharacterImpl(CharacterType.JERRY);
	}

	public static Character createDoor() {
		return new CharacterImpl(CharacterType.DOOR);
	}

	public static Character createEmpty() {
		return new CharacterImpl(CharacterType.EMPTY);
	}
	
	public static CharacterImpl createCopy(CharacterType type) {
		return new CharacterImpl(type);
	}

	@Override
	public int getCost() {
		return cost;
	}

	@Override
	public void setCost(int newCost) {
		cost = newCost;
	}
}
