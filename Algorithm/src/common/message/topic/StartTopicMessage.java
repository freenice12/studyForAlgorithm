package common.message.topic;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

import common.model.UserInfo;

public class StartTopicMessage implements Serializable {

	private static final long serialVersionUID = 20L;

	private Map<UUID, UserInfo> userMap;

	public StartTopicMessage(Map<UUID, UserInfo> userMap) {
		this.userMap = userMap;
	}
	
	public Map<UUID, UserInfo> getUserMap() {
		return userMap;
	}
}
