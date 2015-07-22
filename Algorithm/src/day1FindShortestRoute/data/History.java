package day1FindShortestRoute.data;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import day1FindShortestRoute.interfaces.Character;


public class History {
	private Map<Point, Character> history = new LinkedHashMap<Point, Character>();
	private List<Point> shortestRoute = new ArrayList<Point>();
	public History(Map<Point, Character> history, List<Point> shortestRoute) {
		this.history = history;
		this.shortestRoute = shortestRoute;
	}
	public Map<Point, Character> getHistory() {
		return history;
	}
	public void setHistory(Map<Point, Character> history) {
		this.history = history;
	}
	public List<Point> getShortestRoute() {
		return shortestRoute;
	}
	public void setShortestRoute(List<Point> shortestRoute) {
		this.shortestRoute = shortestRoute;
	}
}
