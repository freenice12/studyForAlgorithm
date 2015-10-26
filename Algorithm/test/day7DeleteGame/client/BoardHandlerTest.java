package day7DeleteGame.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

public class BoardHandlerTest {

	private static Map<Integer, Integer> board;
	private static List<List<Integer>> powList = new ArrayList<List<Integer>>();

	@SuppressWarnings("boxing")
	@Test
	public final void test() {
		int index = 0;
		board = new HashMap<>();
		board.put(index++, 5);
		board.put(index++, 7);
		board.put(index++, 9);
		
		for (Entry<Integer, Integer> entry : board.entrySet()) {
			System.out.println("k: "+entry.getKey()+" / v: "+entry.getValue());
		}

		List<Integer> results = new ArrayList<Integer>();
		for (Integer i : board.values()) {
			results.add(i);
		}
		Collections.sort(results);
		getOddElem(results);
		System.out.println("============");
		for (List<Integer> elemList : powList) {
			for (Integer elem : elemList) {
				System.out.print(elem + " ");
			}
			System.out.println();
		}
		System.out.println("============");

		while (hasEven()) {
			Iterator<List<Integer>> powElems = powList.iterator();
			int i = 0;
			while (powElems.hasNext() && i + 1 < powList.size()) {
				Iterator<Integer> smallElems = powList.get(i).iterator();
				while (smallElems.hasNext()) {
					Integer smallElem = smallElems.next();
					Iterator<Integer> nextElems = null;
					int j = i+1;
					while (j < powList.size() && (nextElems = powList.get(j).iterator()) != null) {
						while (nextElems.hasNext()) {
							Integer nextElem = nextElems.next();
							if (smallElem.equals(nextElem)) {

								board.put(i, (int) (board.get(i) - Math.pow(2, smallElem)));
								board.put(j, (int) (board.get(j) - Math.pow(2, smallElem)));
								
//								for (Entry<Integer, Integer> entry : board.entrySet()) {
//									if (((int)Math.pow(2, smallElem)) == (entry.getValue().intValue())) {
//										System.out.println("i: "+i);
//										System.out.println("entry.getValue(): "+entry.getValue());
//										System.out.println("board.get(i+1): "+board.get(i+1));
//										board.put(entry.getKey(), (int) (entry.getValue() - Math.pow(2, smallElem)));
//										board.put(i + 1, (int) (board.get(i + 1) - Math.pow(2, smallElem)));
//										break;
//									}
//								}
								smallElems.remove();
								nextElems.remove();
								break;
							}
						}
						j++;
					}
				}
				i++;
			}
		}
		System.out.println("============");
		for (List<Integer> elemList : powList) {
			for (Integer elem : elemList) {
				System.out.print(elem + " ");
			}
			System.out.println();
		}
		System.out.println("============");
		
		for (Entry<Integer, Integer> entry : board.entrySet()) {
			System.out.println("k: "+entry.getKey()+" / v: "+entry.getValue());
		}

	}

	private static boolean hasEven() {
		init();
		for (int i = 0; i < powList.size(); i++) {
			List<Integer> smallElems = powList.get(i);
			for (int j = 0; j < smallElems.size() && i + 1 < powList.size(); j++) {
				List<Integer> nextElems = powList.get(i + 1);
				for (Integer smallElem : smallElems) {
					if (nextElems.contains(smallElem))
						return true;
				}
			}
		}
		return false;
	}

	@SuppressWarnings("boxing")
	private static void init() {
		List<List<Integer>> tempLists = new ArrayList<List<Integer>>();
		tempLists.addAll(powList);
		powList.clear();
		List<Integer> reNums = new ArrayList<>();
		int result = 0;
		for (List<Integer> templist : tempLists) {
			for (Integer num : templist) {
				result += (int) Math.pow(2, num);
			}
			reNums.add(result);
			result = 0;
		}

		Collections.sort(reNums);
		for (Integer i : reNums) {
			System.out.print(i + " ");
		}
		System.out.println();

		getOddElem(reNums);

	}

	// public static void main(String[] args) {
	// List<Integer> arrList = new ArrayList<>();
	// arrList.add(4);
	// arrList.add(5);
	// arrList.add(2);
	//
	// Collections.sort(arrList);
	// getOddElem(arrList);
	//
	// System.out.println("Real============");
	// for (List<Integer> elemList : powList) {
	// for (Integer elem : elemList) {
	// System.out.print(elem+ " ");
	// }
	// System.out.println();
	// }
	// System.out.println("============");
	//
	// List<List<Integer>> tempLists = new ArrayList<List<Integer>>();
	// tempLists.addAll(powList);
	//
	// for (int i = 0; i < powList.size(); i++) {
	// List<Integer> smallElems = powList.get(i);
	// for (int j = 0; j < smallElems.size(); j++) {
	// List<Integer> nextElems = powList.get(i+1);
	// for (Integer smallElem : smallElems) {
	// System.out.println(smallElem+" / "+nextElems.contains(smallElem));
	// if (nextElems.contains(smallElem)) {
	// tempLists.get(i).remove(smallElem);
	// tempLists.get(i+1).remove(smallElem);
	// // smallElems.remove(smallElem);
	// // nextElems.remove(smallElem);
	// }
	//
	// }
	// }
	// }
	//
	// System.out.println("Temp============");
	// for (List<Integer> elemList : tempLists) {
	// for (Integer elem : elemList) {
	// System.out.print(elem+ " ");
	// }
	// System.out.println();
	// }
	// System.out.println("============");
	//
	//
	//
	// }

	private static void getOddElem(List<Integer> arrList) {
		for (Integer i : arrList) {
			List<Integer> elemList = new ArrayList<>();
			addPow(i, elemList);
			powList.add(elemList);
		}
	}


	@SuppressWarnings("boxing")
	private static void addPow(Integer i, List<Integer> elemList) {
		if (i == 0) {
			return;
		}
		double result = Math.log(i) / Math.log(2);
		elemList.add((int) result);
		addPow((int) (i - Math.pow(2, (int) result)), elemList);
	}

}

//public class Testing {
//    public static void main(String[] args) {
//        HashMap map = new HashMap();
//        ValueComparator bvc = new ValueComparator(map);
//        TreeMap sorted_map = new TreeMap(bvc);
//
//        map.put("A", 99.5);
//        map.put("B", 67.4);
//        map.put("C", 67.4);
//        map.put("D", 67.3);
//
//        System.out.println("unsorted map: " + map);
//        sorted_map.putAll(map);
//        System.out.println("results: " + sorted_map);
//    }
//}
//
//class ValueComparator implements Comparator {
//    Map base;
//
//    public ValueComparator(Map base) {
//        this.base = base;
//    }
//
//    // Note: this comparator imposes orderings that are inconsistent with
//    // equals.
//    public int compare(String a, String b) {
//        if (base.get(a) >= base.get(b)) {
//            return -1;
//        } else {
//            return 1;
//        } // returning 0 would merge keys
//    }
//}