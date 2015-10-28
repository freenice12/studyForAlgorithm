package common.message;

public class InitResponseMessage extends ResponseMessage {

	private static final long serialVersionUID = 11L;
	
	public InitResponseMessage(boolean result, String resultMsg) {
		super(result, resultMsg);
	}
}

