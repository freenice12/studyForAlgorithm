package day6TheGreatPlain.common.message;

import java.awt.Point;
import java.io.Serializable;
import java.util.Map;
import java.util.UUID;


public class SubmitRequestMessage implements Serializable {

	private static final long serialVersionUID = 22L;

	private UUID uuid;
	private Map<Point, Integer> map;

	public SubmitRequestMessage(UUID uuid, Map<Point, Integer> map) {
		this.uuid = uuid;
		this.map = map;
	}

	public UUID getUuid() {
		return uuid;
	}
	
	public Map<Point, Integer> getMap() {
		return map;
	}
}
