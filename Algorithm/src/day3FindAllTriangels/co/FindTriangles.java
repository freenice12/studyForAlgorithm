package day3FindAllTriangels.co;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class FindTriangles {
	private Line2D line1 = new Line2D.Double(0, 0, 0, 400);
	private Line2D line2 = new Line2D.Double(0, 0, 160, 400);
	private Line2D line3 = new Line2D.Double(0, 0, 280, 400);
	private Line2D line4 = new Line2D.Double(0, 0, 440, 400);
	private Line2D line5 = new Line2D.Double(0, 200, 220, 200);
	private Line2D line6 = new Line2D.Double(220, 200, 0, 400);
	private Line2D line7 = new Line2D.Double(0, 400, 440, 400);

	public static void main(String[] args) {
		FindTriangles finder = new FindTriangles();
		List<Line2D> allLines = finder.addAllLines();
		// Set<Point> allPoints = finder.getAllPoint(allLines);

		List<TriangleCo> triangles = finder.findTriangles(allLines);
		TrianglesViewer viewer = new TrianglesViewer(allLines);
		viewer.init(triangles);
	}

	private List<TriangleCo> findTriangles(List<Line2D> allLines) {
		List<TriangleCo> triangles = new ArrayList<>();
		for (int i = 0; i < allLines.size(); i++) {
			for (int j = 0; j < allLines.size(); j++) {
				for (int k = 0; k < allLines.size(); k++) {
					TriangleCo triangle = new TriangleCo();
					Line2D lineA = allLines.get(i);
					Line2D lineB = allLines.get(j);
					Line2D lineC = allLines.get(k);
					if (isDifferentLine(i, j, k)) {
						Point intersectionA = getIntersection(lineA, lineB);
						Point intersectionB = getIntersection(lineA, lineC);
						Point intersectionC = getIntersection(lineB, lineC);
						if (checkPoint(intersectionA, intersectionB,
								intersectionC)) {
							triangle.addPoint(intersectionA);
							triangle.addPoint(intersectionB);
							triangle.addPoint(intersectionC);
							if (!triangles.contains(triangle)) {
								triangles.add(triangle);
							}
						}
					}
				}
			}
		}
		return triangles;
	}

	private boolean isDifferentLine(int i, int j, int k) {
		return i != j && i != j && i != k;
	}

	private boolean checkPoint(Point intersectionA, Point intersectionB,
			Point intersectionC) {
		return !intersectionB.equals(new Point(-1, -1))
				&& !intersectionA.equals(new Point(-1, -1))
				&& !intersectionC.equals(new Point(-1, -1));
	}

	private Point getIntersection(Line2D lineA, Line2D lineB) {
		double distance = (lineA.getX1() - lineA.getX2())
				* (lineB.getY1() - lineB.getY2())
				- (lineA.getY1() - lineA.getY2())
				* (lineB.getX1() - lineB.getX2());
		if (distance == 0)
			return new Point(-1, -1);
		double xLocation = ((lineB.getX1() - lineB.getX2())
				* (lineA.getX1() * lineA.getY2() - lineA.getY1()
						* lineA.getX2()) - (lineA.getX1() - lineA.getX2())
				* (lineB.getX1() * lineB.getY2() - lineB.getY1()
						* lineB.getX2()))
				/ distance;
		double yLocation = ((lineB.getY1() - lineB.getY2())
				* (lineA.getX1() * lineA.getY2() - lineA.getY1()
						* lineA.getX2()) - (lineA.getY1() - lineA.getY2())
				* (lineB.getX1() * lineB.getY2() - lineB.getY1()
						* lineB.getX2()))
				/ distance;
		if (lineB.getX1() == lineB.getX2()) {
			if (yLocation < Math.min(lineA.getY1(), lineA.getY2())
					|| yLocation > Math.max(lineA.getY1(), lineA.getY2()))
				return new Point(-1, -1);
		}
		Point intersectionPoint = new Point((int) xLocation, (int) yLocation);
		if (xLocation < Math.min(lineA.getX1(), lineA.getX2())
				|| xLocation > Math.max(lineA.getX1(), lineA.getX2()))
			return new Point(-1, -1);
		if (xLocation < Math.min(lineB.getX1(), lineB.getX2())
				|| xLocation > Math.max(lineB.getX1(), lineB.getX2()))
			return new Point(-1, -1);
		return intersectionPoint;
	}

	public List<Line2D> addAllLines() {
		List<Line2D> allLines = new ArrayList<Line2D>();
		allLines.add(line1);
		allLines.add(line2);
		allLines.add(line3);
		allLines.add(line4);
		allLines.add(line5);
		allLines.add(line6);
		allLines.add(line7);
		return allLines;
	}

}
