package common.message.topic;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import common.model.UserInfo;

public class TurnTopicMessage implements Serializable {

	private static final long serialVersionUID = 21L;
	private UUID currentTurnUUID;
	private Map<UUID, UserInfo> userMap;
	private int turnCount;
	private List<List<Boolean>> list;

	public TurnTopicMessage(UUID currentTurnUUID, Map<UUID, UserInfo> userMap, int turnCount, List<List<Boolean>> list) {
		this.currentTurnUUID = currentTurnUUID;
		this.userMap = userMap;
		this.turnCount = turnCount;
		this.list = list;
	}

	public UUID getCurrentTurnUUID() {
		return currentTurnUUID;
	}

	public Map<UUID, UserInfo> getUserMap() {
		return userMap;
	}	
	
	public int getTurnCount() {
		return turnCount;
	}

	public List<List<Boolean>> getMapList() {
		return list;
	}
}
