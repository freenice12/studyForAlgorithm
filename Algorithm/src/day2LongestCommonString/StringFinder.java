package day2LongestCommonString;

import java.util.HashSet;
import java.util.Set;

public class StringFinder {

	public static void main(String[] args) {
		String firstStr = "Please, peter go swimming!";
		String secondStr = "I’m peter goliswi";
		// String firstStr = "123456";
		// String secondStr = " 23a2346";
		// String firstStr = "12345123jdfjdfjdfjdf";
		// String secondStr = "s902384jdf9123 jdfj";
		System.out.println(StringFinder.findLongist(firstStr, secondStr));
		System.out.println(StringFinder
				.findLongistAlgorism(firstStr, secondStr));
	}

	public static String findLongist(String firstStr, String secondStr) {
		StringBuffer buffer = new StringBuffer();
		Set<String> wordSet = new HashSet<>();
		findLong(firstStr, 0, secondStr, 0, buffer, wordSet);
		String longest = "";
		for (String word : wordSet) {
			// System.out.println(word);
			if (word.length() > longest.length())
				longest = word;
		}
		return longest;
	}

	private static void findLong(String firstStr, int i, String secondStr,
			int j, StringBuffer buffer, Set<String> wordSet) {
		if (firstStr.length() <= i || secondStr.length() <= j)
			return;
		char firstChar = firstStr.charAt(i);
		for (int index = j; index < secondStr.length(); index++) {
			char secondChar = secondStr.charAt(index);
			if (firstChar == secondChar) {
				buffer.append(firstChar);
				findLong(firstStr, i + 1, secondStr, index + 1, buffer, wordSet);
				continue;
			}
			if (buffer.length() > 0) {
				addChar(buffer, wordSet);
				return;
			}
		}
		findLong(firstStr, i + 1, secondStr, 0, buffer, wordSet);
	}

	private static void addChar(StringBuffer buffer, Set<String> wordSet) {
		wordSet.add(buffer.toString());
		buffer.delete(0, buffer.length());
	}

	public static String findLongistAlgorism(String firstStr, String secondStr) {
		String first = firstStr.toLowerCase();
		String second = secondStr.toLowerCase();
		int[][] matchedArrays = new int[first.length()][second.length()];
		int matchedCounter = 0;
		StringBuffer matchedString = new StringBuffer();
		for (int row = 0; row < first.length(); row++) {
			for (int col = 0; col < second.length(); col++) {

				fillMatchedArrays(first, second, matchedArrays, row, col);

				if (matchedArrays[row][col] > matchedCounter) {
					matchedCounter = matchedArrays[row][col];

					int initMatchedCharIndex = row - matchedArrays[row][col]
							+ 1;
					if (matchedCounter != initMatchedCharIndex) {
						matchedCounter = initMatchedCharIndex;
						matchedString = new StringBuffer();
						matchedString.append(firstStr.substring(matchedCounter,
								row + 1));
					}

				}

			}
		}

		// printMap(first, second, matchedArrays);

		return matchedString.toString();
	}

	private static void fillMatchedArrays(String first, String second,
			int[][] matchedArrays, int row, int col) {
		if (row < 1 || col < 1) {
			if (first.charAt(row) == second.charAt(col)) {
				matchedArrays[row][col] = 1;
			}
		} else {
			if (first.charAt(row) == second.charAt(col)) {
				matchedArrays[row][col] = matchedArrays[row - 1][col - 1] + 1;
			}
		}
	}

	private static void printMap(String first, String second,
			int[][] stringArray) {
		for (int row = 0; row < first.length(); row++) {
			for (int col = 0; col < second.length(); col++) {
				System.out.print(stringArray[row][col] + "");
			}
			System.out.println();
		}
	}

}
