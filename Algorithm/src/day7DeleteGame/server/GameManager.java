package day7DeleteGame.server;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import common.message.SubmitRequestMessage;

public class GameManager {
	private Map<UUID, Boolean> passedClients = new HashMap<UUID, Boolean>();
	private Map<Integer, SubmitRequestMessage> history = new LinkedHashMap<>();

	public GameManager() {
	}

	public void saveHistory(int turn, SubmitRequestMessage message) {
		history.put(Integer.valueOf(turn), message);
	}

	public boolean putPassClient(UUID client) {
		if (passedClients.containsKey(client))
			return false;
		passedClients.put(client, Boolean.TRUE);
		return true;
	}

	public boolean validMap(List<List<Boolean>> board) {
		for (SubmitRequestMessage savedMessage : history.values()) {
			if (board.equals(savedMessage.getList()))
				return false;
		}
		return true;
	}

	public Object getUUID(int turn) {
		return history.get(Integer.valueOf(turn)).getUuid();
	}

	public boolean passedClient(UUID client) {
		return passedClients.containsKey(client);
	}

}
