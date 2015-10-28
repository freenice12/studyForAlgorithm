package day7DeleteGame.client.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class StatusComposite extends Composite implements PaintListener {
	static final Color BACKGROUND = Display.getDefault().getSystemColor(
			SWT.COLOR_WIDGET_BACKGROUND);
	static final Color RED = Display.getDefault().getSystemColor(SWT.COLOR_RED);
	private NewClientView view;
	private Canvas canvas;
	private GC gc;
	private int width;
	private int height;
	private String userID;

	StatusComposite(NewClientView clientView) {
		super(clientView.mainComposite, SWT.BORDER);
		this.view = clientView;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		gridLayout.makeColumnsEqualWidth = true;
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gridData.heightHint = (view.display.getClientArea().height / 3) / 2;
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		setLayout(gridLayout);
		setLayoutData(gridData);
		createStateContent();
	}

	public void updateStatus(final String userId, final String next, final int turnCount) {
		userID = userId;
		canvas.update();
//		Display.getDefault().syncExec(new Runnable() {
//			@Override
//			public void run() {
//				if (userId.equals(next))
//					statusLabel.setText("My Id: " + userId + "\tTurn: " + turnCount+ "\t\t\t My turn: O");
//				else 
//					statusLabel.setText("My Id: " + userId + "\tTurn: " + turnCount+ "\t\t\t My turn: X");
//			}
//		});
	}
	
	@Override
	public void paintControl(PaintEvent paintevent) {
		gc = new GC(canvas);
		prepareCanvas();
		paintevent.gc.setForeground(RED);
//        Rectangle rect = canvas.getClientArea();
//        gc.drawRectangle(rect.x, rect.y , rect.width - 2, rect.height - 2);
        String string = "User id \t Client \t UUID";
        Point textSize = paintevent.gc.textExtent(string);
//        paintevent.gc.drawRectangle(canvas.getClientArea().x, (canvas.getClientArea().height - textSize.y)/2 + 5 , textSize.x + 5, textSize.y + 5);
        paintevent.gc.drawText(string, 2, 2);
//        e.gc.drawText(text, (canvas.getSize().x - textSize.x)/2, (canvas.getSize().y - textSize.y)/2);
	}
	
	protected void prepareCanvas() {
		gc.setBackground(BACKGROUND);
		gc.fillRectangle(canvas.getClientArea());
		width = canvas.getClientArea().width;
		height = canvas.getClientArea().height;
		gc.dispose();
	}
	
	private void createStateContent() {
		canvas = new Canvas(this, SWT.NO_BACKGROUND);
		
		GridData gridData = new GridData();
		gridData.horizontalSpan = 4;
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		canvas.setLayoutData(gridData);

		canvas.addPaintListener(this);
		
	}

}
