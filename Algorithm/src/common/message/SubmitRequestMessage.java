package common.message;

import java.util.List;
import java.util.UUID;

public class SubmitRequestMessage extends RequestMessage {

	private static final long serialVersionUID = 14L;
	private List<List<Boolean>> list;
	private boolean isPass = false;
	
	public SubmitRequestMessage(UUID uuid, List<List<Boolean>> list) {
		super(uuid);
		this.list = list;
	}
	
	public void setPass() {
		this.isPass = true;
	}
	
	public boolean isPass() {
		return isPass;
	}
	
	public List<List<Boolean>> getList() {
		return list;
	}
}
