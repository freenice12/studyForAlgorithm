package day6.jms;

import java.io.UnsupportedEncodingException;
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

import day6.jms.message.ResponseMessage;

public class Server {
	
	Session session;
	
	@Test
	public void test() throws UnsupportedEncodingException, JMSException, InterruptedException {
		
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		Connection connection = connectionFactory.createConnection();
		connection.start();
		
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		MessageConsumer consumer = session.createConsumer(session.createQueue("TestQueue"));
		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				System.out.println("Received Request Message : " + message);
				try {
					sendResponse(message.getJMSReplyTo());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		
		sendTopicMessage();
		
		Thread.sleep(100*1000);
	}

	private void sendTopicMessage() {
		//Send Message
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				try {
					sendTopic();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}, new Date(), 10*1000L);
	}

	private void sendTopic() throws JMSException {
		Destination topicDestination = session.createTopic("Topic");
		MessageProducer messageProducer = session.createProducer(topicDestination);
//		ObjectMessage message = session.createObjectMessage(new TopicMessage());
//		messageProducer.send(message);
	}

	private void sendResponse(Destination destination)	throws JMSException {
		MessageProducer messageProducer = session.createProducer(destination);
		ObjectMessage message = session.createObjectMessage(new ResponseMessage());
		messageProducer.send(message);
	}
}