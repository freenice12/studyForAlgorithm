package day1FindShortestRoute.character;

import java.awt.Point;

import day1FindShortestRoute.enums.CharacterType;
import day1FindShortestRoute.interfaces.Character;

public class Empty implements Character {

	private Point location;

	public Empty(Point location) {
		this.location = location;
	}
	
	@Override
	public Point getLocation() {
		return location;
	}

	@Override
	public CharacterType getType() {
		return CharacterType.EMPTY;
	}

	@Override
	public String toString() {
		return "Empty : "+location;
	}
	
	
}
