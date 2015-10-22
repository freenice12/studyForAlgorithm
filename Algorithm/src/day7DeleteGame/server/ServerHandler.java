package day7DeleteGame.server;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import common.message.InitRequestMessage;
import common.message.InitResponseMessage;
import common.message.PassRequestMessage;
import common.message.ReadyRequestMessage;
import common.message.ReadyResponseMessage;
import common.message.SubmitRequestMessage;
import common.message.SubmitResponseMessage;
import common.message.topic.EndTopicMessage;
import common.message.topic.StartTopicMessage;
import common.message.topic.TurnTopicMessage;

import day7DeleteGame.model.Board;
import day7DeleteGame.util.ActivemqConnector;

public class ServerHandler implements MessageListener {

	private int count;
	private int readyCount;
	private ActivemqConnector conn;
	private int turn;
	private Map<UUID, String> clientMap = new LinkedHashMap<UUID, String>();
	private GameManager gameManager = new GameManager();

	public ServerHandler(ActivemqConnector connector) {
		this.conn = connector;
	}

	@Override
	public void onMessage(final Message message) {
		try {
			Object messageObj = ((ObjectMessage) message).getObject();
			Destination jmsReplyTo = message.getJMSReplyTo();
			if (messageObj instanceof InitRequestMessage) {
				System.out.println("get init req");
				ready(messageObj, jmsReplyTo);
			} else if (messageObj instanceof ReadyRequestMessage) {
				System.out.println("get ready req");
				start(jmsReplyTo);
			} else if (messageObj instanceof SubmitRequestMessage) {
				System.out.println("get submit req");
				continueGame(messageObj, jmsReplyTo);
			} else if (messageObj instanceof PassRequestMessage) {
				System.out.println("get pass req");
				addPassedClient(messageObj, jmsReplyTo);
			}

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	private boolean addPassedClient(Object messageObj, Destination jmsReplyTo) {
		UUID usedClient = ((SubmitRequestMessage) messageObj).getUuid();
		if (gameManager.putPassClient(usedClient)) {
			conn.sendReplyTo(jmsReplyTo, new SubmitResponseMessage(true, ""));
			return true;
		}
		conn.sendReplyTo(jmsReplyTo, new SubmitResponseMessage(false,
				"Can not Pass!"));
		return false;
	}

	private void continueGame(Object messageObj, Destination jmsReplyTo) {
		Board board = new Board(((SubmitRequestMessage) messageObj).getList());
		boolean isFinish = board.isFinish();
//		System.out.println("isFinish? " + isFinish);
		if (((SubmitRequestMessage) messageObj).isPass()) {
			if (!addPassedClient(messageObj, jmsReplyTo))
				return;
		}
		sendTurnTopic(board.getBoard(), isFinish);
		gameManager.saveHistory(turn, ((SubmitRequestMessage) messageObj));
		if (isFinish) {
			init();
			conn.sendTopic(new EndTopicMessage(
					((SubmitRequestMessage) messageObj).getUuid()));
		}
	}

	private void init() {
		Board.initInstance().getBoard();
		count = 0;
		turn = 0;
		readyCount = 0;
		clientMap = new LinkedHashMap<>();
		gameManager = new GameManager();
	}

	private void start(Destination jmsReplyTo) {
		conn.sendReplyTo(jmsReplyTo, new ReadyResponseMessage(true, ""));
		handleReadyMessage();
	}

	private void ready(Object messageObj, Destination jmsReplyTo) {
		clientMap.put(((InitRequestMessage) messageObj).getUuid(), "client"
				+ (count++));
		conn.sendReplyTo(jmsReplyTo, new InitResponseMessage(true, ""));
	}

	private void handleReadyMessage() {
		readyCount++;
		if (readyCount > 1 && count == readyCount) {
			// System.out.println(Board.getInstance());
			gameManager.saveHistory(turn, new SubmitRequestMessage(null, Board
					.getInstance().getBoard()));
			conn.sendTopic(new StartTopicMessage(clientMap));
			sendTurnTopic(Board.getInstance().getBoard(), false);
		}
	}

	private void sendTurnTopic(List<List<Boolean>> board, boolean isFinish) {
		UUID nextClient = getNextClient(isFinish);
		turn++;
		conn.sendTopic(new TurnTopicMessage(nextClient, turn, board));
	}

	private UUID getNextClient(boolean isFinish) {
		if (isFinish)
			return UUID.randomUUID();
		int next = turn % clientMap.size();
		for (Entry<UUID, String> entry : clientMap.entrySet()) {
			if (entry.getValue().equals("client" + next)) {
				return entry.getKey();
			}
		}
		return UUID.randomUUID();
	}

}
