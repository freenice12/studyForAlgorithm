package common.message.topic;

import java.io.Serializable;
import java.util.List;

public class EndTopicMessage implements Serializable {

	private static final long serialVersionUID = 22L;
	private List<String> winnerIDList;

	public EndTopicMessage(List<String> winnerIDList) {
		this.winnerIDList = winnerIDList;
	}
	
	public List<String> getWinnerIDList() {
		return winnerIDList;
	}
}
