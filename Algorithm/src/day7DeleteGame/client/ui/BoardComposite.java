package day7DeleteGame.client.ui;

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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import day7DeleteGame.model.Board;
import day7DeleteGame.model.BoardLine;

public class BoardComposite extends Composite implements PaintListener,
		MouseListener {
	protected NewClientView view;
	private int width;
	private int height;
	static final Color BACKGROUND = Display.getDefault().getSystemColor(
			SWT.COLOR_WIDGET_BACKGROUND);
	static final Color RED = Display.getDefault().getSystemColor(SWT.COLOR_RED);
	static final Color BLUE = Display.getDefault().getSystemColor(
			SWT.COLOR_BLUE);
	static final Color WHITE = Display.getDefault().getSystemColor(
			SWT.COLOR_WHITE);
	protected Canvas canvas;
	protected GC gc;
	// protected Font font = new Font(Display.getDefault(), "Tahoma", 18,
	// SWT.BOLD);
	private Board board;
	private List<Region> regions = new ArrayList<Region>();
	private Map<Point, Boolean> enablePoints = new HashMap<>();
	private Map<Point, Boolean> selectedPoints = new HashMap<>();
	private int maxXsize;
	private int maxYsize;
	private boolean changed;
	private List<Rectangle> recs = new ArrayList<>();

	public BoardComposite(NewClientView clientView) {
		super(clientView.mainComposite, SWT.BORDER);
		this.view = clientView;

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		gridLayout.makeColumnsEqualWidth = false;
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gridData.heightHint = view.display.getClientArea().height * 2 / 3;
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		setLayout(gridLayout);
		setLayoutData(gridData);

		createContent();
		
//		enableGameComposite(true);
	}

	private void createContent() {
		canvas = new Canvas(this, SWT.NO_BACKGROUND);
		gc = new GC(canvas);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		canvas.setLayoutData(gridData);

		canvas.addPaintListener(this);
	}

	@Override
	public void paintControl(PaintEvent paintevent) {
		prepareCanvas();
		if (board == null)
			return;
		selectedPoints.clear();
		for (int col = 0; col < board.getSize(); col++) {
			for (int row = 0; row < board.getElementSize(col); row++) {
				BoardLine line = board.getElementAt(col);
				Boolean element = line.getElement(row);
				Rectangle rec = new Rectangle(getX(col), getY(row),	40, 40);
				Region region = new Region();
				region.add(rec);
				regions.add(region);
				recs .add(rec);
				Point enable = new Point(col, row);
				if (element.equals(Boolean.TRUE)) {
					enablePoints.put(enable, Boolean.TRUE);
					paintevent.gc.setBackground(RED);
				} else if (element.equals(Boolean.FALSE) && enablePoints.containsKey(enable)){
					paintevent.gc.setBackground(BLUE);
					selectedPoints.put(enable, Boolean.FALSE);
				} else {
					paintevent.gc.setBackground(BACKGROUND);
				}
				paintevent.gc.fillOval(rec.x, rec.y, rec.width, rec.height);
			}
		}
	}

	protected void prepareCanvas() {
		gc.setBackground(BACKGROUND);
		gc.setAlpha(255);
		gc.fillRectangle(canvas.getClientArea());
		width = canvas.getClientArea().width;
		height = canvas.getClientArea().height;
	}

	public void enableGameComposite(final boolean b) {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				setListener(b);
			}
		});
	}

	void setListener(boolean b) {
		if (b)
			canvas.addMouseListener(this);
		else
			canvas.removeMouseListener(this);
	}

	@Override
	public void mouseDown(MouseEvent e) {
		boolean find = false;
		for (Rectangle rec : recs) {
			if (rec.contains(e.x, e.y)) {
				int col = rec.x / getWidth();
				int row = rec.y / getHight();
				Point checkPoint = new Point(col, row);
				if (enablePoints.containsKey(checkPoint)) {
					changed = true;
					view.enableSendButton(true);
					find = true;
					if (!selectedPoints.containsKey(checkPoint)) {
						for (Point point : selectedPoints.keySet()) {
							if (!isTheSameCol(checkPoint, point))
								return;
						}
						changeSelectedRegion(rec, true);
						selectedPoints.put(checkPoint, Boolean.FALSE);
					} else {
						changeSelectedRegion(rec, false);
						selectedPoints.remove(checkPoint);
						if (selectedPoints.isEmpty()) {
							changed = false;
							view.enableSendButton(false);
						}
					}
				}
				if (find)
					return;
			}
		}
	}

	private void changeSelectedRegion(Rectangle rec, boolean selected) {
		if (selected) {
			gc.setBackground(BLUE);
		} else {
			gc.setBackground(RED);
		}
		gc.fillOval(rec.x, rec.y, rec.width, rec.height);
	}

	private boolean isTheSameCol(Point checkPoint, Point point) {
		return checkPoint.x == point.x;
	}

	private int getX(int col) {
		return (col * getWidth()) < 0 ? 0 : (col * getWidth());
	}

	private int getWidth() {
		return (width / maxXsize);
	}

	private int getY(int row) {
		return (row * getHight()) < 0 ? 0 : (row * getHight());
	}

	private int getHight() {
		return (height / maxYsize);
	}

	public void updateView(Board gameBoard) {
		board = gameBoard;
//		System.out.println("==============");
//		System.out.println(board);
//		System.out.println("==============");
		maxXsize = board.getSize();
		maxYsize = board.getMaxColSize();
		enablePoints.clear();
		canvasRedraw();
	}

	private void canvasRedraw() {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				canvas.redraw();
			}
		});
	}

	@Override
	public void mouseDoubleClick(MouseEvent mouseevent) {
	}

	@Override
	public void mouseUp(MouseEvent mouseevent) {
	}

	public void showResult(boolean b) {
		Font font = new Font(Display.getDefault(), "Tahoma", 18, SWT.BOLD);
        gc.setFont(font);

        String text = null;
		int cliWidth = getClientArea().width;
		int cliHeight = getClientArea().height;
		if (b) {
			setGc(WHITE, RED, 180, cliWidth, cliHeight);
			text = "WIN!!!";
		} else {
			setGc(BACKGROUND, BLUE, 180, cliWidth, cliHeight);
			text = "WIN?";
		}
		Point textSize = gc.textExtent(text);
		gc.drawText(text, (canvas.getSize().x - textSize.x)/2, (canvas.getSize().y - textSize.y)/2);
		view.enableReadyButton(true);
		view.enableAutoButton(false);
		view.enablePassButton(false);
		view.enableSendButton(false);
	}

	private void setGc(Color back, Color fore, int alpha, int width, int height) {
		gc.setBackground(back);
		gc.setForeground(fore);
		gc.setAlpha(alpha);
		gc.fillRectangle(0, 0, width, height);
	}

	public Set<Point> getSelectedPoint() {
		return selectedPoints.keySet();
	}

//	public void showBest(Board changedBoard) {
//		Board tempBoard = board;
//		board = changedBoard;
//		canvasRedraw();
//		board = tempBoard;
//	}

	public void showBest(int deleteIndex, int deleteCount) {
		if (deleteCount > 0)
			changed = true;
		board.switchAt(deleteIndex, deleteCount);
		canvasRedraw();
	}

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

}
