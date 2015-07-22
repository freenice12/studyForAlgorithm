package day1FindShortestRoute.character;

import java.awt.Point;

import day1FindShortestRoute.enums.CharacterType;
import day1FindShortestRoute.interfaces.Character;

public class Door implements Character {

	private Point location;

	public Door(Point location) {
		this.location = location;
	}
	
	@Override
	public Point getLocation() {
		return location;
	}

	@Override
	public CharacterType getType() {
		return CharacterType.DOOR;
	}
	
	@Override
	public String toString() {
		return "Door is on: " + location;
	}
}
