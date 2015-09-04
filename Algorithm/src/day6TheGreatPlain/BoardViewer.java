package day6TheGreatPlain;

import java.awt.Point;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class BoardViewer {
	
	private Display display;
	private Shell shell;
	private Composite mainComposite;
	private BoardComposite boardComposite;
	public BoardViewer() {}
	public BoardViewer(Map<Point, Integer> board) {
		display = new Display();
	    shell = new Shell(display);
	    shell.setText("Tom & Jerry");
	    shell.setSize(500, 500);
	    shell.setLayout(new GridLayout(2, false));
	    
	    mainComposite = new Composite(shell, SWT.BORDER);
	    GridLayout gridLayout = new GridLayout();
	    gridLayout.numColumns = 1;
	    gridLayout.makeColumnsEqualWidth = false;
	    mainComposite.setLayout(gridLayout);
	    mainComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	    boardComposite = new BoardComposite(mainComposite);
	    boardComposite.buildComposite(board);
	}
	public void init() {
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
	
}
