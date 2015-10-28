package common.message;

public class HeartBeatResponseMessage extends ResponseMessage {

	private static final long serialVersionUID = 19L;
	
	public HeartBeatResponseMessage(boolean result, String resultMsg) {
		super(result, resultMsg);
	}
}