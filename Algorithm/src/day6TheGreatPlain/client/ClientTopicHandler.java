package day6TheGreatPlain.client;

import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import common.message1.TopicMessage;

public class ClientTopicHandler implements MessageListener {

	private UUID uuid;
	public ClientTopicHandler(UUID uuid) {
		this.uuid = uuid;
	}

	@Override
	public void onMessage(Message message) {
		Object messageObj;
		try {
			messageObj = ((ObjectMessage) message).getObject();
			if (messageObj instanceof TopicMessage) {
				printStatus(messageObj);
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	private void printStatus(Object messageObj) {
		for (UUID winner : ((TopicMessage)messageObj).getWinnerList()) {
			if (uuid.equals(winner)) {
				System.out.println("WIN!");
				return;
			}
		}
		System.out.println("another WIN!");
	}

}
