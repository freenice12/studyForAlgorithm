package day3FindAllTriangels.hardCo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.swt.graphics.Point;

public class FindTriangles {

	private List<Set<Point>> bigLines;
	private Point point1 = new Point(0, 0);
	private Point point2 = new Point(0, 10);
	private Point point3 = new Point(10, 10);
	private Point point4 = new Point(20, 10);
	private Point point5 = new Point(30, 10);
	private Point point6 = new Point(23, 13);
	private Point point7 = new Point(13, 16);
	private Point point8 = new Point(0, 20);
	private Point point9 = new Point(15, 20);
	private Point point10 = new Point(33, 20);
	private Point point11 = new Point(40, 20);

	public static void main(String[] args) {
		FindTriangles triangles = new FindTriangles();
		Map<Point, List<Point>> allLines = triangles.getLines();
		triangles.getTrianges(allLines);
	}

	public FindTriangles() {
		setStraightLine();
	}

	private void setStraightLine() {
		bigLines = new ArrayList<>();
		Set<Point> bLine1 = new HashSet<>();
		bLine1.add(point1);
		bLine1.add(point2);
		bLine1.add(point8);
		Set<Point> bLine2 = new HashSet<>();
		bLine2.add(point1);
		bLine2.add(point3);
		bLine2.add(point7);
		bLine2.add(point9);
		Set<Point> bLine3 = new HashSet<>();
		bLine3.add(point1);
		bLine3.add(point4);
		bLine3.add(point6);
		bLine3.add(point10);
		Set<Point> bLine4 = new HashSet<>();
		bLine4.add(point1);
		bLine4.add(point5);
		bLine4.add(point11);
		Set<Point> bLine5 = new HashSet<>();
		bLine5.add(point5);
		bLine5.add(point2);
		bLine5.add(point3);
		bLine5.add(point4);
		Set<Point> bLine6 = new HashSet<>();
		bLine6.add(point5);
		bLine6.add(point6);
		bLine6.add(point7);
		bLine6.add(point8);
		Set<Point> bLine7 = new HashSet<>();
		bLine7.add(point8);
		bLine7.add(point9);
		bLine7.add(point10);
		bLine7.add(point11);

		bigLines.add(bLine1);
		bigLines.add(bLine2);
		bigLines.add(bLine3);
		bigLines.add(bLine4);
		bigLines.add(bLine5);
		bigLines.add(bLine6);
		bigLines.add(bLine7);

	}

	private void getTrianges(Map<Point, List<Point>> allLines) {
		Set<Triangle> triangleSet = new HashSet<Triangle>();
		for (Entry<Point, List<Point>> firstLine : allLines.entrySet()) {
			Point firstPoint = firstLine.getKey();
			for (Point secondPoint : firstLine.getValue()) {
				for (Point thirdPoint : allLines.get(secondPoint)) {
					if (firstLine.getValue().contains(thirdPoint)) {
						Triangle triangle = new Triangle();
						triangle.addPoint(firstPoint);
						triangle.addPoint(secondPoint);
						triangle.addPoint(thirdPoint);
//						System.out.println(triangle);
						boolean isTriangle = true;
						for (Set<Point> lines : bigLines) {
							if (lines.containsAll(triangle.getPointSet())) {
								isTriangle = false;
							}
						}
						if (isTriangle) {
							triangleSet.add(triangle);
						}
					}
				}
			}
		}
		for (Triangle triangle : triangleSet) {
			System.out.println(triangle);
		}
		System.out.println(triangleSet.size());
	}

	private Map<Point, List<Point>> getLines() {

		Map<Point, List<Point>> lineMap = new HashMap<Point, List<Point>>();

		List<Point> endList1 = new ArrayList<Point>();
		endList1.add(point2);

		Line line1 = new Line(point1);
		line1.addEndPoint(point2);
		line1.addEndPoint(point3);
		line1.addEndPoint(point4);
		line1.addEndPoint(point5);
		line1.addEndPoint(point6);
		line1.addEndPoint(point7);
		line1.addEndPoint(point8);
		line1.addEndPoint(point9);
		line1.addEndPoint(point10);
		line1.addEndPoint(point11);
		lineMap.put(point1, line1.getEndPointList());
		Line line2 = new Line(point2);
		line2.addEndPoint(point3);
		line2.addEndPoint(point4);
		line2.addEndPoint(point5);
		line2.addEndPoint(point8);
		lineMap.put(point2, line2.getEndPointList());
		Line line3 = new Line(point3);
		line3.addEndPoint(point4);
		line3.addEndPoint(point5);
		line3.addEndPoint(point7);
		line3.addEndPoint(point9);
		lineMap.put(point3, line3.getEndPointList());
		Line line4 = new Line(point4);
		line4.addEndPoint(point5);
		line4.addEndPoint(point6);
		line4.addEndPoint(point10);
		lineMap.put(point4, line4.getEndPointList());
		Line line5 = new Line(point5);
		line5.addEndPoint(point6);
		line5.addEndPoint(point7);
		line5.addEndPoint(point8);
		line5.addEndPoint(point11);
		lineMap.put(point5, line5.getEndPointList());
		Line line6 = new Line(point6);
		line6.addEndPoint(point7);
		line6.addEndPoint(point8);
		line6.addEndPoint(point10);
		lineMap.put(point6, line6.getEndPointList());
		Line line7 = new Line(point7);
		line7.addEndPoint(point8);
		line7.addEndPoint(point9);
		lineMap.put(point7, line7.getEndPointList());
		Line line8 = new Line(point8);
		line8.addEndPoint(point9);
		line8.addEndPoint(point10);
		line8.addEndPoint(point11);
		lineMap.put(point8, line8.getEndPointList());
		Line line9 = new Line(point9);
		line9.addEndPoint(point10);
		line9.addEndPoint(point11);
		lineMap.put(point9, line9.getEndPointList());
		Line line10 = new Line(point10);
		line10.addEndPoint(point11);
		lineMap.put(point10, line10.getEndPointList());
		Line line11 = new Line(point11);
		lineMap.put(point11, line11.getEndPointList());

		return lineMap;
	}

}
