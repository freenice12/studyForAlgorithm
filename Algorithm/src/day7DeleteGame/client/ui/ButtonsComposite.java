package day7DeleteGame.client.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class ButtonsComposite extends Composite implements PaintListener{
	
	protected NewClientView view;
	protected Button passButton;
	protected Button autoButton;
	protected Button sendButton;
	protected Button readyButton;
	
	public ButtonsComposite(NewClientView clientView) {
		super(clientView.mainComposite, SWT.BORDER);
		this.view = clientView;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 4;
		gridLayout.makeColumnsEqualWidth = true;
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gridData.heightHint = (view.display.getClientArea().height / 3) / 2;
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		setLayout(gridLayout);
		setLayoutData(gridData);
		createButtonContent();
	}

	private void createButtonContent() {
		
//		canvas = new Canvas(this, SWT.NO_BACKGROUND);
//		gc = new GC(canvas);
//		GridData gridData = new GridData();
//		gridData.horizontalSpan = 4;
//		gridData.horizontalAlignment = SWT.FILL;
//		gridData.verticalAlignment = SWT.FILL;
//		gridData.grabExcessHorizontalSpace = true;
//		gridData.grabExcessVerticalSpace = true;
//		canvas.setLayoutData(gridData);
//
//		canvas.addPaintListener(this);
//		
		GridData gd = getButtonGridData();
		createReadyButton(gd);
		createSendButton(gd);
		createAutoButton(gd);
		createPassButton(gd);
		
	}
	
	private GridData getButtonGridData() {
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, false, false);
		gridData.widthHint = (view.WIDTH * 2 / 3 / 4) - 20;
		gridData.heightHint = 25;
		gridData.verticalAlignment = SWT.CENTER;
		return gridData;
	}
	
	private void createPassButton(GridData gd) {
		passButton = new Button(this, SWT.PUSH);
		passButton.setText("Pass");
		passButton.setLayoutData(gd);
		passButton.setEnabled(false);
		passButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDown(MouseEvent mouseevent) {
				passButton.setEnabled(false);
				view.sendPass();
				super.mouseDown(mouseevent);
			}
			
		});
	}

	private void createAutoButton(GridData gd) {
		autoButton = new Button(this, SWT.PUSH);
		autoButton.setText("Auto");
		autoButton.setLayoutData(gd);
		autoButton.setEnabled(false);
		autoButton.addMouseListener(new MouseAdapter() {


			@Override
			public void mouseDown(MouseEvent mouseevent) {
				if (canPass)
					sendButton.setEnabled(true);
				view.showBest();
				super.mouseDown(mouseevent);
			}
			
		});
	}

	protected boolean canPass = true;
	private void createSendButton(GridData gd) {
		sendButton = new Button(this, SWT.PUSH);
		sendButton.setText("Send");
		sendButton.setLayoutData(gd);
		sendButton.setEnabled(false);
		sendButton.addMouseListener(new MouseAdapter() {


			@Override
			public void mouseDown(MouseEvent mouseevent) {
				canPass = false;
				view.sendPoints();
				super.mouseDown(mouseevent);
			}
			
		});
	}

	private void createReadyButton(GridData gd) {
		readyButton = new Button(this, SWT.PUSH);
		readyButton.setText("Ready");
		readyButton.setLayoutData(gd);
		readyButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDown(MouseEvent mouseevent) {
				readyButton.setEnabled(false);
				view.sendReady();
				super.mouseDown(mouseevent);
			}
			
		});
	}

	@Override
	public void paintControl(PaintEvent paintevent) {
		
	}

	public void enableAutoButton(boolean b) {
		autoButton.setEnabled(b);
	}

	public void enablePassButton(boolean b) {
		if (canPass)
			passButton.setEnabled(b);
		else
			passButton.setEnabled(false);
	}

	public void enableSendButton(boolean b) {
		sendButton.setEnabled(b);
	}

	public void enableReadyButton(boolean b) {
		readyButton.setEnabled(b);
	}

}