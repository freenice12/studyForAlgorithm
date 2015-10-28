package day7DeleteGame.client.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import common.model.UserInfo;

public class ButtonComposite extends Composite {

	private Composite composite;
	private ClientViewHandler clientViewHandler;
	protected Button readyButton;
	protected Button sendButton;
	protected Button autoButton;
	protected Button passButton;
	protected List<Label> clientsLabel = new ArrayList<>();
	protected Label statusLabel;

	public ButtonComposite(Composite parent, ClientViewHandler clientViewHandler) {
		super(parent, SWT.BORDER);
		this.clientViewHandler = clientViewHandler;
		composite = new Composite(this, SWT.NONE);
		init();
		compositeSetup();
	}

	private void setStatusComposite() {
//		statusComposite = new Composite(composite, SWT.NONE);
//		GridLayout gl = new GridLayout(1, false);
//		gl.marginHeight = 5;
//		
		GridData gridData = new GridData(SWT.MEDIUM, SWT.FILL, true, true);
		gridData.widthHint = 430;
		gridData.heightHint = 30;
		gridData.horizontalSpan = 4;
		gridData.grabExcessHorizontalSpace = true;
//		
//		
//		statusComposite.setLayoutData(gridData);
//		statusComposite.setLayout(gl);

		statusLabel = new Label(composite, SWT.NONE);
		statusLabel.setLayoutData(gridData);
//		statusLabel.setText("MyId:\tTurn: ");

	}

	private void compositeSetup() {
		GridLayout gl = new GridLayout(4, true);
		gl.marginHeight = 5;
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		composite.setLayout(gl);
		composite.setSize(453, 100);
	}

	private void init() {
		setStatusComposite();
		createClientsLabel();
		GridData gd = getButtonGridData();
		createReadyButton(gd);
		createSendButton(gd);
		createAutoButton(gd);
		createPassButton(gd);
	}

	private void createPassButton(GridData gd) {
		passButton = new Button(composite, SWT.PUSH);
		passButton.setText("Pass");
		passButton.setLayoutData(gd);
		passButton.setEnabled(false);
		passButton.addMouseListener(new PassListener(this));
	}

	private void createAutoButton(GridData gd) {
		autoButton = new Button(composite, SWT.PUSH);
		autoButton.setText("Auto");
		autoButton.setLayoutData(gd);
		autoButton.setEnabled(false);
		autoButton.addMouseListener(new AutoListener(this));
	}

	private void createSendButton(GridData gd) {
		sendButton = new Button(composite, SWT.PUSH);
		sendButton.setText("Send");
		sendButton.setLayoutData(gd);
		sendButton.setEnabled(false);
		sendButton.addMouseListener(new SendListener(this));
	}

	private void createReadyButton(GridData gd) {
		readyButton = new Button(composite, SWT.PUSH);
		readyButton.setText("Ready");
		readyButton.setLayoutData(gd);
		readyButton.addMouseListener(new ReadyListener(this));
	}
	
	private void createClientsLabel() {
		for (int i = 0; i < 4; i++) {
			Label clientLabel = new Label(composite, SWT.NONE);
			GridData grid = getLabelGridData();
			clientLabel.setLayoutData(grid);
//			clientLabel.pack();
			clientsLabel.add(clientLabel);
		}
	}

	private GridData getLabelGridData() {
		GridData gridData = new GridData(SWT.MEDIUM, SWT.FILL, false, false);
		gridData.widthHint = 430 / 4;
		gridData.heightHint = 30;
		gridData.verticalAlignment = SWT.CENTER;
		return gridData;
	}

	private GridData getButtonGridData() {
		GridData gridData = new GridData(SWT.MEDIUM, SWT.FILL, false, false);
		gridData.widthHint = 430 / 4;
		gridData.heightHint = 25;
		return gridData;
	}

	public void disableReadyButton() {
		readyButton.setEnabled(false);
	}

	public void setClientsLabel(final String id, Collection<UserInfo> clients) {
		if (id.isEmpty())
			return;
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
	}

	public void setEnableSendButton(final boolean b) {
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				sendButton.setEnabled(b);
			}
		});
	}
	
	public void setEnableReadyButton(final boolean b) {
		Display.getDefault().syncExec(new Runnable() {
			
			@Override
			public void run() {
				readyButton.setEnabled(b);
			}
		});
	}

	public Set<Point> getSelectedPoints() {
		return clientViewHandler.getSelectedPoints();
	}

	public void sendPoints(Set<Point> selectedPoints, boolean isAuto) {
		clientViewHandler.sendPoints(selectedPoints, isAuto);
	}

	public void pointRepoInit() {
		clientViewHandler.pointRepoInit();
	}

	public void sendReady() {
		clientViewHandler.sendReady();
	}

	public void setEnableAutoButton(final boolean b) {
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				autoButton.setEnabled(b);
			}
		});
	}

	public void setEnablePassButton(final boolean b) {
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				passButton.setEnabled(b);
			}
		});
	}

	public void sendPass() {
		clientViewHandler.sendPass();
	}

	public void updateStatus(final String userId, final String next, final int turnCount) {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				if (userId.equals(next))
					statusLabel.setText("My Id: " + userId + "\tTurn: " + turnCount+ "\t\t\t My turn: O");
				else 
					statusLabel.setText("My Id: " + userId + "\tTurn: " + turnCount+ "\t\t\t My turn: X");
			}
		});
	}

}
