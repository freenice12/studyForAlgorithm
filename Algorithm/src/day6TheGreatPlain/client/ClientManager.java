package day6TheGreatPlain.client;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

public class ClientManager {

	private UUID uuid;
	private Map<Point, Integer> map = new HashMap<>();
	private Map<Point, Integer> givenPoints = new HashMap<Point, Integer>();

	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}

	public void setMap(Map<Point, Integer> map) {
		this.map = map;
		extractGivenPoint();
		makePlain();
	}

	@SuppressWarnings("boxing")
	private void extractGivenPoint() {
		for (Entry<Point, Integer> entry : map.entrySet()) {
			if (entry.getValue() != 0) {
				givenPoints.put(entry.getKey(), entry.getValue());
			}
		}
	}

	private void makePlain() {
		for (Entry<Point, Integer> entry : givenPoints.entrySet()) {
			fillNextThePoint(entry.getKey(), entry.getValue());
		}
		setZeroToOne();
	}

	@SuppressWarnings("boxing")
	private void setZeroToOne() {
		for (Entry<Point, Integer> entry : map.entrySet()) {
			if (entry.getValue() == 0) {
				map.put(entry.getKey(), 1);
			}
		}
	}

	List<Point> filledPoints = new ArrayList<>();

	@SuppressWarnings("boxing")
	private void fillNextThePoint(Point currentPoint, Integer currentValue) {
		if (currentValue < 1)
			return;
		int x = currentPoint.x;
		int y = currentPoint.y;
		List<Point> checkList = getCheckList(x, y);
		List<Point> nextPoints = new ArrayList<>();
		for (Point p : checkList) {
			if (!map.containsKey(p))
				continue;

			if (!givenPoints.containsKey(p)) {
				fillMap(currentPoint, currentValue, x, y, nextPoints, p);
			}

			if (givenPoints.containsKey(p)) {
				map.put(currentPoint, (Math.max(map.get(p), currentValue) - 1));
			}
		}
		for (Point p : nextPoints) {
			fillNextThePoint(p, currentValue - 1);
		}
	}

	@SuppressWarnings("boxing")
	private void fillMap(Point currentPoint, Integer currentValue, int x,
			int y, List<Point> nextPoints, Point nextPoint) {
		if (map.get(nextPoint) == 0 && currentValue > 1) {
			map.put(nextPoint, currentValue - 1);
			nextPoints.add(nextPoint);
		} else if (map.get(nextPoint) != 0
				&& map.get(nextPoint) != currentValue - 1) {
			int meanValue = getMeanValue(map, x, y);
			map.put(currentPoint, meanValue);
		}
	}

	@SuppressWarnings("boxing")
	private int getMeanValue(Map<Point, Integer> board, int x, int y) {
		int sum = 0;
		int pointCount = 0;
		List<Point> newsPoints = getCheckList(x, y);
		for (Point point : newsPoints) {
			if (board.containsKey(point) && board.get(point) != 0) {
				pointCount++;
				sum += board.get(point);
			}
		}
		return sum / pointCount;
	}

	private List<Point> getCheckList(int x, int y) {
		Point E = new Point(x + 1, y);
		Point W = new Point(x - 1, y);
		Point S = new Point(x, y - 1);
		Point N = new Point(x, y + 1);
		List<Point> checkList = Arrays.asList(E, W, S, N);
		return checkList;
	}

	public Map<Point, Integer> getMap() {
		return map;
	}

	public UUID getUUID() {
		return uuid;
	}

}
