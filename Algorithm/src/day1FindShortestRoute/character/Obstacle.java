package day1FindShortestRoute.character;

import java.awt.Point;

import day1FindShortestRoute.enums.CharacterType;
import day1FindShortestRoute.interfaces.Character;

public class Obstacle implements Character {

	private Point location;

	public Obstacle(Point location) {
		this.location = location;
	}
	
	@Override
	public Point getLocation() {
		return location;
	}

	@Override
	public CharacterType getType() {
		return CharacterType.OBSTACLE;
	}
}
