package day7DeleteGame.client;

import javax.jms.Session;

import common.message.InitRequestMessage;

import day7DeleteGame.client.ui.ClientViewHandler;
import day7DeleteGame.util.ActivemqConnector;

public class GameClient {

	public static void main(String[] args) {
		
//		ActivemqConnector conn = new ActivemqConnector("146.11.76.135", 61616, false, Session.AUTO_ACKNOWLEDGE);
		ActivemqConnector connector = new ActivemqConnector("localhost", 61616, false, Session.AUTO_ACKNOWLEDGE);
		connector.setDestination("ServerQueue");
		connector.createTopic("Topic");
		connector.createMessageProducer();
		GameClientMessageHandler messageHandler = new GameClientMessageHandler();
		connector.setTempMessageHandler(messageHandler);
		GameClientTopicHandler clientHandler = new GameClientTopicHandler(connector);
		ClientViewHandler viewHandler = new ClientViewHandler(clientHandler);
		clientHandler.setViewer(viewHandler);
		messageHandler.setViewer(viewHandler);
		messageHandler.setTopicHandler(clientHandler);
		connector.setTopicHandler(clientHandler);
		connector.sendTempMessage(new InitRequestMessage(viewHandler.uuid));
		viewHandler.init();
		
	}
}
