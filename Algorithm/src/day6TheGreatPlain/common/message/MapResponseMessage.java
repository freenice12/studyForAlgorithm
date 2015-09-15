package day6TheGreatPlain.common.message;

import java.awt.Point;
import java.io.Serializable;
import java.util.Map;

public class MapResponseMessage implements Serializable {

	private static final long serialVersionUID = 9221813293555867483L;
	private Map<Point, Integer> map;

	public MapResponseMessage(Map<Point, Integer> map) {
		this.map = map;
	}
	
	public Map<Point, Integer> getMap() {
		return map;
	}
}
