package day6TheGreatPlain.client;

import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import day6TheGreatPlain.common.message.MapResponseMessage;
import day6TheGreatPlain.common.message.SubmitRequestMessage;
import day6TheGreatPlain.common.message.SubmitResponseMessage;
import day6TheGreatPlain.view.BoardViewer;

public class ClientMessageHandler implements MessageListener {
	private GreatePlainClient greatePlainClient;
	private ClientManager clientManager = new ClientManager();
	
	public ClientMessageHandler(GreatePlainClient greatePlainClient, UUID uuid) {
		this.greatePlainClient = greatePlainClient;
		clientManager.setUUID(uuid);
	}

	@Override
	public void onMessage(Message message) {
		try {
			Object messageObj = ((ObjectMessage) message).getObject();
			if (messageObj instanceof MapResponseMessage) {
				System.out.println("from server: MapResponseMessage");
				clientManager.setMap(((MapResponseMessage)messageObj).getMap());
				sendSubmitRequest();
				showMap();
			} else if (messageObj instanceof SubmitResponseMessage) {
				System.out.println("from server: SubmitResponseMessage");
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}


	private void sendSubmitRequest() throws JMSException {
		SubmitRequestMessage submit = new SubmitRequestMessage(clientManager.getUUID(), clientManager.getMap());
		greatePlainClient.sendRequest(submit);
	}

	private void showMap() {
		BoardViewer viewer = new BoardViewer(clientManager.getMap());
		viewer.init();
	}
}
