package day1Another.smart.bono;

import java.awt.Point;

public interface Board {
	public int WIDTH = 100, HEIGHT = 100;

	Character getCharacter(int x, int y);

	void addCharacter(Character character, Point pt) throws Exception;

	int getStepCount();

	boolean moveJerryAndCheckDone(Direction dir) throws Exception;

	void validate() throws Exception;

	public Point findDoor();

	public Point findJerry();
}
