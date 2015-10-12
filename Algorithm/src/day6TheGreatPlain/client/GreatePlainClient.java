package day6TheGreatPlain.client;

import java.io.Serializable;
import java.util.UUID;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import common.message1.MapRequestMessage;

public class GreatePlainClient {
	private UUID uuid = UUID.randomUUID();
	private Session session;
	private ClientMessageHandler clientHandler;

	public static void main(String[] args) {
		GreatePlainClient client = new GreatePlainClient();
		client.init();
		try {
			client.sendRequest(new MapRequestMessage());
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}
	
	private void init() {
		clientHandler = new ClientMessageHandler(this, uuid);
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://146.11.76.84:61616");
//		ConnectorFactory1 connectionFactory = new ActiveMQConnectionFactory();
		Connection connection;
		try {
			connection = connectionFactory.createConnection();
			connection.start();

			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			session.createConsumer(session.createTopic("Topic")).setMessageListener(new ClientTopicHandler(uuid));

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	public void sendRequest(Serializable RequestMessage) throws JMSException {
		Destination tempQueue = session.createTemporaryQueue();
		
		session.createConsumer(tempQueue).setMessageListener(clientHandler);
		
		session.createProducer(session.createQueue("ServerQueue")).send(createMessage(tempQueue, RequestMessage));
		System.out.println("send request message");
	}

	private ObjectMessage createMessage(Destination tempQueue, Serializable RequestMessage) throws JMSException {
		ObjectMessage message = session.createObjectMessage(RequestMessage);
		message.setJMSReplyTo(tempQueue);
		return message;
	}

}
