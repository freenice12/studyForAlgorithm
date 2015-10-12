package day7DeleteGame.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import day7DeleteGame.model.Board;
import day7DeleteGame.model.BoardLine;

public class GameComposite extends Composite implements PaintListener,
		MouseListener {

	static final Color DARK_GRAY = Display.getDefault().getSystemColor(
			SWT.COLOR_DARK_GRAY);
	static final Color RED = Display.getDefault().getSystemColor(SWT.COLOR_RED);
	static final Color BLUE = Display.getDefault().getSystemColor(
			SWT.COLOR_BLUE);
	static final Color WHITE = Display.getDefault().getSystemColor(
			SWT.COLOR_WHITE);
	protected Canvas canvas;
	protected GC gc;
	private Board board;
	private List<Region> regions = new ArrayList<Region>();
	private Map<Point, Boolean> enablePoints = new HashMap<>();
	private Map<Point, Boolean> selectedPoints = new HashMap<>();
	private ClientViewHandler clientViewHandler;

	public GameComposite(Composite parent, ClientViewHandler clientViewHandler) {
		super(parent, SWT.NONE);
		this.clientViewHandler = clientViewHandler;
		canvas = new Canvas(this, SWT.NO_BACKGROUND);
		gc = new GC(canvas);
		canvas.setSize(455, 400);
		canvas.addPaintListener(this);
		// canvas.addMouseListener(this);
	}

	public void updateView(Board gameBoard) {
		board = gameBoard;
		enablePoints.clear();
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				canvas.redraw();
			}
		});
	}

	@Override
	public void paintControl(PaintEvent paintevent) {
		if (board == null)
			return;
		for (int col = 0; col < board.getSize(); col++) {
			for (int row = 0; row < board.getElementSize(col); row++) {
				BoardLine line = board.getElementAt(col);
				Boolean element = line.getElement(row);
				Color color;
				if (element.equals(Boolean.TRUE)) {
					Point enable = new Point(col, row);
					enablePoints.put(enable, Boolean.TRUE);
					color = DARK_GRAY;
				} else {
					color = RED;
				}
				paintevent.gc.setBackground(color);
				Rectangle rec = new Rectangle(col * 45, row * 40, 35, 30);
				Region region = new Region();
				region.add(rec);
				regions.add(region);
				paintevent.gc.fillRectangle(rec);
			}
		}
	}

	@Override
	public void mouseDown(MouseEvent e) {
		boolean find = false;
		for (Region region : regions) {
			if (region.getBounds().contains(e.x, e.y)) {
				int col = region.getBounds().x / 40;
				int row = region.getBounds().y / 40;
				Point checkPoint = new Point(col, row);
				if (enablePoints.containsKey(checkPoint)) {
					clientViewHandler.enableSendButton(true);
					find = true;
					if (!selectedPoints.containsKey(checkPoint)) {
						for (Point point : selectedPoints.keySet()) {
							if (!isTheSameCol(checkPoint, point))
								return;
						}
						changeSelectedRegion(region, BLUE);
						selectedPoints.put(checkPoint, Boolean.FALSE);
					} else {
						changeSelectedRegion(region, DARK_GRAY);
						selectedPoints.remove(checkPoint);
						if (selectedPoints.isEmpty()) {
							clientViewHandler.enableSendButton(false);
						}
					}
				}
				if (find)
					return;
			}
		}
		System.out.println(selectedPoints);
	}

	private void changeSelectedRegion(Region region, Color color) {
		gc.setForeground(color);
		gc.setBackground(color);
		gc.fillRectangle(region.getBounds());
	}

	private boolean isTheSameCol(Point checkPoint, Point point) {
		return checkPoint.x == point.x;
	}

	@Override
	public void mouseDoubleClick(MouseEvent arg0) {
	}

	@Override
	public void mouseUp(MouseEvent arg0) {
	}

	public Set<Point> getSelectedPoints() {
		return selectedPoints.keySet();
	}

	public void pointRepoInit() {
		selectedPoints.clear();
	}

	public void enableGameComposite(final boolean b) {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				if (b)
					addListener(b);
				else
					addListener(b);
			}
		});
	}

	void addListener(boolean b) {
		if (b)
			canvas.addMouseListener(this);
		else
			canvas.removeMouseListener(this);
	}

	public void showResult(final boolean amI) {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				Font font = new Font(Display.getDefault(), "Tahoma", 18,
						SWT.BOLD);
				gc.setFont(font);
				gc.setForeground(WHITE);
				gc.setBackground(BLUE);
				StringBuffer text = new StringBuffer();
				if (amI)
					text.append("Done!!");
				else
					text.append("Well Done!");

				Point textSize = gc.textExtent(text.toString());
				gc.drawText(text.toString(), (canvas.getSize().x - textSize.x) / 2,
						(canvas.getSize().y - textSize.y) / 2);
			}
		});
	}

}