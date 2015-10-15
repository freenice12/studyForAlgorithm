package common.message;

import java.util.UUID;

public class PassRequestMessage extends RequestMessage {
	private static final long serialVersionUID = 16L;

	public PassRequestMessage(UUID uuid) {
		super(uuid);
	}
}
