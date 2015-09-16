package day6TheGreatPlain.server;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import day6TheGreatPlain.common.message.MapRequestMessage;
import day6TheGreatPlain.common.message.MapResponseMessage;
import day6TheGreatPlain.common.message.SubmitRequestMessage;
import day6TheGreatPlain.common.message.SubmitResponseMessage;
import day6TheGreatPlain.common.message.TopicMessage;

public class ServerMessageHandler implements MessageListener {
	private Random random = new Random();
	private Session session;
	private Map<Point, Integer> board;
	private Map<UUID, Integer> clientResults = new HashMap<>();
	private int userCount = 0;
	private int numOfClient;
	private int submitCount;
	private int mapSize = 40;
	private int setSize = 40;

	public ServerMessageHandler(Session session, int numOfClient) {
		this.session = session;
		this.numOfClient = numOfClient;
		board = createMap();
	}
	
	@SuppressWarnings("boxing")
	private Map<Point, Integer> createMap() {
		if (board == null) {
			board = new HashMap<>();
			while (setSize-- > 0) {
				int x = random.nextInt(mapSize);
				int y = random.nextInt(mapSize);
				if (board.containsKey(new Point(x, y))) {
					setSize++;
					continue;
				}
				board.put(new Point(x, y), random.nextInt(9) + 1);
			}
			for (int x = 0; x < mapSize; x++) {
				for (int y = 0; y < mapSize; y++) {
					if (board.get(new Point(x, y)) == null) {
						board.put(new Point(x, y), 0);
					}
				}
			}
		}
		return board;
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
				if (userCount == numOfClient && submitCount == numOfClient) {
					System.out.println("userCount: " + userCount + " / numOfClient: " + numOfClient);
					printClientInfo();
					sendTopicWinner();
					rollback();
				}
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	private void rollback() {
		userCount = 0;
		submitCount = 0;
	}

	private void responseMapRequest(Destination destination) throws JMSException {
//		Map<Point, Integer> map = createMap();
		MapResponseMessage response = new MapResponseMessage(board);
		MessageProducer messageProducer = session.createProducer(destination);
		ObjectMessage message = session.createObjectMessage(response);
		messageProducer.send(message);
	}
	
	private void responseSubmitRequest(Destination destination) throws JMSException {
		SubmitResponseMessage response = new SubmitResponseMessage();
		MessageProducer messageProducer = session.createProducer(destination);
		ObjectMessage message = session.createObjectMessage(response);
		messageProducer.send(message);
	}
	

	@SuppressWarnings("boxing")
	private void addClient(Object messageObj) {
		UUID client = ((SubmitRequestMessage)messageObj).getUuid();
		Map<Point, Integer> clientMap = ((SubmitRequestMessage)messageObj).getMap();
		int diffNum = computeClientMap(clientMap);
		clientResults.put(client, diffNum);
	}


	private void printClientInfo() {
		for (Entry<UUID, Integer> entry : clientResults.entrySet()) {
			System.out.println("client: "+entry.getKey()+" -> "+entry.getValue());
		}
	}
	
	private UUID winer;
	@SuppressWarnings("boxing")
	private void sendTopicWinner() throws JMSException {
		List<UUID> clients = new ArrayList<>();
		int min = Integer.MAX_VALUE;
		for (Entry<UUID, Integer> entry : clientResults.entrySet()) {
			int value = entry.getValue();
			if (value < min) {
				min = value;
				winer = entry.getKey();
			}
		}
		clients.add(winer);
		addDrawer(clients);
		
		sendTopic(clients);
	}

	

	@SuppressWarnings("boxing")
	private int computeClientMap(Map<Point, Integer> clientMap) {
		int result = 0;
		for (int x=0; x<mapSize ; x++) {
			for (int y=0; y<mapSize; y++) {
				int target = clientMap.get(new Point(x, y));
				result += getDiffFrom(target, x, y);
			}
		}
		System.out.println("The result is : " + result);
		return result;
	}

	@SuppressWarnings("boxing")
	private int getDiffFrom(int target, int x, int y) {
		int divCounter = 0;
		int diff = 0;
		List<Point> checkList = getCheckList(x, y);
		for (Point point : checkList) {
			if (board.containsKey(point)) {
				divCounter++;
				diff += Math.pow(Math.abs(board.get(point) - target), 2);
			}
				
		}
		return diff / divCounter;
	}

	private List<Point> getCheckList(int x, int y) {
		Point E = new Point(x+1, y);
		Point W = new Point(x-1, y);
		Point S = new Point(x, y-1);
		Point N = new Point(x, y+1);
		List<Point> checkList = Arrays.asList(E, W, S, N);
		return checkList;
	}


	private void sendTopic(List<UUID> clients)
			throws JMSException {
		Destination topicDestination = session.createTopic("Topic");
		MessageProducer messageProducer = session.createProducer(topicDestination);
		TopicMessage topic = new TopicMessage(clients);
		ObjectMessage message = session.createObjectMessage(topic);
		messageProducer.send(message);
	}

	@SuppressWarnings("boxing")
	private void addDrawer(List<UUID> clients) {
		int min = clientResults.get(winer);
		for (Entry<UUID, Integer> entry : clientResults.entrySet()) {
			if (entry.getValue() == min && !clients.contains(entry.getKey())) {
				clients.add(entry.getKey());
			}
		}
	}

}
