package common.message;

import java.io.Serializable;
import java.util.UUID;

public class RequestMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	private UUID uuid;
	
	public RequestMessage(UUID uuid) {
		this.uuid = uuid;
	}
	
	public UUID getUuid() {
		return uuid;
	}
}
