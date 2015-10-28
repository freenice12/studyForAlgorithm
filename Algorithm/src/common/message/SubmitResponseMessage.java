package common.message;

public class SubmitResponseMessage extends ResponseMessage {

	private static final long serialVersionUID = 15L;
	
	public SubmitResponseMessage(boolean result, String resultMsg) {
		super(result, resultMsg);
	}
}

