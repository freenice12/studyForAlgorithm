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
	private Point point1 = new Point(5, 5);
	private Point point2 = new Point(5, 200);
	private Point point3 = new Point(80,200);
	private Point point4 = new Point(140, 200);
	private Point point5 = new Point(220, 200);
	private Point point6 = new Point(170, 240);
	private Point point7 = new Point(120, 290);
	private Point point8 = new Point(5, 400);
	private Point point9 = new Point(160, 400);
	private Point point10 = new Point(280, 400);
	private Point point11 = new Point(440, 400);

	public static void main(String[] args) {
		FindTriangles finder = new FindTriangles();
		Map<Point, List<Point>> allLines = finder.getLines();
		Map<Integer, Triangle> triangles = finder.getTrianges(allLines);
		TrianglesViewer viewer = new TrianglesViewer(triangles);
		viewer.init();
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

	@SuppressWarnings("boxing")
	private Map<Integer, Triangle> getTrianges(Map<Point, List<Point>> allLines) {
		Map<Integer, Triangle> triangleMap = new HashMap<Integer, Triangle>();
		int count = 0;
		
		for (Entry<Point, List<Point>> firstLine : allLines.entrySet()) {
			Point firstPoint = firstLine.getKey();
			for (Point secondPoint : firstLine.getValue()) {
				for (Point thirdPoint : allLines.get(secondPoint)) {
					if (firstLine.getValue().contains(thirdPoint)) {
						Triangle triangle = new Triangle();
						triangle.setFirstPoint(firstPoint);
						triangle.setSecondPoint(secondPoint);
						triangle.setThirdPoint(thirdPoint);
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
							triangleMap.put(count++, triangle);
						}
					}
				}
			}
		}
//		for (Triangle triangle : triangleMap.values()) {
//			System.out.println(triangle);
//		}
		return triangleMap;
	}

	private Map<Point, List<Point>> getLines() {

		Map<Point, List<Point>> lineMap = new HashMap<Point, List<Point>>();

		List<Point> endList1 = new ArrayList<Point>();
		endList1.add(point2);

		List<Point> endPoints = new ArrayList<Point>();
		endPoints.add(point2);
		endPoints.add(point3);
		endPoints.add(point4);
		endPoints.add(point5);
		endPoints.add(point6);
		endPoints.add(point7);
		endPoints.add(point8);
		endPoints.add(point9);
		endPoints.add(point10);
		endPoints.add(point11);
		lineMap.put(point1, endPoints);
//		Line endPoints2 = new Line(point2);
		List<Point> endPoints2 = new ArrayList<Point>();
		endPoints2.add(point3);
		endPoints2.add(point4);
		endPoints2.add(point5);
		endPoints2.add(point8);
		lineMap.put(point2, endPoints2);
//		Line line3 = new Line(point3);
		List<Point> endPoints3 = new ArrayList<Point>();
		endPoints3.add(point4);
		endPoints3.add(point5);
		endPoints3.add(point7);
		endPoints3.add(point9);
		lineMap.put(point3, endPoints3);
		List<Point> endPoints4 = new ArrayList<Point>();
		endPoints4.add(point5);
		endPoints4.add(point6);
		endPoints4.add(point10);
		lineMap.put(point4, endPoints4);
		List<Point> endPoints5 = new ArrayList<Point>();
		endPoints5.add(point6);
		endPoints5.add(point7);
		endPoints5.add(point8);
		endPoints5.add(point11);
		lineMap.put(point5, endPoints5);
		List<Point> endPoints6 = new ArrayList<Point>();
		endPoints6.add(point7);
		endPoints6.add(point8);
		endPoints6.add(point10);
		lineMap.put(point6, endPoints6);
		List<Point> endPoints7 = new ArrayList<Point>();
		endPoints7.add(point8);
		endPoints7.add(point9);
		lineMap.put(point7, endPoints7);
		List<Point> endPoints8 = new ArrayList<Point>();
		endPoints8.add(point9);
		endPoints8.add(point10);
		endPoints8.add(point11);
		lineMap.put(point8, endPoints8);
		List<Point> endPoints9 = new ArrayList<Point>();
		endPoints9.add(point10);
		endPoints9.add(point11);
		lineMap.put(point9, endPoints9);
		List<Point> endPoints10 = new ArrayList<Point>();
		endPoints10.add(point11);
		lineMap.put(point10, endPoints10);
		List<Point> endPoints11 = new ArrayList<Point>();
		lineMap.put(point11, endPoints11);

		return lineMap;
	}

}
