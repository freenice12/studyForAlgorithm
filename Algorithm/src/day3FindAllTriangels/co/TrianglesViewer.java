package day3FindAllTriangels.co;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.List;
import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class TrianglesViewer {

	private static final int TIMER_INTERVAL = 1000;
	Display display;
	private Shell shell;
	private Composite mainComposite;
	private Canvas canvas;
	GC gc;

	private List<TriangleCo> triangleList;
	int[] pointArray = new int[6];

	int index;
	Random random = new Random(255);

	public void animate() {
		if (index < triangleList.size()) {
			TriangleCo selectedTriangle = triangleList.get(index);
			int arrayIndex = 0;
			for (Point point : selectedTriangle.getPointSet()) {
				pointArray[arrayIndex++] = point.x;
				pointArray[arrayIndex++] = point.y;
			}
			canvas.redraw();
			index++;
		}
	}

	private Point getCenter() {
		int x = (pointArray[0] + pointArray[2] + pointArray[4]) / 3;
		int y = (pointArray[1] + pointArray[3] + pointArray[5]) / 3;
		return new Point(x, y);
	}

	public TrianglesViewer(final List<Line2D> allLines) {
		display = new Display();
		shell = new Shell(display);
		shell.setText("Triangles!");
		shell.setSize(550, 550);
		shell.setLayout(new GridLayout(1, false));

		mainComposite = new Composite(shell, SWT.BORDER);
		mainComposite.setLayout(new GridLayout(1, false));
		mainComposite
				.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		canvas = new Canvas(mainComposite, SWT.NO_BACKGROUND);
		canvas.setSize(500, 500);
		canvas.setLayout(new GridLayout(1, false));
		canvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		gc = new GC(canvas);
		canvas.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent arg0) {
				gc.setBackground(new Color(Display.getCurrent(), 255, 255, 255));
				gc.fillRectangle(Display.getCurrent().getClientArea());
				
				gc.setBackground(new Color(Display.getCurrent(), random
						.nextInt(255), random.nextInt(255), random.nextInt(255)));

				for (Line2D lineA : allLines) {
					gc.drawLine((int) lineA.getX1(), (int) lineA.getY1(),
							(int) lineA.getX2(), (int) lineA.getY2());
				}
				if (index != 0) {
					Point center = getCenter();
					Font font = new Font(Display.getCurrent(), new FontData(
							"Serif", 12, index));
					gc.setFont(font);
					gc.fillPolygon(pointArray);
					gc.drawString((index) + "", center.x, center.y);
				}

			}
		});

		canvas.addListener(SWT.MouseDown, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				if (arg0.type == 3) {
					System.out.println(arg0.x + " / " + arg0.y);
				}

			}
		});
	}

	public void init(final List<TriangleCo> triangles) {
		triangleList = triangles;
		shell.open();
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				animate();
				display.timerExec(TIMER_INTERVAL, this);
			}
		};
		display.timerExec(TIMER_INTERVAL, runnable);
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.timerExec(-1, runnable);
		display.dispose();
	}
}
