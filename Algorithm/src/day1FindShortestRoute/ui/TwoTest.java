package day1FindShortestRoute.ui;

import java.awt.Point;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import day1FindShortestRoute.data.History;
import day1FindShortestRoute.enums.CharacterType;
import day1FindShortestRoute.interfaces.Character;
import day1FindShortestRoute.interfaces.Jerry;

public class TwoTest {

	private Display display = new Display();
	private Canvas canvas;
	private int turn;
	private History history;

	private Map<Integer, History> historyMap = new LinkedHashMap<Integer, History>();
	private Jerry jerry;

	public TwoTest(Map<Integer, History> history) {
		this.historyMap = history;
	}

	public TwoTest(Jerry jerry) {
		this.jerry = jerry;
	}

	public int getHistorySize() {
		return historyMap.size();
	}

	public History getNextTurn(int turn) {
		return historyMap.get(turn);
	}

	public Map<Integer, History> getHistory() {
		return historyMap;
	}

	public void setHistory(Map<Integer, History> history) {
		this.historyMap = history;
	}

	public void run() {
		Shell shell = new Shell(display);
		shell.setText("Line Example");
		shell.setSize(500, 500);
		createContents(display, shell);
		shell.open();
		while (!shell.isDisposed()) {

			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	public void setHistory(int turn, History history) {
		drawMap(turn, history);
	}

	public void createContents(final Display display, Shell shell) {
		shell.setLayout(new FillLayout());
		canvas = new Canvas(shell, SWT.NONE);

		canvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				Canvas canvas = (Canvas) e.widget;
				int maxX = canvas.getSize().x;
				int maxY = canvas.getSize().y;

				e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_BLACK));

				e.gc.setLineWidth(1);

				for (int i = 10; i < maxX; i += 10) {
					e.gc.drawLine(i, 0, i, maxY);
				}

				for (int i = 10; i < maxX; i += 10) {
					e.gc.drawLine(0, i, maxX, i);
				}
			}
		});
		setHistory(jerry.getHistorySize()-1, jerry.getNextTurn(jerry.getHistorySize()-1));
//		setHistory(jerry.getHistorySize()-1, jerry.getNextTurn(jerry.getHistorySize()-1));
//		for (int turn = 0; turn < jerry.getHistorySize(); turn++) {
//			setHistory(turn, jerry.getNextTurn(turn));
//			try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
	}

	private void drawMap(final int turn, final History history) {
		canvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				for (Entry<Point, Character> entry : history.getHistory()
						.entrySet()) {
					if (entry.getValue().getType().equals(CharacterType.EMPTY)) {
						continue;
					} else if (entry.getValue().getType()
							.equals(CharacterType.JERRY)) {
						e.gc.setBackground(display
								.getSystemColor(SWT.COLOR_RED));
						e.gc.fillOval(entry.getKey().x * 10,
								entry.getKey().y * 10, 10, 10);
					} else if (entry.getValue().getType()
							.equals(CharacterType.OBSTACLE)) {
						e.gc.setBackground(display
								.getSystemColor(SWT.COLOR_BLACK));
						e.gc.fillOval(entry.getKey().x * 10,
								entry.getKey().y * 10, 10, 10);
					} else {
						e.gc.setBackground(display
								.getSystemColor(SWT.COLOR_CYAN));
						e.gc.fillOval(entry.getKey().x * 10,
								entry.getKey().y * 10, 10, 10);
					}

				}

			}
		});
	}

}
