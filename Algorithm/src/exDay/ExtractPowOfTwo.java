package exDay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExtractPowOfTwo {
	@SuppressWarnings("boxing")
	public static void main(String[] args) {
		List<Integer> arrList = new ArrayList<>();
		arrList.add(10);
		arrList.add(9);
		arrList.add(8);
		arrList.add(7);
		arrList.add(6);
		arrList.add(5);
		arrList.add(4);
		arrList.add(3);
		arrList.add(2);
		arrList.add(1);
		arrList.add(0);

		// Collections.reverse(arrList);
		Collections.sort(arrList);

		getOddElem(arrList);

		// int result = 0;
		// for (Integer ele : arrList) {
		// if (result == 0)
		// result -= ele.intValue();
		// else
		// result += ele.intValue();
		// }
		// System.out.println(result);

		// int result = 0;
		// for (Integer element : arrList) {
		// int pow = Integer.toBinaryString(element.intValue()).length();
		// for(String wow :
		// Integer.toBinaryString(element.intValue()).split("")) {
		// if (wow.equals("1"))
		// result +=Math.pow(2, pow);
		// pow--;
		// }
		// System.out.println(result);
		// result = 0;
		// }

		// String s = "I am A";
		// List<String> r = Arrays.asList(s.split(" "));
		// Collections.reverse(r);
		// for (String rs : r) {
		// System.out.print(rs+" ");
		// }

	}

	private static void getOddElem(List<Integer> arrList) {
		for (Integer i : arrList) {
			getLog(i);
		}
	}

	private static void getLog(Integer i) {
		if (i == 0) {
			System.out.println();
			return;
		}
//		else if (i < 2) {
//			System.out.println(i);
//			return;
//		}
		double result = Math.log(i) / Math.log(2);
		System.out.print((int) result+" ");
		getLog((int) (i - Math.pow(2, (int) result)));
	}
}
