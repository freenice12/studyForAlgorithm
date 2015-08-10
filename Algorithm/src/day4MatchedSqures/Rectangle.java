package day4MatchedSqures;

import org.eclipse.swt.graphics.Point;

public class Rectangle {
	private Point start;
	private Point end;
	private int sum;
	private int size;
	
	public Rectangle() { }
	
	public Rectangle(int startX, int startY, int size) throws Exception {
		if (invalid(startX, startY, size))
			throw new Exception("Cannot create Rectangle from ("+startX+", "+startY+") size: "+size);
		int endPoint = size-1;
		this.size = size;
		start = new Point(startX, startY);
		end = new Point(startX+endPoint, startY+endPoint);
	}

	private boolean invalid(int startX, int startY, int size) {
		return size < 1 || size > GameBoard.SIZE || size + startX > GameBoard.SIZE || size + startY > GameBoard.SIZE;
	}
	
	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}
	
	public Point getStart() {
		return start;
	}

	public Point getEnd() {
		return end;
	}
	
	public boolean hasPoint(Point target) {
		if (start.x <= target.x && target.x <= end.x) {
			if (start.y <= target.y && target.y <= end.y)
				return true;
		}
		return false;
	}
	
	public boolean hasRectangle(Rectangle target) {
		for (int from = target.getStart().x; from < target.getEnd().x; from++) {
			for (int to = target.getStart().y; to < target.getEnd().y; to++) {
				if (hasPoint(new Point(from, to)))
					return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
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
		Rectangle other = (Rectangle) obj;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "From: "+start+" To: "+end+" Sum: "+sum+"(size: "+size+")";
	}
	
	
}
