package exDay;

import java.util.ArrayList;
import java.util.List;

public class IOError {
	public static void main(String[] args) {
//		String s = "OIOOIIIIOIOOIOII";
		String s = "OIOOIOOIOOIOOOOOOOIOOIIIOOIIIIOOOOIIOOIIOOIOOIIIOOIOOOOOOOIOOOIOOIOOOOIIOOIIOOOOOIIOOIOOOOIIOOIIOOIOOOOOOIOOIOIOOOIIOIOOOIIOIIOIOOIOOOIOOOIOOOOIOOIOOOOOOOIIIOIOOOIOIOOI";
		List<String> results = extractBinaryStrings(s);
		StringBuffer sb = new StringBuffer();
		for (String result : results) {
			sb.append((char)Integer.parseInt(StringToBinary(result), 2));
		}
		System.out.println(sb.toString());
		
	}

	private static List<String> extractBinaryStrings(String s) {
		List<String> bis = new ArrayList<>();
		StringBuffer sb = new StringBuffer();
		int index = 0;
		for (char c : s.toCharArray()) {
			index++;
			sb.append(c);
			if (index % 8 == 0) {
				bis.add(sb.toString());
				sb.delete(0, sb.capacity());
			}
		}
		return bis;
	}

	private static String StringToBinary(String s1) {
		StringBuffer sb = new StringBuffer();
		for (char c : s1.toCharArray()) {
			if (c == 'O')
				sb.append(0);
			if (c == 'I')
				sb.append(1);
		}
		return sb.toString();
	}
}
