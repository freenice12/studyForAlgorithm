package common.message;

import java.util.UUID;

public class InitRequestMessage extends RequestMessage {

	private static final long serialVersionUID = 10L;
	private String userID;

	public InitRequestMessage(UUID uuid, String userID) {
		super(uuid);
		this.userID = userID;
	}

	public String getUserID() {
		return userID;
	}
}
