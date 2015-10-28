package common.message;

import java.util.UUID;

public class GiveUpRequestMessage extends RequestMessage {

	private static final long serialVersionUID = 16L;

	public GiveUpRequestMessage(UUID uuid) {
		super(uuid);
	}
}
