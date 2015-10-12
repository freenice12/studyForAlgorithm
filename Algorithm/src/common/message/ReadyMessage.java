package common.message;

import java.util.UUID;



public class ReadyMessage extends RequestMessage {

	private static final long serialVersionUID = 12L;

	public ReadyMessage(UUID uuid) {
		super(uuid);
	}
}
