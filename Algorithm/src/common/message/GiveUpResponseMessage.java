package common.message;

public class GiveUpResponseMessage extends ResponseMessage {

	private static final long serialVersionUID = 17L;
	
	public GiveUpResponseMessage(boolean result, String resultMsg) {
		super(result, resultMsg);
	}
}

