package day7DeleteGame.client.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
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
	private NewClientView view;
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
	protected ClientViewHandler clientViewHandler;
	private int maxXsize;
	private int maxYsize;

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
		
		enableGameComposite(true);
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
		for (int col = 0; col < board.getSize(); col++) {
			for (int row = 0; row < board.getElementSize(col); row++) {
				BoardLine line = board.getElementAt(col);
				Boolean element = line.getElement(row);
				Rectangle rec = new Rectangle(getX(col), getY(row),	40, 40);
				Region region = new Region();
				region.add(rec);
				regions.add(region);
				if (element.equals(Boolean.TRUE)) {
					Point enable = new Point(col, row);
					enablePoints.put(enable, Boolean.TRUE);
					paintevent.gc.setBackground(RED);
				} else {
					paintevent.gc.setBackground(BACKGROUND);
				}
				paintevent.gc.fillOval(rec.x, rec.y, rec.width, rec.height);
			}
		}
	}

	protected void prepareCanvas() {
		gc.setBackground(BACKGROUND);
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
		for (Region region : regions) {
			if (region.getBounds().contains(e.x, e.y)) {
				int col = region.getBounds().x / getWidth();
				int row = region.getBounds().y / getHight();
				Point checkPoint = new Point(col, row);
				System.out.println(checkPoint);
				if (enablePoints.containsKey(checkPoint)) {
//					clientViewHandler.enableSendButton(true);
					find = true;
					if (!selectedPoints.containsKey(checkPoint)) {
						for (Point point : selectedPoints.keySet()) {
							if (!isTheSameCol(checkPoint, point))
								return;
						}
						changeSelectedRegion(region, true);
						selectedPoints.put(checkPoint, Boolean.FALSE);
					} else {
						changeSelectedRegion(region, false);
						selectedPoints.remove(checkPoint);
						if (selectedPoints.isEmpty()) {
//							clientViewHandler.enableSendButton(false);
						}
					}
				}
				if (find)
					return;
			}
		}
	}

	private void changeSelectedRegion(Region region, boolean selected) {
		if (selected) {
			gc.setBackground(BLUE);
		} else {
			gc.setBackground(RED);
		}
		gc.fillOval(region.getBounds().x, region.getBounds().y, region.getBounds().width, region.getBounds().height);
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
		System.out.println("==============");
		System.out.println(board);
		System.out.println("==============");
		maxXsize = board.getSize();
		maxYsize = board.getMaxColSize();
		enablePoints.clear();
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

}

