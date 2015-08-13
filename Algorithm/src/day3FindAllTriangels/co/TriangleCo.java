package day3FindAllTriangels.co;

import java.awt.Point;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class TriangleCo implements Comparable<TriangleCo> {

	private Set<Point> pointSet = new HashSet<Point>();
	private Map<Integer, Point> pointMap = new TreeMap<Integer, Point>();

	public Set<Point> getPointSet() {
		return pointSet;
	}

	private int index;

	@SuppressWarnings("boxing")
	public void addPoint(Point point) {
		pointSet.add(point);
		pointMap.put(index++, point);
	}

	public boolean isTriangle() {
		return pointSet.size() == 3;
	}

	public boolean contains(Point point) {
		return pointMap.containsValue(point);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((pointSet == null) ? 0 : pointSet.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TriangleCo other = (TriangleCo) obj;
		if (pointSet == null) {
			if (other.pointSet != null)
				return false;
		} else if (!pointSet.equals(other.pointSet))
			return false;
		return true;
	}

	@Override
	public String toString() {

		StringBuffer buffer = new StringBuffer();
		for (Point point : pointMap.values()) {
			buffer.append("[" + point.x + ", " + point.y + "] ");
		}
		return buffer.toString();
	}

	public Point getCenter() {
		int sumOfX = 0;
		int sumOfY = 0;
		for (Point point : pointSet) {
			sumOfX += point.x;
			sumOfY += point.y;
		}
		Point center = new Point(sumOfX / 3, sumOfY / 3);

		return center;
	}

	// double count = 0;
	private double getArea() {
		// count = 0;
		Iterator<Point> pointIter = pointSet.iterator();
		Point one = pointIter.next();
		Point two = pointIter.next();
		Point three = pointIter.next();
		double area = Math.abs((one.getX() - three.getX())
				* (two.getY() - one.getY()) - (one.getX() - two.getX())
				* (three.getY() - one.getY())) * 0.5;
		return area;
	}

	// [117, 293] [160, 400] [0, 400]
	@Override
	public int compareTo(TriangleCo other) {
//		if (getCenter().y < other.getCenter().y)
//			return -1;
		return 0;
	}
}
