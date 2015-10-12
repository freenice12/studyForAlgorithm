package day7DeleteGame.client;

import java.util.HashSet;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;

public class PassListener extends MouseAdapter {
	
	private ButtonComposite buttonComposite;

	public PassListener(ButtonComposite buttonComposite) {
		this.buttonComposite = buttonComposite;
	}

	@Override
	public void mouseDown(MouseEvent e) {
		buttonComposite.sendPoints(new HashSet<Point>(), false);
		buttonComposite.setEnablePassButton(false);
		System.out.println(getClass().getName()+" clicked");
	}

}
