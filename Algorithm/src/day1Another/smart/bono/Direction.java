package day1Another.smart.bono;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public enum Direction {
	NORTH, WEST, EAST, SOUTH;

	private static Random rand = new Random(System.currentTimeMillis());

	public static Direction getRandom() {
		return Direction.values()[Math.abs(rand.nextInt()) % 4];
	}

	public static Direction inverse(Direction dir) {
		switch (dir) {
		case EAST:
			return WEST;
		case WEST:
			return EAST;
		case NORTH:
			return SOUTH;
		default: // SOUTH
			return NORTH;
		}
	}

	public static List<Direction> searchOrder(List<Direction> dirs) {
		List<Direction> orders = new ArrayList<Direction>(dirs);
		for (Direction dir : values()) {
			if (!orders.contains(dir))
				orders.add(dir);
		}

		return orders;
	}
}
