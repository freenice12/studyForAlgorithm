package day3AndDay5FindAllTriangels.hardCo;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Point;

public class Line {

	private Point startPoint;
	private List<Point> endPointList = new ArrayList<Point>();
	
	public Line(Point startPoint) {
		this.startPoint = startPoint;
	}
	
	public Point getStartPoint() {
		return startPoint;
	}
	
	public List<Point> getEndPointList() {
		return endPointList;
	}

	public void addEndPoint(Point end) {
		endPointList.add(end);
	}
	
	public boolean isEndPoint(Point end) {
		if (endPointList.contains(end))
			return true;
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((endPointList == null) ? 0 : endPointList.hashCode());
		result = prime * result
				+ ((startPoint == null) ? 0 : startPoint.hashCode());
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
		Line other = (Line) obj;
		if (endPointList == null) {
			if (other.endPointList != null)
				return false;
		} else if (!endPointList.equals(other.endPointList))
			return false;
		if (startPoint == null) {
			if (other.startPoint != null)
				return false;
		} else if (!startPoint.equals(other.startPoint))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("S:" + startPoint + "\n");
		for (Point point : endPointList) {
			buffer.append("\t->" + point + "\n");
		}
		return buffer.toString();
	}

}
