package day7DeleteGame.client;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import common.message.GiveUpRequestMessage;
import common.message.HeartBeatRequestMessage;
import common.message.ReadyRequestMessage;
import common.message.SubmitRequestMessage;
import common.message.topic.EndTopicMessage;
import common.message.topic.StartTopicMessage;
import common.message.topic.TurnTopicMessage;
import common.model.UserInfo;
import day7DeleteGame.client.ui.NewClientView;
import day7DeleteGame.util.ActivemqConnector;

public class GameClientTopicHandler implements MessageListener{
	
	private NewClientView view;
//	private GameMapHandler mapHandler;
	private ActivemqConnector connector;
	private Map<UUID, UserInfo> clients;
	private boolean heartbeat;
	private boolean isNewGame;
	
	public GameClientTopicHandler(ActivemqConnector conn, NewClientView view) {
		this.connector = conn;
		this.view = view;
//		this.mapHandler = new BoardHandler();
		
	}

	@Override
	public void onMessage(Message message) {
		try {
			Object messageObj = ((ObjectMessage) message).getObject();
			if (messageObj instanceof StartTopicMessage) {
				System.out.println("get start topic");
				readyToStart(messageObj);
			}else if (messageObj instanceof TurnTopicMessage) {
				System.out.println("get turn topic");
				continueGame(messageObj);
			}else if (messageObj instanceof EndTopicMessage) {
				System.out.println("get end topic");
				endGame(messageObj);
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	private void readyToStart(Object messageObj) {
		clients = new LinkedHashMap<UUID, UserInfo>();
		clients.putAll(((StartTopicMessage) messageObj).getUserMap());
		view.updateStatus("", clients.values());
	}

	private void continueGame(Object messageObj) {
		heartbeat = true;
		view.updateView(((TurnTopicMessage) messageObj).getMapList());
		String next = getNext(((TurnTopicMessage) messageObj).getCurrentTurnUUID());
		if (next != null) {
			int turnCount = ((TurnTopicMessage) messageObj).getTurnCount();
			view.updateInfo(turnCount);
			view.updateStatus(next, clients.values());
		}
		controlButtons(next);
	}

	public void controlButtons(String next) {
		if (next != null && next.equals(clients.get(view.uuid).getName())) {
			view.enableGameComposite(true);
			view.enableAutoButton(true);
			view.enableGiveupButton(true);
			if (view.getCanPass()) {
				view.enablePassButton(true);
			}
		} else {
			view.enableSendButton(false);
			view.enableAutoButton(false);
			view.enablePassButton(false);
			view.enableGiveupButton(false);
			view.enableGameComposite(false);
		}
	}
		
	public String getNext(UUID uuid) {
		UserInfo userInfo = clients.get(uuid);
		if (userInfo != null)
			return userInfo.getName();
		return null;
	}
	
	public void sendReady() {
		isNewGame = true;
		connector.sendTempMessage(new ReadyRequestMessage(view.uuid));
	}

//	public void setViewer(ClientViewHandler viewer) {
//		this.view = viewer;
//	}

	public void sendPoints(List<List<Boolean>> maps) {
//		if (isAuto)
//			mapHandler.setClientsNum(clients.size());
//		List<List<Boolean>> result = mapHandler.getModifiedBoard(selectedPoints, isAuto, canPass);
		if (maps.isEmpty()) {
			SubmitRequestMessage object = new SubmitRequestMessage(view.uuid, maps);
			object.setPass();
			connector.sendTempMessage(object);
		} else {
			SubmitRequestMessage object = new SubmitRequestMessage(view.uuid, maps);
			connector.sendTempMessage(object);
		}
//		this.canPass = false;
	}

	public String getTurn(UUID uuid) {
		return clients.get(uuid).getName();
	}

	public void sendPass(List<List<Boolean>> maps) {
		if (view.getCanPass()) {
//			canPass = false;
			SubmitRequestMessage object = new SubmitRequestMessage(view.uuid, maps);
			object.setPass();
			connector.sendTempMessage(object);
		}
	}
	
	private void endGame(Object messageObj) {
		if (((EndTopicMessage) messageObj).getWinnerIDList().contains(clients.get(view.uuid).getName())) {
			view.showResult(true);
		} else {
			view.showResult(false);
		}
		heartbeat = false;
		isNewGame = false;
		view.initStatus();
		view.setCanPass(true);
	}

	public void sendGiveup() {
		GiveUpRequestMessage object = new GiveUpRequestMessage(view.uuid);
		connector.sendTempMessage(object);
	}
	
	public void sendHeartbeat() {
		connector.sendTempMessage(new HeartBeatRequestMessage(view.uuid));
	}

	public boolean startHeartbeat() {
		return heartbeat;
	}
	
	public boolean getIsNewGame() {
		return isNewGame;
	}

//	public void sendInit() {
//		connector.sendTempMessage(new InitRequestMessage(view.uuid,""));
//	}

}
