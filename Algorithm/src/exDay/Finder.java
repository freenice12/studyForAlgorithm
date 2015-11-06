package exDay;

import java.util.Arrays;
import java.util.List;



public class Finder {
	public static void main(String[] args) {
		String input = "I am going to watch a movie";
		List<String> inputCharList = Arrays.asList(input.replace(" ", "").toLowerCase().split(""));
		String result = "";
		for (int i = 1; i < input.length(); i++) {
			for (int j=1; j < inputCharList.size() - i; j++) {
				String sub = input.replace(" ", "").toLowerCase().substring(j, inputCharList.size()-1);
				result = inputCharList.get(j);
				if (!sub.contains(result)) {
					System.out.println("result: "+result);
				}
			}
		}
	}
}
