package day6TheGreatPlain.server;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class GreatePlainServer {

	private Session session;
	private ServerMessageHandler serverMessageHandler;
	public static void main(String[] args) {

		GreatePlainServer server = new GreatePlainServer();
		int numOfClient = 1;
		server.init(numOfClient);
		
	}
	private void init(int numOfClient) {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		Connection connection;
		try {
			
			connection = connectionFactory.createConnection();
			connection.start();

			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageConsumer consumer = session.createConsumer(session.createQueue("TestQueue"));
			serverMessageHandler = new ServerMessageHandler(session, numOfClient);
			consumer.setMessageListener(serverMessageHandler);

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
