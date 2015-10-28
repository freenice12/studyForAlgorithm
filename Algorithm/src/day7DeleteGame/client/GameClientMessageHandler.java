package day7DeleteGame.client;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import common.message.GiveUpResponseMessage;
import common.message.HeartBeatResponseMessage;
import common.message.InitResponseMessage;
import common.message.PassResponseMessage;
import common.message.ReadyResponseMessage;
import common.message.SubmitResponseMessage;
import day7DeleteGame.client.ui.NewClientView;

public class GameClientMessageHandler implements MessageListener {
	
	private NewClientView view;
	private GameClientTopicHandler clientHandler;

	@Override
	public void onMessage(Message message) {
		try {
			Object messageObj = ((ObjectMessage) message).getObject();
			if (messageObj instanceof InitResponseMessage) {
				if (!((InitResponseMessage) messageObj).getResult()) {
					System.err.println(((InitResponseMessage) messageObj).getResultMsg());
				}
			} else if (messageObj instanceof ReadyResponseMessage) {
				if (!((ReadyResponseMessage) messageObj).getResult())
					System.err.println(((ReadyResponseMessage) messageObj).getResultMsg());
			} else if (messageObj instanceof SubmitResponseMessage) {
				if (!((SubmitResponseMessage) messageObj).getResult()) {
					System.err.println(((SubmitResponseMessage) messageObj).getResultMsg());
					clientHandler.controlButtons(clientHandler.getTurn(view.uuid));
				}
			} else if (messageObj instanceof PassResponseMessage) {
				if (!((PassResponseMessage) messageObj).getResult())
					System.err.println(((PassResponseMessage) messageObj).getResultMsg());
			} else if (messageObj instanceof HeartBeatResponseMessage) {
				if (!((HeartBeatResponseMessage) messageObj).getResult())
					System.err.println(((HeartBeatResponseMessage) messageObj).getResultMsg());
			} else if (messageObj instanceof GiveUpResponseMessage) {
				if (!((GiveUpResponseMessage) messageObj).getResult())
					System.err.println(((GiveUpResponseMessage) messageObj).getResultMsg());
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
	}

	public void setViewer(NewClientView viewer) {
		view = viewer;
	}

	public void setTopicHandler(MessageListener clientHandler) {
		this.clientHandler = (GameClientTopicHandler) clientHandler; 
	}

}
