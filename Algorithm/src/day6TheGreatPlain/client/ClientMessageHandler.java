package day6TheGreatPlain.client;

import java.awt.Point;
import java.util.ArrayList;
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
	private Map<Point, Integer> givenPoints = new HashMap<Point, Integer>();
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
				sendPlainMap(map);
				BoardViewer viewer = new BoardViewer(map);
//				BoardViewer viewer = new BoardViewer(map, givenPoints);
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
				givenPoints.put(entry.getKey(), entry.getValue());
			}
		}
	}

	private void sendPlainMap(Map<Point, Integer> map) throws JMSException {
		Map<Point, Integer> plainMap = getPlain(map);
		sendSubmitRequest(plainMap);
	}

	private Map<Point, Integer> getPlain(Map<Point, Integer> map) {
		Map<Point, Integer> tempMap = map;
		for (Entry<Point, Integer> entry : givenPoints.entrySet()) {
			fillNextThePoint(tempMap, entry.getKey(), entry.getValue());
		}
		setZeroToOne(tempMap);
		return tempMap;
	}

	@SuppressWarnings("boxing")
	private void setZeroToOne(Map<Point, Integer> tempMap) {
		for (Entry<Point, Integer> entry : tempMap.entrySet()) {
			if (entry.getValue() == 0) {
				tempMap.put(entry.getKey(), 1);
			}
		}
	}

	
	@SuppressWarnings("boxing")
	private void fillNextThePoint(Map<Point, Integer> board, Point currentPoint, Integer currentValue) {
		if (currentValue < 1)
			return;
		int x = currentPoint.x;
		int y = currentPoint.y;
		List<Point> checkList = getCheckList(x, y);
		List<Point> nextPoints = new ArrayList<>();
		for (Point p : checkList) {
			if (board.containsKey(p) && !givenPoints.containsKey(p)) {
				if (board.get(p) == 0 && currentValue > 1) {
					board.put(p, currentValue-1);
					nextPoints.add(p);
				} else if (board.get(p) != 0 && board.get(p) != currentValue - 1) {
					if (board.get(p) - currentValue > 1) {
						int meanValue = getMeanValue(board, x, y);						
						board.put(currentPoint, meanValue);
					}
				}
			} else if (board.containsKey(p) && givenPoints.containsKey(p)) {
				board.put(currentPoint, ((board.get(p) + currentValue) / 2) % 9);  
			}
		}
		for (Point p : nextPoints) {
			fillNextThePoint(board, p, currentValue-1);
		}
	}

	@SuppressWarnings("boxing")
	private int getMeanValue(Map<Point, Integer> board, int x, int y) {
		int mean = 0;
		int pointCount = 0;
		List<Point> newsPoints = getCheckList(x, y);
		for (Point point : newsPoints) {
			if (board.containsKey(point) && board.get(point) != 0) {
				pointCount++;
				mean += board.get(point);
			}
		}
		return ((mean / pointCount) + 1) % 9;
	}

	private List<Point> getCheckList(int x, int y) {
		Point E = new Point(x+1, y);
		Point W = new Point(x-1, y);
		Point S = new Point(x, y-1);
		Point N = new Point(x, y+1);
		List<Point> checkList = Arrays.asList(E, W, S, N);
		return checkList;
	}

	private void sendSubmitRequest(Map<Point, Integer> map) throws JMSException {
		SubmitRequestMessage submit = new SubmitRequestMessage(uuid, map);
		greatePlainClient.sendRequest(submit);
		
	}

}
