package day6TheGreatPlain.server;

import java.awt.Point;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class BoardHandler {

	private Map<Point, Integer> board;
	private Random random = new Random();
	private int mapSize = 40;
	private int setSize = 40;
	private ClientManager clientManager;
	
	public BoardHandler() {
		this.board = createMap();
		this.clientManager = new ClientManager(); 
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
	
	@SuppressWarnings("boxing")
	public int computeClientMap(Map<Point, Integer> clientMap) {
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

	public List<UUID> getWinner() {
		return clientManager.getWinner();
	}

	public void addClient(UUID uuid, Map<Point, Integer> map) {
		if (clientManager.isNewClient(uuid)) {
			clientManager.addClient(uuid, computeClientMap(map));
		} else {
			clientManager.setClientMinDiffNum(uuid, computeClientMap(map));
		}
	}

	public Map<Point, Integer> getMap() {
		return board;
	}
}
