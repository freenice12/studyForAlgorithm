package day7DeleteGame.server;

import java.util.ArrayList;
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
	private List<UUID> clients = new ArrayList<>();
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
				ready(messageObj, jmsReplyTo);
			} else if (messageObj instanceof ReadyRequestMessage) {
				start(jmsReplyTo);
			} else if (messageObj instanceof SubmitRequestMessage) {
				continueGame(messageObj, jmsReplyTo);
			}

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	private void continueGame(Object messageObj, Destination jmsReplyTo) {
		UUID client = ((SubmitRequestMessage) messageObj).getUuid(); 
		Board board = new Board(((SubmitRequestMessage) messageObj).getList());
		boolean isFinish = board.isFinish();
		if (!gameManager.validMap(board.getBoard())) {
			boolean result = gameManager.putPassClient(client);
			if (!result) {
				conn.sendReplyTo(jmsReplyTo, new SubmitResponseMessage(result, "Can not Pass!"));
				sendTurnTopic(client, board);
				return;
			}
		}
		sendTurnTopic(board.getBoard(), isFinish);
		gameManager.saveHistory(turn, ((SubmitRequestMessage) messageObj));
		if (isFinish) {
			sendEndTopic(((SubmitRequestMessage) messageObj).getUuid());
		}
	}
	
	private void sendEndTopic(UUID uuid) {
		conn.sendTopic(new EndTopicMessage(uuid));
	}

	private void start(Destination jmsReplyTo) {
		sendReadyResponse(jmsReplyTo);
		handleReadyMessage();
	}

	private void ready(Object messageObj, Destination jmsReplyTo) {
		addClient(((InitRequestMessage) messageObj).getUuid());
		sendInitResponse(jmsReplyTo);
	}

	private void sendInitResponse(Destination destination) {
		conn.sendReplyTo(destination, new InitResponseMessage(true, ""));
	}

	private void handleReadyMessage() {
		readyCount++;
		if (count == readyCount) {
			System.out.println(Board.getInstance());
			gameManager.saveHistory(turn, new SubmitRequestMessage(null, Board.getInstance().getBoard()));
			sendStartTopic();
			sendTurnTopic(Board.getInstance().getBoard(), false);
		}
	}

	private void sendReadyResponse(Destination destination) {
		conn.sendReplyTo(destination, new ReadyResponseMessage(true, ""));
	}

	private void sendStartTopic() {
		conn.sendTopic(new StartTopicMessage(clientMap));
	}

	private void sendTurnTopic(List<List<Boolean>> board, boolean isFinish) {
		UUID nextClient = getNextClient(isFinish);
		System.out.println("nextId: " + nextClient);
		conn.sendTopic(new TurnTopicMessage(nextClient, board));
	}
	
	private void sendTurnTopic(UUID client, Board board) {
		conn.sendTopic(new TurnTopicMessage(client, board.getBoard()));
	}

	private UUID getNextClient(boolean isFinish) {
		if (isFinish)
			return UUID.randomUUID();
		int next = turn % clientMap.size();
		turn++;
		for (Entry<UUID, String> entry : clientMap.entrySet()) {
			if (entry.getValue().equals("client" + next)) {
				System.out.println(entry.getKey() + " / " + entry.getValue());
				return entry.getKey();
			}
		}
		return UUID.randomUUID();
	}

	private void addClient(UUID client) {
		clients.add(client);
		clientMap.put(client, "client" + (count++));
	}

}
