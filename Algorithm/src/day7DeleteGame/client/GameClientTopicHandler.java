package day7DeleteGame.client;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.eclipse.swt.graphics.Point;

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
	private boolean canPass = true;
	
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
		view.updateView(((TurnTopicMessage) messageObj).getMapList());
		String next = getNext(((TurnTopicMessage) messageObj).getCurrentTurnUUID());
		if (next != null) {
			view.updateInfo(((TurnTopicMessage) messageObj).getTurnCount());
			view.updateStatus(next, clients.values());
		}
		controlButtons(next);
	}

	public void controlButtons(String next) {
		if (next != null && next.equals(clients.get(view.uuid).getName())) {
			view.enableAutoButton(true);
			if (canPass) {
				view.enablePassButton(true);
			}
			view.enableGameComposite(true);
		} else {
			view.enableSendButton(false);
			view.enableAutoButton(false);
			view.enablePassButton(false);
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
		this.canPass = false;
	}

	public String getTurn(UUID uuid) {
		return clients.get(uuid).getName();
	}

	public void sendPass(List<List<Boolean>> maps) {
		if (canPass) {
			canPass = false;
			SubmitRequestMessage object = new SubmitRequestMessage(view.uuid, maps);
			object.setPass();
			connector.sendTempMessage(object);
		}
	}
	
	private void endGame(Object messageObj) {
		if (((EndTopicMessage) messageObj).getWinnerIDList().contains(clients.get(view.uuid).getName())) {
			view.showResult(true);
		} else
			view.showResult(false);
	}

//	public void sendInit() {
//		connector.sendTempMessage(new InitRequestMessage(view.uuid,""));
//	}

}
