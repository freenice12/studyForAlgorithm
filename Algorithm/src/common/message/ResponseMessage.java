package common.message;

import java.io.Serializable;

public class ResponseMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private boolean result;
	private String resultMsg;
	
	public ResponseMessage(boolean result, String resultMsg) {
		this.result = result;
		this.resultMsg = resultMsg;
	}

	public String getResultMsg() {
		return resultMsg;
	}
	
	public boolean getResult() {
		return result;
	}
}
