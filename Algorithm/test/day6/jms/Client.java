package day6.jms;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import day6.jms.message.RequestMessage;

public class Client {

	Session session;
	
	@Test
	public void test() throws JMSException, InterruptedException {

		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		Connection connection = connectionFactory.createConnection();
		connection.start();

		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination topicDestination = session.createTopic("Topic");
		MessageConsumer consumer = session.createConsumer(topicDestination);
		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message arg0) {
				System.out.println("Received Topic Message : " + arg0);
			}
		});
		
		sendMessage();
		
		Thread.sleep(100*1000);
	}

	private void sendMessage() {
		//Send Message
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				try {
					sendRequest();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}, new Date(), 5*1000L);
	}

	private void sendRequest() throws JMSException {
		Destination destination = session.createQueue("TestQueue");
		Destination tempQueue = session.createTemporaryQueue();
		
		MessageConsumer consumer = session.createConsumer(tempQueue);
		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message arg0) {
				System.out.println("Received Response Message : " + arg0);
			}
		});
		
		MessageProducer producer = session.createProducer(destination);
		producer.send(createMessage(session, tempQueue));
	}

	private ObjectMessage createMessage(Session session, Destination tempQueue) throws JMSException {
		ObjectMessage message = session.createObjectMessage(new RequestMessage());
		message.setJMSReplyTo(tempQueue);
		return message;
	}
}
