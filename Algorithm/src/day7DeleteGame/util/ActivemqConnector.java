package day7DeleteGame.util;

import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class ActivemqConnector implements MqConnector {

	private ConnectionFactory connectionFactory;
	private Connection connection;
	private Session session;
	private MessageConsumer consumer;
	private MessageProducer producer;
	private Queue dest;
	private MessageListener tempMessageHandler;
	private MessageProducer replyProducer;
	private MessageProducer topicProducer;
	private Topic topic;
	private MessageConsumer topicConsumer;

	public ActivemqConnector(String address, int port, boolean flag,
			int autoAcknowledge) {
		try {
			connectionFactory = new ActiveMQConnectionFactory("tcp://" + address + ":" + port);
			connection = connectionFactory.createConnection();
			connection.start();
			//false, Session.AUTO_ACKNOWLEDGE
			session = connection.createSession(flag, autoAcknowledge);
//			initSession(flag, autoAcknowledge);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void createMessageConsumer() {
			try {
			checkSession();
				if (consumer == null)
					consumer = session.createConsumer(dest);
			} catch (JMSException e) {
				e.printStackTrace();
			}
	}
	public void createMessageProducer() {
		try {
			checkSession();
			if (producer == null)
				producer = session.createProducer(dest);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	private void checkSession() throws JMSException {
		if (session == null)
			throw new JMSException("Session == null");
	}

//	public void createMessageProducer() {
//		try {
//			checkSession();
//		if (producer == null)
//			producer = session.createProducer(dest);
//		} catch (JMSException e) {
//			e.printStackTrace();
//		}
//	}
	
	public void createResponseProducer() {
		try {
//			checkSession();
//			if (replyProducer == null) {
			replyProducer = session.createProducer(null);
			replyProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
//			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
//	public void createResponseConsumer(MessageListener handler) {
//		try {
//			tempDest = session.createTemporaryQueue();
//			responseConsumer = session.createConsumer(tempDest);
//			responseConsumer.setMessageListener(handler);
//			setTempMessageHandler(handler);
//		} catch (JMSException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	public void setMessageHandler(MessageListener handler) throws JMSException {
//		if (consumer == null)
//			throw new JMSException("Consumer == null");
		consumer.setMessageListener(handler);
	}

	public void setDestination(String name) {
		try {
			dest = session.createQueue(name);
//			producer = session.createProducer(dest);
//			consumer = session.createConsumer(dest);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void send(Serializable object) {
		try {
			System.out.println("send: "+object);
//			checkSession();
			producer.send(session.createObjectMessage(object));
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void setTopicHandler(MessageListener topicHandler) {
		try {
			topicConsumer = session.createConsumer(topic);
			topicConsumer.setMessageListener(topicHandler);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	public Session getSession() {
		try {
			checkSession();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return session;
	}

	public void createTopic(String name) {
		try {
			topic = session.createTopic(name);
			topicProducer = session.createProducer(session.createTopic(name));
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	public void sendTopic(Serializable object) {
		try {
//			checkSession();
			topicProducer.send(session.createObjectMessage(object));
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	public void sendReplyTo(Destination jmsReplyTo, Serializable object) {
		try {
			replyProducer.send(jmsReplyTo, session.createObjectMessage(object));
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void sendTempMessage(Serializable object) {
		try {
			Destination tempQueue = session.createTemporaryQueue();
			session.createConsumer(tempQueue).setMessageListener(tempMessageHandler);
			session.createProducer(dest).send(createMessage(tempQueue, object));
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	private ObjectMessage createMessage(Destination tempQueue, Serializable requestMessage) throws JMSException {
		ObjectMessage message = session.createObjectMessage(requestMessage);
		message.setJMSReplyTo(tempQueue);
		return message;
	}

	public void setTempMessageHandler(MessageListener messageHandler) {
		this.tempMessageHandler = messageHandler; 
	}

}


