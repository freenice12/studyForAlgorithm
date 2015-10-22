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

import common.message.InitRequestMessage;
import common.message.ReadyRequestMessage;
import common.message.SubmitRequestMessage;
import common.message.topic.EndTopicMessage;
import common.message.topic.StartTopicMessage;
import common.message.topic.TurnTopicMessage;
import day7DeleteGame.client.ui.ClientViewHandler;
import day7DeleteGame.util.ActivemqConnector;

public class GameClientTopicHandler implements MessageListener{
	
	private ClientViewHandler viewHandler;
	private GameMapHandler mapHandler;
	private ActivemqConnector connector;
	private Map<UUID, String> clients;
	private boolean canPass = true;
	
	public GameClientTopicHandler(ActivemqConnector conn) {
		this.connector = conn;
		this.mapHandler = new BoardHandler();
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

	private void endGame(Object messageObj) {
		viewHandler.showResult(viewHandler.uuid, ((EndTopicMessage) messageObj).getLoserUser());
	}

	private void continueGame(Object messageObj) {
		viewHandler.updateView(mapHandler.convertToBoard(((TurnTopicMessage) messageObj).getList()));
		String next = getNext(((TurnTopicMessage) messageObj).getUuid());
		if (next != null) {
			viewHandler.updateInfo(((TurnTopicMessage) messageObj).getTurnCount(), next);
			viewHandler.updateLabel(next, clients.values());
		}
		controlButtons(next);
	}

	private void readyToStart(Object messageObj) {
		clients = new LinkedHashMap<UUID, String>();
		clients.putAll(((StartTopicMessage) messageObj).getUserMap());
		if (clients.get(viewHandler.uuid) != null)
			viewHandler.setUserId(clients.get(viewHandler.uuid));
		viewHandler.updateLabel("", clients.values());
	}

	public void controlButtons(String next) {
		if (next != null && next.equals(clients.get(viewHandler.uuid))) {
			viewHandler.enableAutoButton(true);
			if (canPass) {
				viewHandler.enablePassButton(true);
			}
			viewHandler.enableGameComposite(true);
		} else {
			viewHandler.enableSendButton(false);
			viewHandler.enableAutoButton(false);
			viewHandler.enablePassButton(false);
			viewHandler.enableGameComposite(false);
		}
	}
		
	public String getNext(UUID uuid) {
		return clients.get(uuid);
	}
	
	public void sendReady() {
		connector.sendTempMessage(new ReadyRequestMessage(viewHandler.uuid));
	}

	public void setViewer(ClientViewHandler viewer) {
		this.viewHandler = viewer;
	}

	public void sendPoints(Set<Point> selectedPoints, boolean isAuto) {
		if (isAuto)
			mapHandler.setClientsNum(clients.size());
		List<List<Boolean>> result = mapHandler.modifyMap(selectedPoints, isAuto, canPass);
		if (result.isEmpty()) {
			SubmitRequestMessage object = new SubmitRequestMessage(viewHandler.uuid, result);
			object.setPass();
			connector.sendTempMessage(object);
		} else {
			SubmitRequestMessage object = new SubmitRequestMessage(viewHandler.uuid, result);
			connector.sendTempMessage(object);
		}
		this.canPass = false;
	}

	public String getTurn(UUID uuid) {
		return clients.get(uuid);
	}

	public void sendPass() {
		if (canPass) {
			canPass = false;
			SubmitRequestMessage object = new SubmitRequestMessage(viewHandler.uuid, mapHandler.getBoard());
			object.setPass();
			connector.sendTempMessage(object);
		}
	}

	public void sendInit() {
		connector.sendTempMessage(new InitRequestMessage(viewHandler.uuid));
	}

}
