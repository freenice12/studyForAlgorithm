package day1FindShortestRoute.ui;

import java.awt.Point;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import day1FindShortestRoute.data.History;
import day1FindShortestRoute.interfaces.Character;

public class ButtonsComposite extends Composite {

	private int turn;

	@SuppressWarnings("synthetic-access")
	public ButtonsComposite(Composite parent,
			final TomJerryViewer tomJerryViewer) {
		super(parent, SWT.BORDER);
		setSize(810, 150);

		org.eclipse.swt.layout.GridLayout gl = new org.eclipse.swt.layout.GridLayout(
				4, true);
		this.setLayout(gl);

		final Button preButton = new Button(this, SWT.PUSH);
		preButton.setText("<");
		final Button nextButton = new Button(this, SWT.PUSH);
		nextButton.setText(">");
		final Button playButton = new Button(this, SWT.PUSH);
		playButton.setText("Play");
		final Button routeButton = new Button(this, SWT.PUSH);
		routeButton.setText("Show route");

		final Map<Integer, History> history = tomJerryViewer.getHistory();

		preButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDown(MouseEvent e) {
				super.mouseDown(e);
				turn--;
				if (-1 < turn && history.size() > turn) {
					nextButton.setVisible(true);
					showMapStatus(turn, tomJerryViewer, history, false);
				} else if (turn > history.size()) {
					turn++;
				}
			}

		});
		nextButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDown(MouseEvent e) {
				super.mouseDown(e);
				turn++;
				if (-1 < turn && turn < history.size()) {
					preButton.setVisible(true);
					showMapStatus(turn, tomJerryViewer, history, false);
				} else if (turn > history.size()) {
					turn--;
				}
			}

		});
		playButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDown(MouseEvent e) {
				super.mouseDown(e);
				playButton.setVisible(false);
				Display.getCurrent().asyncExec(new Runnable() {

					@Override
					public void run() {
						for (int index = turn; index < history.size(); index++) {
							showMapStatus(index, tomJerryViewer, history, false);
						}
						playButton.setVisible(true);
						turn = history.size() - 1;
					}
				});

			}

		});
		routeButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDown(MouseEvent e) {
				super.mouseDown(e);
				showMapStatus(turn, tomJerryViewer, history, true);
			}

		});
	}


	@SuppressWarnings("boxing")
	private void showMapStatus(int index, TomJerryViewer tomJerryViewer,
			Map<Integer, History> history, boolean showRoute) {
		History iThHistory = history.get(index);
		Map<Point, Character> mapStatus = iThHistory.getHistory();
		List<Point> shortestRoute = iThHistory.getShortestRoute();
		tomJerryViewer.updateBoardStatus(mapStatus, shortestRoute, showRoute);
	}

}
