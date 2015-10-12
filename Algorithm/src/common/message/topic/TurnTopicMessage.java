package common.message.topic;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class TurnTopicMessage implements Serializable {

	private static final long serialVersionUID = 21L;
	private UUID uuid;
	private List<List<Boolean>> list;

	public TurnTopicMessage(UUID uuid, List<List<Boolean>> list) {
		this.uuid = uuid;
		this.list = list;
	}
	
	public UUID getUuid() {
		return uuid;
	}
	
	public List<List<Boolean>> getList() {
		return list;
	}
}
