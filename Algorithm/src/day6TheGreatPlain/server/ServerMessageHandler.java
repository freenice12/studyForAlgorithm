package day6TheGreatPlain.server;

import java.util.List;
import java.util.UUID;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import day6TheGreatPlain.common.message.MapRequestMessage;
import day6TheGreatPlain.common.message.MapResponseMessage;
import day6TheGreatPlain.common.message.SubmitRequestMessage;
import day6TheGreatPlain.common.message.SubmitResponseMessage;
import day6TheGreatPlain.common.message.TopicMessage;

public class ServerMessageHandler implements MessageListener {
	
	private Session session;
	private int userCount = 0;
	private int numOfClient;
	private int submitCount;
	
	private BoardHandler boardHandler;

	public ServerMessageHandler(Session session, int numOfClient) {
		this.session = session;
		this.numOfClient = numOfClient;
		this.boardHandler = new BoardHandler();
	}
	
	

	@Override
	public void onMessage(Message message) {
		try {
			Object messageObj = ((ObjectMessage) message).getObject();
			if (messageObj instanceof MapRequestMessage) {
				System.out.println("from client: MapRequestMessage");
				userCount++;
				responseMapRequest(message.getJMSReplyTo());
			} else if (messageObj instanceof SubmitRequestMessage) {
				System.out.println("from client: SubmitRequestMessage");
				submitCount++;
				responseSubmitRequest(message.getJMSReplyTo());
				addClient(messageObj);
				if (isReady()) {
					sendTopicWinner();
				}
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}



	private boolean isReady() {
		return userCount == numOfClient && submitCount == numOfClient;
	}
	
	private void responseMapRequest(Destination destination) throws JMSException {
		session.createProducer(destination).send(session.createObjectMessage(new MapResponseMessage(boardHandler.getMap())));
	}
	
	private void responseSubmitRequest(Destination destination) throws JMSException {
		session.createProducer(destination).send(session.createObjectMessage(new SubmitResponseMessage()));
	}
	

	private void addClient(Object messageObj) {
		boardHandler.addClient(((SubmitRequestMessage)messageObj).getUuid(), ((SubmitRequestMessage)messageObj).getMap());
	}


	private void sendTopicWinner() throws JMSException {
		sendTopic(boardHandler.getWinner());
	}

	private void sendTopic(List<UUID> clients)
			throws JMSException {
		session.createProducer(session.createTopic("Topic")).send(session.createObjectMessage(new TopicMessage(clients)));
	}

}
