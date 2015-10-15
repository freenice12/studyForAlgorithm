package day7DeleteGame.client.ui;

import java.util.Collections;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class AutoListener extends MouseAdapter {
	private ButtonComposite buttonComposite;

	public AutoListener(ButtonComposite buttonComposite) {
		this.buttonComposite = buttonComposite;
	}

	@Override
	public void mouseDown(MouseEvent e) {
		System.out.println(getClass().getName()+" clicked");
		buttonComposite.sendPoints(Collections.EMPTY_SET, true);
		buttonComposite.pointRepoInit();
	}
}
