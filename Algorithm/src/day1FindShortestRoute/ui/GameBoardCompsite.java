package day1FindShortestRoute.ui;

import java.awt.Point;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import day1FindShortestRoute.data.History;
import day1FindShortestRoute.enums.CharacterType;
import day1FindShortestRoute.interfaces.Character;

public class GameBoardCompsite extends Composite {

	private Canvas canvas;
	private TomJerryViewer tomJerryViewer;

	public GameBoardCompsite(Composite parent, TomJerryViewer tomJerryViewer) {
		super(parent, SWT.BORDER);
		this.tomJerryViewer = tomJerryViewer;
		buildMapComposite();
	}

	private void buildMapComposite() {
		canvas = new Canvas(this, SWT.NONE);
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
	}

	public void updateHistoryComposite(Map<Integer, History> history) {
//		for (int turn = 0; turn < history.size(); turn++) {
//			for (Entry<Point, Character> entry : history.getHistory()
//					.entrySet()) {
//				if (entry.getValue().getType().equals(CharacterType.EMPTY)) {
//					continue;
//				} else if (entry.getValue().getType()
//						.equals(CharacterType.JERRY)) {
//					e.gc.setBackground(display
//							.getSystemColor(SWT.COLOR_RED));
//					e.gc.fillOval(entry.getKey().x * 10,
//							entry.getKey().y * 10, 10, 10);
//				} else if (entry.getValue().getType()
//						.equals(CharacterType.OBSTACLE)) {
//					e.gc.setBackground(display
//							.getSystemColor(SWT.COLOR_BLACK));
//					e.gc.fillOval(entry.getKey().x * 10,
//							entry.getKey().y * 10, 10, 10);
//				} else {
//					e.gc.setBackground(display
//							.getSystemColor(SWT.COLOR_CYAN));
//					e.gc.fillOval(entry.getKey().x * 10,
//							entry.getKey().y * 10, 10, 10);
//				}
//
//			}
//
//		}
	}

}
