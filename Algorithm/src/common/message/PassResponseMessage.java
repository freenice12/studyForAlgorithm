package common.message;

public class PassResponseMessage extends ResponseMessage {
	private static final long serialVersionUID = 17L;

	public PassResponseMessage(boolean result, String resultMsg) {
		super(result, resultMsg);
	}

}
