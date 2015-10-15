package day7DeleteGame.server;

import javax.jms.JMSException;
import javax.jms.Session;

import day7DeleteGame.util.ActivemqConnector;

public class GameServer {

	public static void main(String[] args) {
		try {
			// Caused by: java.io.InvalidClassException: common.message.SubmitResponseMessage; local class incompatible: stream classdesc serialVersionUID = 15, local class serialVersionUID = 14
			ActivemqConnector conn = new ActivemqConnector("localhost", 61616, false, Session.AUTO_ACKNOWLEDGE);
			conn.setDestination("ServerQueue");
			conn.createTopic("Topic");
			conn.createMessageConsumer();
			conn.createResponseProducer();
			ServerHandler handler = new ServerHandler(conn);
			conn.setMessageHandler(handler);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
