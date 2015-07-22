package day2LongestCommonString;

import java.util.ArrayList;
import java.util.List;

public class StringFinderJY {
	
	public static void main(String[] args) {
		// 공백 단위
		String firstStr = "a b c d e f";
		String secondStr = "a b z d e f";
		System.out.println(StringFinderJY.findLongist(firstStr, secondStr));
	}
	

	public static String findLongist(String firstStr, String secondStr) {
		List<String> compareStringList = new ArrayList<>();
		
		compareStringList = compareList(firstStr, secondStr);
		
		String longest = "";
		for (String word : compareStringList) {
			if (word.length() > longest.length()) {
				longest = word;
			}
		}
		
		return longest;
	}


	private static List<String> compareList(String firstStr, String secondStr) {
		List<String> compareStringList = new ArrayList<>();
		String[] firstSplit = firstStr.split(" ");
		String[] secondSplit = secondStr.split(" ");
		
		StringBuffer compareString = new StringBuffer();
//		StringBuffer diffString = new StringBuffer();
		for (int i = 0; i < firstSplit.length; i++) {
			for (int j = i; j < secondSplit.length; j++) {
				if (firstSplit[i].equals(secondSplit[j])) {
					compareString.append(firstSplit[i]+" ");
					break;
				}
//				diffString.append(secondSplit[j]+" ");
				compareString.delete(0, compareString.length());
			}
			compareStringList.add(compareString.toString());
		}
		return compareStringList;
	}

	
}
