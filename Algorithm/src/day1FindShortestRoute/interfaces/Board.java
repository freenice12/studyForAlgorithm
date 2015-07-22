package day1FindShortestRoute.interfaces;

import java.awt.Point;
import java.util.List;
import java.util.Map;

import com.sun.media.sound.InvalidDataException;

import day1FindShortestRoute.enums.Direction;
import day1FindShortestRoute.exception.InvalidDirectException;
import day1FindShortestRoute.exception.NotEmptyException;

public interface Board {
	public Character getCharacter(int x, int y) throws InvalidDataException;
	public void addCharacter(Character character) throws InvalidDataException, NotEmptyException;
	public int getStepCount();
	public void moveJerry(Direction dir) throws InvalidDirectException, InvalidDataException, NotEmptyException;
	public boolean validate(Direction dir) throws InvalidDataException, InvalidDirectException;

	//Addition
	public boolean isFinish();
	public Point getJerryPosition();
	public Point getDoorPosition();
	public List<Point> getObstacleList();
	public void printBoard();
	public Map<Integer, Map<Point, Character>> getHistory();
	public Map<Point, Character> getBoardMap();
}
