package day1FindShortestRoute.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class ButtonsComposite extends Composite {
	
	private TomJerryViewer tomJerryViewer;

	public ButtonsComposite(Composite parent, TomJerryViewer tomJerryViewer) {
		super(parent, SWT.BORDER);
		this.tomJerryViewer = tomJerryViewer; 
		setSize(810, 150);
		
		org.eclipse.swt.layout.GridLayout gl = new org.eclipse.swt.layout.GridLayout(4, true);
		this.setLayout(gl);
		
		Button preButton = new Button(this, SWT.PUSH);
		preButton.setText("<");
		Button nextButton = new Button(this, SWT.PUSH);
		nextButton.setText(">");
		Button playButton = new Button(this, SWT.PUSH);
		playButton.setText("Play");
		Button routeButton = new Button(this, SWT.PUSH);
		routeButton.setText("Show route");
		playButton.addMouseListener(new PlayButtonListener(this));
	}
	
	public void updateBoard() {
		tomJerryViewer.updateBoardStatus();
	}

}
