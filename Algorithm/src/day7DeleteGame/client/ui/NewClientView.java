package day7DeleteGame.client.ui;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.jms.MessageListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import common.model.UserInfo;
import day7DeleteGame.client.BoardHandler;
import day7DeleteGame.client.GameClientMessageHandler;
import day7DeleteGame.client.GameClientTopicHandler;
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
	protected GameClientMessageHandler messageHandler;
	protected GameClientTopicHandler topicHandler;
	protected BoardHandler helper;
	protected boolean canPass;

	public NewClientView(GameClientMessageHandler gameClientMessageHandler) {
		this.messageHandler = gameClientMessageHandler;
		this.messageHandler.setViewer(this);
		this.helper = new BoardHandler();
	}

	public void setTopicHandler(MessageListener gameClientTopicHandler) {
		this.topicHandler = (GameClientTopicHandler) gameClientTopicHandler;
	}

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
		mainComposite
				.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

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
		// boardComposite.updateView(Board.getInstance());
		// List<UserInfo> clients = new ArrayList<UserInfo>();
		// clients.add(new UserInfo(UUID.randomUUID(), "user1"));
		// clients.add(new UserInfo(UUID.randomUUID(), "tester2"));
		// clients.add(new UserInfo(UUID.randomUUID(), "na3"));
		// statusComposite.setClientsLabel("", clients);
		shell.setSize(WIDTH, HEIGHT);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	public void updateStatus(final String string,
			final Collection<UserInfo> userInfos) {
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				statusComposite.updateStatus(string, userInfos);
			}
		});
	}

	public void updateInfo(final int turnCount) {
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				statusComposite.updateTurn(turnCount);
			}
		});
	}

	// public void updateView(final Board board) {
	// helper.convertToBoard(board.getBoard());
	// Display.getDefault().asyncExec(new Runnable() {
	//
	// @Override
	// public void run() {
	// boardComposite.updateView(board);
	// }
	// });
	// }

	public void enableAutoButton(final boolean b) {
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				buttonsComposite.enableAutoButton(b);
			}
		});
	}

	public void enablePassButton(final boolean b) {
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				buttonsComposite.enablePassButton(b);
			}
		});
	}

	public void enableGameComposite(final boolean b) {
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				boardComposite.enableGameComposite(b);
			}
		});
	}

	public void enableSendButton(final boolean b) {
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				buttonsComposite.enableSendButton(b);
			}
		});
	}

	public void showResult(final boolean b) {
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				boardComposite.showResult(b);
			}
		});
	}

	public void enableReadyButton(final boolean b) {
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				buttonsComposite.enableReadyButton(b);
			}
		});
	}

	public void sendReady() {
		// if (reGame)
		// clientHandler.sendInit();
		topicHandler.sendReady();
	}

	public void updateView(final List<List<Boolean>> mapList) {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				boardComposite.updateView(helper.convertToBoard(mapList));
			}
		});
	}
	
		

//	public List<List<Boolean>> sendPoint(Set<Point> selectedPoints,
//			boolean isAuto, boolean canPass) {
//		return helper.getModifiedBoard(selectedPoints, isAuto, canPass);
//	}
//
//	public void sendSelectedPoint(boolean isAuto) {
//		topicHandler.sendPoints(boardComposite.getSelectedPoint(), isAuto);
//	}

	public void showBest() {
		if (helper.findAnswer(canPass))
			boardComposite.showBest(helper.deleteIndex, helper.deleteCount);
	}

	public void sendPoints() {
		helper.switchSelectedElement(boardComposite.getSelectedPoint());
		topicHandler.sendPoints(helper.getBoard());
	}

	public void sendPass() {
		topicHandler.sendPass(helper.getBoard());
	}

}
