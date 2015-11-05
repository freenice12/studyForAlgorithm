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

import common.message.GiveUpRequestMessage;
import common.message.GiveUpResponseMessage;
import common.message.HeartBeatRequestMessage;
import common.message.HeartBeatResponseMessage;
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
import common.model.UserInfo;

import day7DeleteGame.model.Board;
import day7DeleteGame.util.ActivemqConnector;

public class ServerHandler implements MessageListener {

	private int count;
	private int readyCount;
	private ActivemqConnector conn;
	private int turn;
	private Map<UUID, UserInfo> clientMap = new LinkedHashMap<UUID, UserInfo>();
	private GameManager gameManager = new GameManager();
	private Map<UUID, UserInfo> giveupUsers = new LinkedHashMap<UUID, UserInfo>();
	private List<List<Boolean>> recentMap;

	public ServerHandler(ActivemqConnector connector) {
		this.conn = connector;
	}

	@Override
	public void onMessage(final Message message) {
		try {
			Object messageObj = ((ObjectMessage) message).getObject();
			Destination jmsReplyTo = message.getJMSReplyTo();
			if (messageObj instanceof InitRequestMessage) {
				System.out.println("get InitRequestMessage");
				ready(messageObj, jmsReplyTo);
			} else if (messageObj instanceof ReadyRequestMessage) {
				System.out.println("get ReadyRequestMessage");
				start(messageObj, jmsReplyTo);
			} else if (messageObj instanceof SubmitRequestMessage) {
				System.out.println("get SubmitRequestMessage");
				continueGame(messageObj, jmsReplyTo);
			} else if (messageObj instanceof PassRequestMessage) {
				System.out.println("get PassRequestMessage");
				addPassedClient(messageObj, jmsReplyTo);
			} else if (messageObj instanceof HeartBeatRequestMessage) {
				System.out.println("get HeartBeatRequestMessage");
				conn.sendReplyTo(jmsReplyTo, new HeartBeatResponseMessage(
						false, ""));
			} else if (messageObj instanceof GiveUpRequestMessage) {
				System.out.println("get GiveUpRequestMessage");
				manageClient(messageObj, jmsReplyTo);
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
		SubmitRequestMessage submitRequestMessage = (SubmitRequestMessage) messageObj;
		recentMap = submitRequestMessage.getList();
		Board board = new Board(recentMap);
		boolean isFinish = board.isFinish();
		// System.out.println("isFinish? " + isFinish);
		if (submitRequestMessage.isPass()) {
			if (!addPassedClient(messageObj, jmsReplyTo))
				return;
		}
		sendTurnTopic(board.getBoard(), isFinish);
		gameManager.saveHistory(turn, submitRequestMessage);
		if (isFinish) {
			sendWinner(submitRequestMessage.getUuid());
		}
	}
	
	private void sendWinner() {
		sendWinner(UUID.randomUUID());
	}


	private void sendWinner(UUID client) {
		List<String> winner = new ArrayList<String>();
		for (Entry<UUID, UserInfo> entry : clientMap.entrySet()) {
			giveupUsers.put(entry.getKey(), entry.getValue());
			if (entry.getKey().equals(client))
				continue;
			winner.add(entry.getValue().getName());
		}
		System.out.println("Winner: "+winner);
		conn.sendTopic(new EndTopicMessage(winner));
		init();
	}

	private void init() {
		Board.getNewInstance();
		count = 0;
		turn = 0;
		readyCount = 0;
		clientMap = new LinkedHashMap<>();
		gameManager = new GameManager();
	}

	private void start(Object messageObj, Destination jmsReplyTo) {
		conn.sendReplyTo(jmsReplyTo, new ReadyResponseMessage(true, ""));
		handleClient((ReadyRequestMessage)messageObj);
		handleReadyMessage();
	}

	private void handleClient(ReadyRequestMessage messageObj) {
		UUID uuid = messageObj.getUuid();
		UserInfo info = giveupUsers.get(uuid);
		if (info != null) {
			count++;
			clientMap.put(uuid, info);
			giveupUsers.remove(uuid);
		}
	}

	private void ready(Object messageObj, Destination jmsReplyTo) {
		count++;
		UUID uuid = ((InitRequestMessage) messageObj).getUuid();
		String userId = ((InitRequestMessage) messageObj).getUserID();
		for (Entry<UUID, UserInfo> entry : clientMap.entrySet()) {
			if (entry.getValue().getName().equals(userId)) {
				conn.sendReplyTo(jmsReplyTo, new InitResponseMessage(false,
						"Can not use \"" + userId + "\""));
				count--;
				return;
			}
		}
		UserInfo info = new UserInfo(uuid, userId);
		info.setReady(true);
		clientMap.put(uuid, info);
		conn.sendReplyTo(jmsReplyTo, new InitResponseMessage(true, ""));
	}

	private void handleReadyMessage() {
		readyCount++;
		if (readyCount > 1 && clientMap.size() == readyCount) {
			giveupUsers.clear();
			recentMap = Board.getInstance().getBoard();
			gameManager.saveHistory(turn, new SubmitRequestMessage(null,
					recentMap));
			conn.sendTopic(new StartTopicMessage(clientMap));
			sendTurnTopic(recentMap, false);
		}
	}

	private void sendTurnTopic(List<List<Boolean>> board, boolean isFinish) {
		UUID nextClient = getNextClient(isFinish);
		turn++;
		conn.sendTopic(new TurnTopicMessage(nextClient, clientMap, turn, board));
	}

	private UUID getNextClient(boolean isFinish) {
		if (isFinish)
			return UUID.randomUUID();

		int next = (turn - giveupUsers.size()) % clientMap.size();
		int index = 0;
		for (Entry<UUID, UserInfo> entry : clientMap.entrySet()) {
			if (next == index) {
				return entry.getKey();
			}
			index++;
		}
		return UUID.randomUUID();
	}

	private void manageClient(Object messageObj, Destination jmsReplyTo) {
		UUID uuid = ((GiveUpRequestMessage) messageObj).getUuid();
		UserInfo userInfo = clientMap.get(uuid);
		giveupUsers.put(uuid, userInfo);
		clientMap.remove(uuid);
		
		conn.sendReplyTo(jmsReplyTo, new GiveUpResponseMessage(true, ""));
		if (clientMap.size() < 2) {
			sendWinner();
		} else {
			sendTurnTopic(recentMap, false);
		}
	}

}
