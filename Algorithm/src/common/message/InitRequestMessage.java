package common.message;

import java.util.UUID;

public class InitRequestMessage extends RequestMessage {

	private static final long serialVersionUID = 10L;

	public InitRequestMessage(UUID uuid) {
		super(uuid);
	}
}
