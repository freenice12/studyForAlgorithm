package day6TheGreatPlain.common.message;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class TopicMessage implements Serializable {

	private static final long serialVersionUID = 24L;

	private List<UUID> winnerList;

	public TopicMessage(List<UUID> winnerList) {
		this.winnerList = winnerList;
	}
	
	public List<UUID> getWinnerList() {
		return winnerList;
	}
}
