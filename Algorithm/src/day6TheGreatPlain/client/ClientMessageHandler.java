package day6TheGreatPlain.client;

import java.awt.Point;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
	private UUID uuid;
	private Map<Point, Integer> givenPoint = new HashMap<Point, Integer>();
	public ClientMessageHandler(GreatePlainClient greatePlainClient, UUID uuid) {
		this.greatePlainClient = greatePlainClient;
		this.uuid = uuid;
	}

	@Override
	public void onMessage(Message message) {
		try {
			Object messageObj = ((ObjectMessage) message).getObject();
			if (messageObj instanceof MapResponseMessage) {
				System.out.println("from server: MapResponseMessage");
				Map<Point, Integer> map = ((MapResponseMessage)messageObj).getMap();
				extractGivenPoint(map);
				sendPlain(map);
				BoardViewer viewer = new BoardViewer(map);
				viewer.init();
			} else if (messageObj instanceof SubmitResponseMessage) {
				System.out.println("from server: SubmitResponseMessage");
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("boxing")
	private void extractGivenPoint(Map<Point, Integer> map) {
		for (Entry<Point, Integer> entry : map.entrySet()) {
			if (entry.getValue() != 0) {
				givenPoint.put(entry.getKey(), entry.getValue());
			}
		}
	}

	private void sendPlain(Map<Point, Integer> map) throws JMSException {
		Map<Point, Integer> plainMap = getPlain(map);
		sendSubmitRequest(plainMap);
	}

	private Map<Point, Integer> getPlain(Map<Point, Integer> map) {
		Map<Point, Integer> tempMap = map;
		for (Entry<Point, Integer> entry : givenPoint.entrySet()) {
			fillNextThePoint(tempMap, entry.getKey(), entry.getValue());
		}
		setZeroToOne(tempMap);
		return tempMap;
	}

	@SuppressWarnings("boxing")
	private void setZeroToOne(Map<Point, Integer> tempMap) {
		for (Entry<Point, Integer> entry : tempMap.entrySet()) {
			if (entry.getValue() == 0) {
				// TODO
				tempMap.put(entry.getKey(), 5);
			}
		}
	}

	@SuppressWarnings("boxing")
	private void fillNextThePoint(Map<Point, Integer> tempMap, Point point, Integer targetValue) {
		int x = point.x;
		int y = point.y;
		Point E = new Point(x+1, y);
		Point W = new Point(x-1, y);
		Point S = new Point(x, y-1);
		Point N = new Point(x, y+1);
		List<Point> checkList = Arrays.asList(E, W, S, N);
		for (Point p : checkList) {
			if (tempMap.containsKey(p) && tempMap.get(p) == 0 && targetValue > 1) {
				tempMap.put(p, targetValue-1);
				fillNextThePoint(tempMap, p, targetValue-1);
			} else if (tempMap.containsKey(p) && tempMap.get(p) != 0 && !givenPoint.containsKey(p)) {
				tempMap.put(p, (tempMap.get(p) + targetValue) / 2);
			}
		}
	}

	private void sendSubmitRequest(Map<Point, Integer> map) throws JMSException {
		SubmitRequestMessage submit = new SubmitRequestMessage(uuid, map);
		greatePlainClient.sendRequest(submit);
		
	}

}
