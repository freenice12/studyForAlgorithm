package day7DeleteGame.client.ui;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class PassListener extends MouseAdapter {
	
	private ButtonComposite buttonComposite;

	public PassListener(ButtonComposite buttonComposite) {
		this.buttonComposite = buttonComposite;
	}

	@Override
	public void mouseDown(MouseEvent e) {
		System.out.println(getClass().getName()+" clicked");
		buttonComposite.sendPass();
		buttonComposite.setEnablePassButton(false);
	}

}
