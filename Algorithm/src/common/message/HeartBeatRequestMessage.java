package common.message;

import java.util.UUID;

public class HeartBeatRequestMessage extends RequestMessage {

	private static final long serialVersionUID = 18L;

	public HeartBeatRequestMessage(UUID uuid) {
		super(uuid);
	}
}
