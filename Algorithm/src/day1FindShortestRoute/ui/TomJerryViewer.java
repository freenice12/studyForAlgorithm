package day1FindShortestRoute.ui;

import java.awt.Point;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import day1FindShortestRoute.Player;
import day1FindShortestRoute.data.History;
import day1FindShortestRoute.interfaces.Character;

public class TomJerryViewer {

	private Display display;
	private Shell shell;
	private Composite mainComposite;
	private GameBoardCompsite gameBoardCompsite;
	private ButtonsComposite buttonsComposite;
	
	private Player player;
	

	public TomJerryViewer(Player player) {
		this.player = player;
		display = new Display();
	    shell = new Shell(display);
	    shell.setText("Tom & Jerry");
	    shell.setSize(850, 1000);
	    shell.setLayout(new GridLayout(2, false));
	    
	    mainComposite = new Composite(shell, SWT.BORDER);
	    GridLayout gridLayout = new GridLayout();
	    gridLayout.numColumns = 1;
	    gridLayout.makeColumnsEqualWidth = false;
	    mainComposite.setLayout(gridLayout);
	    mainComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	    gameBoardCompsite = new GameBoardCompsite(mainComposite);
	    buttonsComposite = new ButtonsComposite(mainComposite, this);
	    

	}

	public void init() {
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	public void updateBoardStatus(Map<Point, Character> mapStatus, List<Point> shortestRoute, boolean showRoute) {
		gameBoardCompsite.updateHistoryComposite(mapStatus, shortestRoute, showRoute);
	}

	public Map<Integer, History> getHistory() {
		
		return player.getHistory();
	}
}
