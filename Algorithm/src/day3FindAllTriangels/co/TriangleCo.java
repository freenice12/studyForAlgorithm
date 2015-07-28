package day3FindAllTriangels.co;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

public class TriangleCo {
	
	private Set<Point> pointSet = new HashSet<Point>();

	public Set<Point> getPointSet() {
		return pointSet;
	}

	public void addPoint(Point point) {
		pointSet.add(point);
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
		for (Point point : pointSet) {
			buffer.append("["+point.x+", "+point.y+"] ");
		}
		return buffer.toString();
	}


}
