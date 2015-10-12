package common.message;




public class ReadyResponseMessage extends ResponseMessage {

	private static final long serialVersionUID = 13L;

	public ReadyResponseMessage(boolean result, String resultMsg) {
		super(result, resultMsg);
	}
}