// extends Composite implements PaintListener,
// MouseListener {
//
// private static final int width = 455;
// private static final int HIGHT = 400;
// static final Color GRAY = Display.getDefault().getSystemColor(
// SWT.COLOR_GRAY);
// static final Color RED = Display.getDefault().getSystemColor(SWT.COLOR_RED);
// static final Color BLUE = Display.getDefault().getSystemColor(
// SWT.COLOR_BLUE);
// static final Color WHITE = Display.getDefault().getSystemColor(
// SWT.COLOR_WHITE);
// protected Canvas canvas;
// protected GC gc;
// protected Font font = new Font(Display.getDefault(), "Tahoma", 18, SWT.BOLD);
// private Board board;
// private List<Region> regions = new ArrayList<Region>();
// private Map<Point, Boolean> enablePoints = new HashMap<>();
// private Map<Point, Boolean> selectedPoints = new HashMap<>();
// protected ClientViewHandler clientViewHandler;
// private int maxXsize;
// private int maxYsize;
//
// public GameComposite(Composite parent, ClientViewHandler clientViewHandler) {
// super(parent, SWT.NONE);
// this.clientViewHandler = clientViewHandler;
// canvas = new Canvas(this, SWT.NO_BACKGROUND);
// gc = new GC(canvas);
// canvas.setSize(width, HIGHT);
// canvas.addPaintListener(this);
// }
//
// public void updateView(Board gameBoard) {
// board = gameBoard;
// maxXsize = board.getSize();
// maxYsize = board.getMaxColSize();
// enablePoints.clear();
// Display.getDefault().syncExec(new Runnable() {
// @Override
// public void run() {
// canvas.redraw();
// }
// });
// }
//
// @Override
// public void paintControl(PaintEvent paintevent) {
// prepareCanvas();
// if (board == null)
// return;
// for (int col = 0; col < board.getSize(); col++) {
// for (int row = 0; row < board.getElementSize(col); row++) {
// BoardLine line = board.getElementAt(col);
// Boolean element = line.getElement(row);
// Rectangle rec = new Rectangle(getX(col), getY(row),
// getWidth() - 10, getHight() - 10);
// Region region = new Region();
// region.add(rec);
// regions.add(region);
// if (element.equals(Boolean.TRUE)) {
// Point enable = new Point(col, row);
// enablePoints.put(enable, Boolean.TRUE);
// // paintevent.gc.setForeground(GRAY);
// paintevent.gc.setBackground(RED);
// } else {
// // paintevent.gc.setForeground(WHITE);
// paintevent.gc.setBackground(GRAY);
// }
// paintevent.gc.fillRectangle(rec);
// // paintevent.gc.fillGradientRectangle(rec.x, rec.y, rec.width,
// // rec.height, element.booleanValue());
// }
// }
// }
//
// private int getX(int col) {
// return (col * getWidth()) < 0 ? 0 : (col * getWidth());
// }
//
// private int getWidth() {
// return (width / maxXsize);
// }
//
// private int getY(int row) {
// return (row * getHight()) < 0 ? 0 : (row * getHight());
// }
//
// private int getHight() {
// return (HIGHT / maxYsize);
// }
//
// @Override
// public void mouseDown(MouseEvent e) {
// boolean find = false;
// for (Region region : regions) {
// if (region.getBounds().contains(e.x, e.y)) {
// int col = region.getBounds().x / getWidth();
// int row = region.getBounds().y / getHight();
// Point checkPoint = new Point(col, row);
// if (enablePoints.containsKey(checkPoint)) {
// clientViewHandler.enableSendButton(true);
// find = true;
// if (!selectedPoints.containsKey(checkPoint)) {
// for (Point point : selectedPoints.keySet()) {
// if (!isTheSameCol(checkPoint, point))
// return;
// }
// changeSelectedRegion(region, true);
// selectedPoints.put(checkPoint, Boolean.FALSE);
// } else {
// changeSelectedRegion(region, false);
// selectedPoints.remove(checkPoint);
// if (selectedPoints.isEmpty()) {
// clientViewHandler.enableSendButton(false);
// }
// }
// }
// if (find)
// return;
// }
// }
// //System.out.println(selectedPoints);
// }
//
// private void changeSelectedRegion(Region region, boolean selected) {
// if (selected) {
// // gc.setForeground(RED);
// gc.setBackground(BLUE);
//
// // int in = 5;
// // gc.fillGradientRectangle(region.getBounds().x + in,
// // region.getBounds().y + in, region.getBounds().width - in*2,
// // region.getBounds().height - in*2, true);
// } else {
// // gc.setForeground(GRAY);
// gc.setBackground(RED);
// // gc.fillGradientRectangle(region.getBounds().x,
// // region.getBounds().y, region.getBounds().width,
// // region.getBounds().height, true);
// }
// gc.fillRectangle(region.getBounds());
// }
//
// private boolean isTheSameCol(Point checkPoint, Point point) {
// return checkPoint.x == point.x;
// }

// @Override
// public void mouseDoubleClick(MouseEvent arg0) {
// }
//
// @Override
// public void mouseUp(MouseEvent arg0) {
// }
//
// public Set<Point> getSelectedPoints() {
// return selectedPoints.keySet();
// }
//
// public void pointRepoInit() {
// selectedPoints.clear();
// }
//
// public void enableGameComposite(final boolean b) {
// Display.getDefault().syncExec(new Runnable() {
// @Override
// public void run() {
// setListener(b);
// }
// });
// }
//
// void setListener(boolean b) {
// if (b)
// canvas.addMouseListener(this);
// else
// canvas.removeMouseListener(this);
// }
//
// public void showResult(final boolean amI) {
// Display.getDefault().syncExec(new Runnable() {
// @Override
// public void run() {
// gc.setFont(font);
// gc.setForeground(WHITE);
// gc.setBackground(BLUE);
// StringBuffer text = new StringBuffer();
// if (amI)
// text.append("Done!!");
// else
// text.append("Well Done!");
//
// Point textSize = gc.textExtent(text.toString());
// gc.drawText(text.toString(),
// (canvas.getSize().x - textSize.x) / 2,
// (canvas.getSize().y - textSize.y) / 2);
// }
// });
// }
//
// public void updateTurn(final int turnCount, final String next) {
// Display.getDefault().syncExec(new Runnable() {
//
// @Override
// public void run() {
// Display.getDefault().timerExec(1000, new Runnable() {
//
// @Override
// public void run() {
// canvas.redraw();
// }
// });
// // prepareCanvas();
// gc.setFont(font);
// gc.setForeground(BLUE);
// StringBuffer text = new StringBuffer("Round: " + turnCount
// + "\n");
// if (next.equals(clientViewHandler.getUserId()))
// text.append("\nMy Turn");
// else
// text.append("\n" + next);
// final Point textSize = gc.textExtent(text.toString());
// gc.drawText(text.toString(),
// (canvas.getSize().x - textSize.x) / 2,
// (canvas.getSize().y - textSize.y) / 2);
// }
//
// });
// }
//
// protected void prepareCanvas() {
// gc.setBackground(WHITE);
// gc.fillRectangle(canvas.getClientArea());
// }
