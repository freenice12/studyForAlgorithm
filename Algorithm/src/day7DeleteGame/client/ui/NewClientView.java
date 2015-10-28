package day7DeleteGame.client.ui;

import java.util.UUID;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import day7DeleteGame.model.Board;

public class NewClientView {

	public final UUID uuid = UUID.randomUUID();
	protected int WIDTH = 700;
	protected int HEIGHT = 730;
	protected Display display;
	protected Shell shell;
	protected Composite mainComposite;
	protected LeftComposite left;
	protected BoardComposite boardComposite;
	protected StatusComposite statusComposite;
	protected ButtonsComposite buttonsComposite;

	public void init() {
		display = new Display();
		shell = new Shell(display);
		shell.setText("Pearls Before Swine");
		shell.setLayout(new GridLayout(2, false));

		mainComposite = new Composite(shell, SWT.BORDER);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.makeColumnsEqualWidth = false;
		mainComposite.setLayout(gridLayout);
		mainComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createLeftComposite();
		createRightComposite();

		start();
	}

	private void createLeftComposite() {
		left = new LeftComposite(this);
	}

	private void createRightComposite() {
		boardComposite = new BoardComposite(this);
		statusComposite = new StatusComposite(this);
		buttonsComposite = new ButtonsComposite(this);
	}

	private void start() {
		boardComposite.updateView(Board.getInstance());
		statusComposite.updateStatus("", "", 1);
		shell.setSize(WIDTH, HEIGHT);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	public static void main(String[] args) {
		NewClientView view = new NewClientView();
		view.init();
	}

}
