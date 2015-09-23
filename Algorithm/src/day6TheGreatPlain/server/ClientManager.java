package day6TheGreatPlain.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

public class ClientManager {

	private Map<UUID, Integer> clientResults = new HashMap<>();
	
	
	public ClientManager() {
		
	}

	@SuppressWarnings("boxing")
	public void addClient(UUID clientUUID, int diffNum) {
		clientResults.put(clientUUID, diffNum);
	}
	
	public List<UUID> getWinner() {
		UUID winner = searchWinner();
		return getTies(clientResults.get(winner));
	}

	@SuppressWarnings("boxing")
	private List<UUID> getTies(Integer min) {
		List<UUID> clients = new ArrayList<>();
		for (Entry<UUID, Integer> entry : clientResults.entrySet()) {
			if (entry.getValue() <= min)
				clients.add(entry.getKey());
		}
		if (clients.isEmpty())
			return Collections.EMPTY_LIST;
		return clients;
	}

	@SuppressWarnings("boxing")
	private UUID searchWinner() {
		UUID winner = null;
		int min = Integer.MAX_VALUE;
		for (Entry<UUID, Integer> entry : clientResults.entrySet()) {
			int value = entry.getValue();
			if (value < min) {
				min = value;
				winner = entry.getKey();
			}
		}
		return winner;
	}
	
}
