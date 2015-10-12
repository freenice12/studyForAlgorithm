package common.message.topic;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

public class StartTopicMessage implements Serializable {

	private static final long serialVersionUID = 20L;

	private Map<UUID, String> userMap;

	//( user1, user2, user3 ... )
	public StartTopicMessage(Map<UUID, String> userMap) {
		this.userMap = userMap;
	}
	
	public Map<UUID, String> getUserMap() {
		return userMap;
	}
}
