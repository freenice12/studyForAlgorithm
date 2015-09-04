package day3AndDay5FindAllTriangels.co;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class FindTriangles {
	public static Line2D LINE1 = new Line2D.Double(0, 0, 0, 400);
	public static Line2D LINE2 = new Line2D.Double(0, 0, 160, 400);
	public static Line2D LINE3 = new Line2D.Double(0, 0, 280, 400);
	public static Line2D LINE4 = new Line2D.Double(0, 0, 440, 400);
	public static Line2D LINE5 = new Line2D.Double(0, 200, 220, 200);
	public static Line2D LINE6 = new Line2D.Double(220, 200, 0, 400);
//	public static Line2D LINE7 = new Line2D.Double(0, 200, 440, 400);
	public static Line2D LINE8 = new Line2D.Double(0, 400, 440, 400);

	public static void main(String[] args) {
		FindTriangles finder = new FindTriangles();
		List<Line2D> allLines = FindTriangles.addAllLines();
		// Set<Point> allPoints = finder.getAllPoint(allLines);
		List<TriangleCo> triangles = finder.findTriangles(allLines);
		triangles = triangleSort(triangles, new Point(0, 0));
		Collections.sort(triangles);
		TrianglesViewer viewer = new TrianglesViewer(allLines);
		viewer.init(triangles);
	}

	private static List<TriangleCo> triangleSort(List<TriangleCo> triangles,
			Point firstPoint) {
		List<TriangleCo> result = new ArrayList<>();
		Point nextPoint = firstPoint;
		while (triangles.size() != result.size()) {
			for (TriangleCo triangle : triangles) {
				if (triangle.contains(nextPoint)
						&& !result.contains(triangle)) {
					result.add(triangle);
				}
			}
			nextPoint = getNextPoint(triangles, result);
		}
		return result;
	}

	private static Point getNextPoint(List<TriangleCo> triangles,
			List<TriangleCo> result) {
		for (TriangleCo triangle : triangles) {
			if (!result.contains(triangle)) {
				return triangle.getAnyPoint();
			}
		}
		return null;
	}

	private List<TriangleCo> findTriangles(List<Line2D> allLines) {
		List<TriangleCo> triangles = new CopyOnWriteArrayList<>();
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
							if (!triangles.contains(triangle)
									&& triangle.isTriangle()) {
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

	// private Set<Point> getAllPoint(List<Line2D> allLines) {
	// Set<Point> allPoint = new HashSet<Point>();
	// for (int index = 0; index < allLines.size(); index++) {
	// for (Line2D line : allLines) {
	// if (!allLines.get(index).equals(line)) {
	// Point intersection = getIntersection(allLines.get(index),
	// line);
	// if (!intersection.equals(new Point(-1, -1))) {
	// allPoint.add(intersection);
	// }
	// }
	// }
	// }
	// return allPoint;
	// }

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

	public static List<Line2D> addAllLines() {
		List<Line2D> allLines = new ArrayList<Line2D>();
		allLines.add(LINE1);
		allLines.add(LINE2);
		allLines.add(LINE3);
		allLines.add(LINE4);
		allLines.add(LINE5);
		allLines.add(LINE6);
//		allLines.add(LINE7);
		allLines.add(LINE8);
		return allLines;
	}

}
