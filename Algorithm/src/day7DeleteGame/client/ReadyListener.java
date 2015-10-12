package day7DeleteGame.client;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class ReadyListener extends MouseAdapter {
	
	private ButtonComposite buttonComposite;

	public ReadyListener(ButtonComposite buttonComposite) {
		this.buttonComposite = buttonComposite;
	}

	@Override
	public void mouseDown(MouseEvent e) {
		System.out.println(getClass().getName()+" clicked");
		buttonComposite.sendReady();
		buttonComposite.disableReadyButton();
	}
	
}
