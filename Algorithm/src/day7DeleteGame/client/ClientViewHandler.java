package day7DeleteGame.client;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import day7DeleteGame.model.Board;

public class ClientViewHandler {
	public final UUID uuid = UUID.randomUUID();
	private Display display;
	private Shell shell;
	private Composite mainComposite;
	private GameComposite gameComposite;
	private ButtonComposite buttonComposite;
	private GameClientTopicHandler clientHandler;
	
	public ClientViewHandler(GameClientTopicHandler clientHandler) {
		this.clientHandler = clientHandler;
		display = new Display();
	    shell = new Shell(display);
	    shell.setText("Plain Map");
	    shell.setLayout(new GridLayout(1, false));
	    
	    mainComposite = new Composite(shell, SWT.BORDER);
	    GridLayout gridLayout = new GridLayout();
	    gridLayout.numColumns = 1;
	    gridLayout.makeColumnsEqualWidth = false;
	    mainComposite.setLayout(gridLayout);
	    mainComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	    gameComposite = new GameComposite(mainComposite, this);
	    buttonComposite = new ButtonComposite(mainComposite, this);
	}

	public GameComposite getGameComposite() {
		return gameComposite;
	}

	public ButtonComposite getButtonComposite() {
		return buttonComposite;
	}

	public void init() {
		shell.setSize(500, 600);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
	
	public void sendReady() {
		clientHandler.sendReady();
	}
	
	public void updateLabel(String id, Collection<String> clients) {
		buttonComposite.setClientsLabel(id, clients);
	}

	public void enableSendButton(boolean b) {
		buttonComposite.setEnableSendButton(b);
	}

	public void updateView(Board board) {
		gameComposite.updateView(board);
	}

	public Set<Point> getSelectedPoints() {
		return gameComposite.getSelectedPoints();
	}

	public void sendPoints(Set<Point> selectedPoints, boolean isAuto) {
		clientHandler.sendPoints(selectedPoints, isAuto);
	}

	public void pointRepoInit() {
		gameComposite.pointRepoInit();
	}

	public void enableGameComposite(boolean b) {
		gameComposite.enableGameComposite(b);
	}

	public void showResult(UUID clientUUID, UUID otherClientUUID) {
		gameComposite.showResult(clientUUID.equals(otherClientUUID));
	}

	public void enableAutoButton(boolean b) {
		buttonComposite.setEnableAutoButton(b);
	}

	public void enablePassButton(boolean b) {
		buttonComposite.setEnablePassButton(b);
	}


}