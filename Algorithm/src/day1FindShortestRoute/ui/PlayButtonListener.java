package day1FindShortestRoute.ui;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class PlayButtonListener extends MouseAdapter {
	private ButtonsComposite buttonsComposite;

	public PlayButtonListener(ButtonsComposite buttonsComposite) {
		this.buttonsComposite = buttonsComposite;
	}

	@Override
	public void mouseDown(MouseEvent mouseevent) {
		super.mouseDown(mouseevent);
		
	}

}
