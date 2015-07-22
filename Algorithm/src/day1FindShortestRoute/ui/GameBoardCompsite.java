package day1FindShortestRoute.ui;

import java.awt.Point;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import day1FindShortestRoute.interfaces.Character;

public class GameBoardCompsite extends Composite {


	private Canvas canvas;
	private GC gc;

	public GameBoardCompsite(Composite parent) {
		super(parent, SWT.BORDER);
		buildMapComposite();
	}

	private void buildMapComposite() {
		canvas = new Canvas(this, SWT.NO_BACKGROUND);
		canvas.setSize(810, 810);

		canvas.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				int maxX = 800;
				int maxY = 800;

				e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_BLACK));

				e.gc.setLineWidth(1);

				for (int i = 0; i <= maxX; i += 8) {
					e.gc.drawLine(i, 0, i, maxY);
				}

				for (int i = 0; i <= maxX; i += 8) {
					e.gc.drawLine(0, i, maxX, i);
				}
			}
		});
		gc  = new GC(canvas);
	}
	
	public void updateHistoryComposite(Map<Point, Character> mapStatus,
			List<Point> shortestRoute, boolean showRoute) {
		canvas.update();
		this.update();
		canvas = new Canvas(this, SWT.NO_BACKGROUND);
		canvas.setSize(810, 810);
		Device device = Display.getCurrent();
		Color red = new Color(device, 255, 0, 0);
		Color black = new Color(device, 255, 255, 255);
		Color blue = new Color(device, 0, 0, 255);
		
		int maxX = 800;
		int maxY = 800;

		gc.setForeground(new Color(device, 0, 0, 0));
		gc.setLineWidth(1);

		for (int i = 0; i <= maxX; i += 8) {
			gc.drawLine(i, 0, i, maxY);
		}

		for (int i = 0; i <= maxX; i += 8) {
			gc.drawLine(0, i, maxX, i);
		}

		for (Entry<Point, Character> entry : mapStatus.entrySet()) {
			org.eclipse.swt.graphics.Point newPoint = new org.eclipse.swt.graphics.Point(entry.getKey().y, entry.getKey().x);
			switch (entry.getValue().getType()) {
			case EMPTY:
				gc.setBackground(black);
				gc.fillOval(newPoint.x * 8, newPoint.y * 8, 8, 8);
				continue;
			case JERRY:
				gc.setBackground(red);
				break;
			case OBSTACLE:
				gc.setBackground(new Color(device, 0, 0, 0));
				break;
			case DOOR:
				gc.setBackground(blue);
				break;
			}
			gc.fillOval(newPoint.x * 8, newPoint.y * 8, 8, 8);
		}
		
		if (showRoute) {
			for (Point routePoint : shortestRoute) {
				org.eclipse.swt.graphics.Point newPoint = new org.eclipse.swt.graphics.Point(routePoint.y, routePoint.x);
				gc.setBackground(new Color(device, 0, 255, 0));
				gc.fillOval(newPoint.x * 8, newPoint.y * 8, 8, 8);
			}
		}
		
		canvas.redraw();
	}

}
