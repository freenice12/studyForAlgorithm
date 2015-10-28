package day7DeleteGame.client.ui;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class LeftComposite extends Composite {
	private static final String IP = "IP";
	private static final String PORT = "Port";
	private static final String NAME = "Name";
	private static final String TOPIC = "Topic";
	private static final String QUEUE = "Queue";
	static final Color RED = Display.getDefault().getSystemColor(SWT.COLOR_RED);
	static final Color GREEN = Display.getDefault().getSystemColor(SWT.COLOR_GREEN);
	private NewClientView view;
	protected HashMap<String, Text> textMap = new HashMap<String, Text>();
	protected Label connectState;
	
	LeftComposite(NewClientView clientView) {
		super(clientView.mainComposite, SWT.BORDER);
		this.view = clientView;
		GridLayout gridLayout = new GridLayout(2, false);
		// contents space
		gridLayout.verticalSpacing = 10;
		setLayout(gridLayout);
		GridData gridData = new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 3);
		gridData.widthHint = view.WIDTH / 3;
		setLayoutData(gridData);
		createInfoLabel();
		createContent();
		createStateLabel();
		createConnectButton();
	}

	private void createInfoLabel() {
		Label label = new Label(this, SWT.NONE);
		Font boldFont = new Font( label.getDisplay(), new FontData( "Arial", 14, SWT.BOLD ) );
		label.setFont( boldFont );
		label.setText("Information");
		GridData gridData = new GridData(SWT.CENTER, SWT.TOP, false, false, 2, 2);
		gridData.grabExcessHorizontalSpace = true;
		label.setLayoutData(gridData);
	}
	
	private void createContent() {
		createLabel(IP);
		createLabel(PORT);
		createLabel(NAME);
		createLabel(TOPIC);
		createLabel(QUEUE);
	}
	
	private void createLabel(String text) {
		Label label = new Label(this, SWT.NONE);
		Font boldFont = new Font( label.getDisplay(), new FontData( "Arial", 12, SWT.ITALIC ) );
		label.setFont( boldFont );
		label.setText(text+": ");
		
		GridData gridData = new GridData(SWT.LEFT, SWT.MEDIUM, false, false, 1, 2);
		gridData.heightHint = 25;
		gridData.verticalAlignment = SWT.MEDIUM;
		label.setLayoutData(gridData);
		createText(text);
	}

	private void createText(String key) {
		Text text = new Text(this, SWT.NONE);
		GridData gridData = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 2);
		gridData.heightHint = 25;
		gridData.verticalAlignment = SWT.CENTER;
		gridData.grabExcessHorizontalSpace = true;
		text.setLayoutData(gridData);
//		text.setText("text");
		setPlacehold(key, text);
		textMap.put(key, text);
		
	}

	private void setPlacehold(String key, Text text) {
		StringBuffer placehold = new StringBuffer();
		switch (key) {
		case IP:
			placehold.append("localhost");
			break;
		case PORT:
			placehold.append("61616");
			break;
		case NAME:
			placehold.append("PealsBeforeSwine");
			break;
		case TOPIC:
			placehold.append("Topic");
			break;
		case QUEUE:
			placehold.append("ServerQueue");
			break;
			

		default:
			break;
		}
		text.setText(placehold.toString());
	}

	protected boolean isConnected = false;
	protected Button connectButton;
	private void createStateLabel() {
		connectState = new Label(this, SWT.NONE);
		connectState.setBackground(RED);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, false, false, 2, 2);
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		connectState.setLayoutData(gridData);
	}
	
	private void createConnectButton() {
		connectButton = new Button(this, SWT.PUSH);
		connectButton.setText("Connect");
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, false, false, 2, 2);
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = SWT.BOTTOM;
		connectButton.setLayoutData(gridData);
		connectButton.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseDown(MouseEvent mouseevent) {
				if (isConnected) {
					connectState.setBackground(RED);
					connectButton.setText("Connect");
					isConnected = false;
				} else {
					System.out.println("Address: tcp://"+textMap.get(IP).getText()+":"+textMap.get(PORT).getText());
					System.out.println("Name: "+textMap.get(NAME).getText());
					System.out.println("Topic: "+textMap.get(TOPIC).getText());
					System.out.println("Queue: "+textMap.get(QUEUE).getText());
					connectState.setBackground(GREEN);
					connectButton.setText("Disconnect");
					isConnected = true;
				}
				super.mouseDown(mouseevent);
			}
			
		});
	}
	
}
