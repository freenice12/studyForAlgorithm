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
	private int userCount = 0;
	private int requestCount = 2;
	private Map<Point, Integer> board;
	private Map<UUID, Integer> clientsResult = new HashMap<>();
	private int submitCount;
	private int mapSize = 40;
	private int setSize = 40;

	public ServerMessageHandler(Session session) {
		this.session = session;
	}

	@Override
	public void onMessage(Message message) {
		try {
			Object messageObj = ((ObjectMessage) message).getObject();
			if (messageObj instanceof MapRequestMessage) {
				userCount++;
				System.out.println("from client: MapRequestMessage");
				responseMapRequest(message.getJMSReplyTo());
			} else if (messageObj instanceof SubmitRequestMessage) {
				submitCount++;
				responseSubmitRequest(message.getJMSReplyTo());
				System.out.println("from client: SubmitRequestMessage");
				parseClient(messageObj);
				if (userCount == requestCount && submitCount == requestCount) {
					System.out.println("userCount: " + userCount
							+ " / requestCount: " + requestCount);
					printClientInfo();
					sendTopicWinner();
				}
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	private void printClientInfo() {
		for (Entry<UUID, Integer> entry : clientsResult.entrySet()) {
			System.out.println("client: "+entry.getKey()+" -> "+entry.getValue());
		}
	}

	private void parseClient(Object messageObj) {
		UUID client = ((SubmitRequestMessage)messageObj).getUuid();
		Map<Point, Integer> clientMap = ((SubmitRequestMessage)messageObj).getMap();
		int diffNum = computeClientMap(clientMap);
		addClient(client, diffNum);
	}

	private void responseSubmitRequest(Destination destination) throws JMSException {
		SubmitResponseMessage response = new SubmitResponseMessage();
		MessageProducer messageProducer = session.createProducer(destination);
		ObjectMessage message = session.createObjectMessage(response);
		messageProducer.send(message);
	}

	@SuppressWarnings("boxing")
	private void addClient(UUID client, int diffNum) {
		clientsResult.put(client, diffNum);
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
		Point E = new Point(x+1, y);
		Point W = new Point(x-1, y);
		Point S = new Point(x, y-1);
		Point N = new Point(x, y+1);
		List<Point> checkList = Arrays.asList(E, W, S, N);
		for (Point point : checkList) {
			if (board.containsKey(point)) {
				divCounter++;
				diff += Math.pow(Math.abs(board.get(point) - target), 2);
			}
				
		}
		return diff / divCounter;
	}

	private UUID winer;
	@SuppressWarnings("boxing")
	private void sendTopicWinner() throws JMSException {
		List<UUID> clients = new ArrayList<>();
		int min = Integer.MAX_VALUE;
		for (Entry<UUID, Integer> entry : clientsResult.entrySet()) {
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
		int min = clientsResult.get(winer);
		for (Entry<UUID, Integer> entry : clientsResult.entrySet()) {
			if (entry.getValue() == min && !clients.contains(entry.getKey())) {
				clients.add(entry.getKey());
			}
		}
	}

	private void responseMapRequest(Destination destination) throws JMSException {
		Map<Point, Integer> map = createMap();
		MapResponseMessage response = new MapResponseMessage(map);
		MessageProducer messageProducer = session.createProducer(destination);
		ObjectMessage message = session.createObjectMessage(response);
		messageProducer.send(message);
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

}
