package day2LongestCommonString;

public class StringFinderAlgorism {

	public static void main(String[] args) {
		String firstStr = "Please, peter go swimming!";
		String secondStr = "I’m peter goliswi";
		System.out.println(StringFinderAlgorism
				.findLongistAlgorism(firstStr, secondStr));
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

					int initMatchedCharIndex = row - matchedArrays[row][col] + 1;
					if (matchedCounter != initMatchedCharIndex) {
						matchedCounter = initMatchedCharIndex;
						matchedString = new StringBuffer();
						matchedString.append(firstStr.substring(matchedCounter,
								row + 1));
					}

				}

			}
		}

//		printMap(first, second, matchedArrays);

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

//	private static void printMap(String first, String second,
//			int[][] stringArray) {
//		for (int row = 0; row < first.length(); row++) {
//			for (int col = 0; col < second.length(); col++) {
//				System.out.print(stringArray[row][col] + "");
//			}
//			System.out.println();
//		}
//	}

}
