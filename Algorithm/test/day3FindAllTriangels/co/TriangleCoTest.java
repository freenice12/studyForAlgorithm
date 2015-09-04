package day3FindAllTriangels.co;

import java.awt.Point;

import org.junit.Test;

import day3AndDay5FindAllTriangels.co.TriangleCo;

public class TriangleCoTest {

	

	@Test
	public final void testGetArea() {
		TriangleCo t = new TriangleCo();
		t.addPoint(new Point(0,0));
		t.addPoint(new Point(100,0));
		t.addPoint(new Point(0,100));
//		System.out.println(t.getArea());
		
	}

}
