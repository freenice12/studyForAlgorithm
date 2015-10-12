package common.message.topic;

import java.io.Serializable;
import java.util.UUID;

public class EndTopicMessage implements Serializable {

	private static final long serialVersionUID = 22L;
	private UUID uuid;

	public EndTopicMessage(UUID uuid) {
		this.uuid = uuid;
	}
	
	public UUID getLoserUser() {
		return uuid;
	}
}
