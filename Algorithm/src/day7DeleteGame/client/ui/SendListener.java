package day7DeleteGame.client.ui;

import java.util.Set;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;

public class SendListener extends MouseAdapter {
	private ButtonComposite buttonComposite;

	public SendListener(ButtonComposite buttonComposite) {
		this.buttonComposite = buttonComposite;
	}

	@Override
	public void mouseDown(MouseEvent e) {
		System.out.println(getClass().getName()+" clicked");
		Set<Point> selectedPoints = buttonComposite.getSelectedPoints();
		buttonComposite.sendPoints(selectedPoints, false);
		buttonComposite.pointRepoInit();
	}
}
