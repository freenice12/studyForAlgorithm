package day3FindAllTriangels.hardCo;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class TrianglesViewer {

	private Display display;
	private Shell shell;
	private Composite mainComposite;
	private Canvas canvas;
	GC gc;

	public TrianglesViewer(final Map<Integer, Triangle> triangleMap) {
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
		Device device = Display.getCurrent();
		Color white = new Color(device, 0, 0, 0);
		final Color red = new Color(device, 255, 0, 0);
		final Color black = new Color(device, 255, 255, 255);
		Color blue = new Color(device, 0, 0, 255);

		canvas.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent arg0) {
				for (Entry<Integer, Triangle> entry : triangleMap.entrySet()) {
					gc.setBackground(red);
					Triangle triangle = entry.getValue();
					for (Point point : triangle.getPointSet()) {
						gc.fillOval(point.x, point.y, 10, 10);
					}
					gc.setBackground(black);
					gc.setLineWidth(3);
					gc.drawLine(triangle.getFirstPoint().x, triangle.getFirstPoint().y, triangle.getSecondPoint().x, triangle.getSecondPoint().y);
					gc.drawLine(triangle.getSecondPoint().x, triangle.getSecondPoint().y, triangle.getThirdPoint().x, triangle.getThirdPoint().y);
					gc.drawLine(triangle.getThirdPoint().x, triangle.getThirdPoint().y, triangle.getFirstPoint().x, triangle.getFirstPoint().y);
					
				}

			}
		});
		
		canvas.addListener(SWT.MouseDown, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				if (arg0.type == 3) {
					System.out.println(arg0.x+" / "+arg0.y);
				}
				
			}
		});
		
		int counter = 0;
		Text counterText = new Text(mainComposite, SWT.NONE);
		counterText.setText(counter+"");

	}

	public void init() {
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
