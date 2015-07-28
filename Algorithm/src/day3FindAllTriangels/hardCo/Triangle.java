package day3FindAllTriangels.hardCo;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.graphics.Point;

public class Triangle {
	
	private Set<Point> pointSet = new HashSet<Point>();
	private Point firstPoint;
	private Point secondPoint;
	private Point thirdPoint;

	public Set<Point> getPointSet() {
		return pointSet;
	}

	public void setPointSet(Set<Point> pointSet) {
		this.pointSet = pointSet;
	}
	
	public Point getFirstPoint() {
		return firstPoint;
	}

	public void setFirstPoint(Point firstPoint) {
		this.firstPoint = firstPoint;
	}

	public Point getSecondPoint() {
		return secondPoint;
	}

	public void setSecondPoint(Point secondPoint) {
		this.secondPoint = secondPoint;
	}

	public Point getThirdPoint() {
		return thirdPoint;
	}

	public void setThirdPoint(Point thirdPoint) {
		this.thirdPoint = thirdPoint;
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
		Triangle other = (Triangle) obj;
		if (pointSet == null) {
			if (other.pointSet != null)
				return false;
		} else if (!pointSet.equals(other.pointSet)) {
			return false;
		} 
		if (other.getPointSet().containsAll(pointSet)) {
			return true;
		}
		return true;
	}

	@Override
	public String toString() {
		
		StringBuffer buffer = new StringBuffer();
		for (Point point : pointSet) {
			buffer.append("["+point.x+", "+point.y+"] ");
		}
		return buffer.toString();
//		return "Triangle [pointSet=" + pointSet + "]";
	}

}
