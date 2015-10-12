package day7DeleteGame.client;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import common.message.InitResponseMessage;
import common.message.ReadyResponseMessage;
import common.message.SubmitResponseMessage;

public class GameClientMessageHandler implements MessageListener {
	
	private ClientViewHandler viewHandler;
	private GameClientTopicHandler clientHandler;

	@Override
	public void onMessage(Message message) {
		try {
			Object messageObj = ((ObjectMessage) message).getObject();
			if (messageObj instanceof InitResponseMessage) {
				if (!((InitResponseMessage) messageObj).getResult())
					System.err.println(((InitResponseMessage) messageObj).getResultMsg());
			}
			if (messageObj instanceof ReadyResponseMessage) {
				if (!((ReadyResponseMessage) messageObj).getResult())
					System.err.println(((ReadyResponseMessage) messageObj).getResultMsg());
			}
			if (messageObj instanceof SubmitResponseMessage) {
				if (!((SubmitResponseMessage) messageObj).getResult()) {
					System.err.println(((SubmitResponseMessage) messageObj).getResultMsg());
					clientHandler.controlButtons(clientHandler.getTurn(viewHandler.uuid));
				}
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
	}

	public void setViewer(ClientViewHandler viewer) {
		viewHandler = viewer;
	}

	public void setTopicHandler(GameClientTopicHandler clientHandler) {
		this.clientHandler = clientHandler; 
	}

}
