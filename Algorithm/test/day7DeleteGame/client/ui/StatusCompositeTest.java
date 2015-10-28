package day7DeleteGame.client.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class StatusCompositeTest {

	private List<String> userNames = new ArrayList<>();

	@Test
	public final void test() {
		Map<String, String> a = new LinkedHashMap<>();
		a.put("1", "1");
		a.put("2", "21");
		a.put("3", "31");
		setClientsLabel("", a.values());
	}
	
	public void setClientsLabel(final String id, Collection<String> clients) {
		if (id.isEmpty()) {
			userNames.addAll(clients);
			System.out.println(userNames);
			return;
		}
	}

}
