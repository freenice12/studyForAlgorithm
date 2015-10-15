package day7DeleteGame.client;

import javax.jms.Session;

import common.message.InitRequestMessage;
import day7DeleteGame.client.ui.ClientViewHandler;
import day7DeleteGame.util.ActivemqConnector;

public class GameClient {

	public static void main(String[] args) {
		
//		ActivemqConnector conn = new ActivemqConnector("146.11.76.127", 61616, false, Session.AUTO_ACKNOWLEDGE);
		ActivemqConnector conn = new ActivemqConnector("localhost", 61616, false, Session.AUTO_ACKNOWLEDGE);
		conn.setDestination("ServerQueue");
		conn.createTopic("Topic");
		conn.createMessageProducer();
		GameClientMessageHandler messageHandler = new GameClientMessageHandler();
		conn.setTempMessageHandler(messageHandler);
		GameClientTopicHandler clientHandler = new GameClientTopicHandler(conn);
		ClientViewHandler viewHandler = new ClientViewHandler(clientHandler);
		clientHandler.setViewer(viewHandler);
		messageHandler.setViewer(viewHandler);
		messageHandler.setTopicHandler(clientHandler);
		conn.setTopicHandler(clientHandler);
		conn.sendTempMessage(new InitRequestMessage(viewHandler.uuid));
		viewHandler.init();
		
	}
}
