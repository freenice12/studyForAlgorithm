package day7DeleteGame.client.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
import org.eclipse.swt.widgets.Label;

import common.model.UserInfo;

public class StatusComposite extends Composite implements PaintListener {
	static final Color BACKGROUND = Display.getDefault().getSystemColor(
			SWT.COLOR_WIDGET_BACKGROUND);
	static final Color RED = Display.getDefault().getSystemColor(SWT.COLOR_RED);
	static final Color BLUE = Display.getDefault().getSystemColor(SWT.COLOR_BLUE);
	static final Color BLACK = Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
	private NewClientView view;
	protected Canvas canvas;
	private GC gc;
	private String nextId;
	private List<UserInfo> userInfos = new ArrayList<>();
	private List<Label> clientsLabel = new ArrayList<>();
	private int turn;

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
	
	public void setClientsLabel(final String id, Collection<UserInfo> clients) {
		if (id.isEmpty()) {
			userInfos.addAll(clients);
			return;
		}
		int index = 0;
		for (final UserInfo client : clients) {
			final Label clientLabel = clientsLabel.get(index++);
			Display.getDefault().syncExec(new Runnable() {

				@Override
				public void run() {
					clientLabel.setAlignment(SWT.CENTER);
					clientLabel.setText(client.getName());
					if (id.equals(client)) {
						clientLabel.setForeground(getDisplay().getSystemColor(
								SWT.COLOR_RED));
					} else {
						clientLabel.setForeground(getDisplay().getSystemColor(
								SWT.COLOR_BLACK));
					}
				}
			});
		}
		canvas.update();
	}
	
	private int getMaxWidth() {
		int max = 0;
		for (UserInfo info : userInfos) {
			Point textSize = gc.textExtent(info.getName());
			if (max < textSize.x)
				max = textSize.x;
		}
		return max;
	}

	@Override
	public void paintControl(PaintEvent paintevent) {
		gc = new GC(canvas);
		prepareCanvas();
		
		
		int x = 0;
		int y = 0;
		int indent = 20;
		for (UserInfo info : userInfos) {
			if (info.getName().equals(nextId))
				paintevent.gc.setForeground(RED);
			else
				paintevent.gc.setForeground(BLACK);
				
			Point textSize = paintevent.gc.textExtent(info.getName());
//			if (x == 0)
//				x += indent;
//			else
			x += getMaxWidth()+indent;
			y = (canvas.getClientArea().height - textSize.y)/2;
			paintevent.gc.drawString(info.getName(), x, y);
			if (info.getName().equals(nextId))
				paintevent.gc.drawRectangle(x - 10, y - 2, textSize.x + indent, textSize.y + 5);
		}
		paintevent.gc.drawString("Turn: "+turn, 5, y);
		
	}
	
	protected void prepareCanvas() {
		gc.setBackground(BACKGROUND);
		gc.fillRectangle(canvas.getClientArea());
	}
	
	private void createStateContent() {
//		canvas = new Canvas(this, SWT.NO_BACKGROUND);
		canvas = new Canvas(this, SWT.BORDER);
		
		GridData gridData = new GridData();
		gridData.horizontalSpan = 4;
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		canvas.setLayoutData(gridData);

		canvas.addPaintListener(this);
		
	}
	
	public void updateStatus(final String id, Collection<UserInfo> clientInfos) {
		if (id.isEmpty()) {
			userInfos.addAll(clientInfos);
			return;
		}
		nextId = id;
		canvasRedraw();
	}

	private void canvasRedraw() {
		Display.getDefault().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				canvas.redraw();
			}
		});
	}

	public void updateTurn(int turnCount) {
		turn = turnCount;
		canvasRedraw();
	}

}
