package common.message;

import java.util.UUID;

public class ReadyRequestMessage extends RequestMessage {

	private static final long serialVersionUID = 12L;

	public ReadyRequestMessage(UUID uuid) {
		super(uuid);
	}
}
