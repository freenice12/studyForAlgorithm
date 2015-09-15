package day6TheGreatPlain.client;

import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import day6TheGreatPlain.common.message.TopicMessage;

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
				for (UUID winner : ((TopicMessage)messageObj).getWinnerList()) {
					if (uuid.equals(winner)) {
						System.out.println("WIN!");
						return;
					}
				}
				System.out.println("another WIN!");
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
