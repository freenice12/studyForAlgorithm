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

import day7DeleteGame.util.ActivemqConnector;

public class GameClientTopicHandler implements MessageListener{
	
	private ClientViewHandler viewHandler;
	private GameMapHandler mapHandler;
	private ActivemqConnector connector;
	private Map<UUID, String> clients;
	private boolean canPass = true;
	
	public GameClientTopicHandler(ActivemqConnector conn) {
		this.connector = conn;
//		this.mapHandler = new MapHandler();
		this.mapHandler = new BoardHandler();
	}

	@Override
	public void onMessage(Message message) {
		try {
			Object messageObj = ((ObjectMessage) message).getObject();
			if (messageObj instanceof StartTopicMessage) {
				readyToStart(messageObj);
			}else if (messageObj instanceof TurnTopicMessage) {
				continueGame(messageObj);
			}else if (messageObj instanceof EndTopicMessage) {
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
		String next = getNext(((TurnTopicMessage) messageObj).getUuid());
		viewHandler.updateView(mapHandler.convertToBoard(((TurnTopicMessage) messageObj).getList()));
		if (next != null)
			viewHandler.updateLabel(next, clients.values());
		controlButtons(next);
	}

	private void readyToStart(Object messageObj) {
		clients = new LinkedHashMap<UUID, String>();
		clients.putAll(((StartTopicMessage) messageObj).getUserMap());
		viewHandler.updateLabel("", clients.values());
	}

	public void controlButtons(String next) {
		if (next != null && next.equals(clients.get(viewHandler.uuid))) {
//			viewHandler.enableSendButton(true);
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
		List<List<Boolean>> result = mapHandler.modifyMap(selectedPoints, isAuto);
		if (selectedPoints.isEmpty() && !isAuto || !mapHandler.isChanged())
			this.canPass = false;
		connector.sendTempMessage(new SubmitRequestMessage(viewHandler.uuid, result));
	}

	public String getTurn(UUID uuid) {
		return clients.get(uuid);
	}

}
