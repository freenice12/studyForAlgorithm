package day6TheGreatPlain.client;

import java.io.Serializable;
import java.util.UUID;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import day6TheGreatPlain.common.message.MapRequestMessage;

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
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		Connection connection;
		try {
			connection = connectionFactory.createConnection();
			connection.start();

			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination topicDestination = session.createTopic("Topic");
			MessageConsumer consumer = session.createConsumer(topicDestination);
			consumer.setMessageListener(new ClientTopicHandler(uuid));

		} catch (JMSException e) {
			e.printStackTrace();
		}
		
		clientHandler = new ClientMessageHandler(this, uuid);
		
	}
	
	public void sendRequest(Serializable RequestMessage) throws JMSException {
		Destination destination = session.createQueue("TestQueue");
		Destination tempQueue = session.createTemporaryQueue();
		
		MessageConsumer consumer = session.createConsumer(tempQueue);
		consumer.setMessageListener(clientHandler);
		
		MessageProducer producer = session.createProducer(destination);
		producer.send(createMessage(tempQueue, RequestMessage));
	}

	private ObjectMessage createMessage(Destination tempQueue, Serializable RequestMessage) throws JMSException {
		ObjectMessage message = session.createObjectMessage(RequestMessage);
		message.setJMSReplyTo(tempQueue);
		return message;
	}

}
