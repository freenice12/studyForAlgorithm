package day1FindShortestRoute.interfaces;

import java.awt.Point;
import java.util.Map;

import day1FindShortestRoute.data.History;
import day1FindShortestRoute.enums.Direction;

public interface Jerry extends Character{
	public void setLocation(Point p);
	public Direction getNextMove(Board board);
	public Map<Integer, History> getHistory();
	public int getHistorySize();
	public History getNextTurn(int turn);
}
