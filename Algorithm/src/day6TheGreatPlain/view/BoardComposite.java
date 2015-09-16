package day6TheGreatPlain.view;

import java.awt.Point;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class BoardComposite extends Composite {

	private Canvas canvas;

	public BoardComposite(Composite parent) {
		super(parent, SWT.BORDER);
	}

	public void buildComposite(final Map<Point, Integer> board) {
		canvas = new Canvas(this, SWT.NO_BACKGROUND);
		canvas.setSize(410, 410);

		canvas.addPaintListener(new PaintListener() {
			@SuppressWarnings("boxing")
			@Override
			public void paintControl(PaintEvent e) {
				for (int x=0; x<40; x++) {
					for (int y=0; y<40; y++) {
						int num = board.get(new Point(x, y));
						Color back = new Color(Display.getCurrent(), 255 - num * 24, 255 - num * 24, 255 - num * 24);
						e.gc.setBackground(back);
						e.gc.fillRectangle(x*10, y*10, 10, 10);
					}
				}
				
			}
		});
	}

//	public void buildComposite(final Map<Point, Integer> board,
//			final Map<Point, Integer> givenPoint) {
//		canvas = new Canvas(this, SWT.NO_BACKGROUND);
//		canvas.setSize(410, 410);
//
//		canvas.addPaintListener(new PaintListener() {
//			@SuppressWarnings("boxing")
//			@Override
//			public void paintControl(PaintEvent e) {
//				for (int x=0; x<40; x++) {
//					for (int y=0; y<40; y++) {
//						int num = board.get(new Point(x, y));
//						Color back = new Color(Display.getCurrent(), 255 - num * 24, 255 - num * 24, 255 - num * 24);
//						e.gc.setBackground(back);
//						e.gc.fillRectangle(x*10, y*10, 10, 10);
//						e.gc.setForeground(new Color(Display.getCurrent(), 250, 0, 0));
//						e.gc.drawText((board.get(new Point(x, y)).toString()), x * 10, y * 10);
//						if (givenPoint.containsKey(new Point(x, y))) {
//							e.gc.setForeground(new Color(Display.getCurrent(), 250, 0, 0));
//							e.gc.drawText((givenPoint.get(new Point(x, y)).toString()), x * 10, y * 10);
//						}
//					}
//				}
//				
//			}
//		});
//	}
}
