package day7DeleteGame.client.ui;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.jms.MessageListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import common.model.UserInfo;

import day7DeleteGame.client.BoardHandler;
import day7DeleteGame.client.GameClientMessageHandler;
import day7DeleteGame.client.GameClientTopicHandler;

public class NewClientView {

	public final UUID uuid = UUID.randomUUID();
	protected int WIDTH = 700;
	protected int HEIGHT = 730;
	protected Display display;
	protected Shell shell;
	protected Composite mainComposite;
	protected LeftComposite leftComposite;
	protected BoardComposite boardComposite;
	protected StatusComposite statusComposite;
	protected ButtonsComposite buttonsComposite;
	protected GameClientMessageHandler messageHandler;
	protected GameClientTopicHandler topicHandler;
	protected BoardHandler helper;

	public NewClientView(GameClientMessageHandler gameClientMessageHandler) {
		this.messageHandler = gameClientMessageHandler;
		this.messageHandler.setViewer(this);
		this.helper = new BoardHandler();
	}

	public void setTopicHandler(MessageListener gameClientTopicHandler) {
		this.topicHandler = (GameClientTopicHandler) gameClientTopicHandler;
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				sendHeartBeat();
				display.timerExec(20000, this);
			}
		};

		// Launch the timer
		display.timerExec(1000, runnable);
		// display.asyncExec(new Runnable() {
		// @Override
		// public void run() {
		// Runnable runnable = new Runnable() {
		// @Override
		// public void run() {
		// sendHeartBeat();
		// display.timerExec(10000, this);
		// }
		// };
		// Display.getCurrent().timerExec(1000, runnable);
		// }
		// });
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
		leftComposite = new LeftComposite(this);
	}

	private void createRightComposite() {
		boardComposite = new BoardComposite(this);
		statusComposite = new StatusComposite(this);
		buttonsComposite = new ButtonsComposite(this);
	}

	private void start() {
		shell.setSize(WIDTH, HEIGHT);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	public void sendHeartBeat() {
		if (topicHandler.startHeartbeat()) {
			topicHandler.sendHeartbeat();
		}
		leftComposite.redraw();
		boardComposite.redraw();
		statusComposite.redraw();
		buttonsComposite.redraw();
		
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

	// public List<List<Boolean>> sendPoint(Set<Point> selectedPoints,
	// boolean isAuto, boolean canPass) {
	// return helper.getModifiedBoard(selectedPoints, isAuto, canPass);
	// }
	//
	// public void sendSelectedPoint(boolean isAuto) {
	// topicHandler.sendPoints(boardComposite.getSelectedPoint(), isAuto);
	// }

	public void showBest() {
		if (helper.findAnswer(buttonsComposite.canPass))
			boardComposite.showBest(helper.deleteIndex, helper.deleteCount);
	}

	public void sendPoints() {
		helper.switchSelectedElement(boardComposite.getSelectedPoint());
		topicHandler.sendPoints(helper.getBoard());
	}

	public void sendPass() {
		topicHandler.sendPass(helper.getBoard());
	}

	public void sendGiveup() {
		topicHandler.sendGiveup();
	}

	public void enableGiveupButton(boolean b) {
		buttonsComposite.enableGiveupButton(b);
	}

	public boolean getCanPass() {
		return buttonsComposite.canPass;
	}
	
	public void setCanPass(boolean b) {
		buttonsComposite.setCanPass(b);
	}

	public void initStatus() {
		statusComposite.initUserInfo();
	}
}
