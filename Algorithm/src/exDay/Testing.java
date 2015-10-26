package exDay;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Testing {
	
	static Map<Integer, List<Integer>> powElemMap;
    @SuppressWarnings("boxing")
	public static void main(String[] args) {
    	List<Integer> numArray = new ArrayList<Integer>();
    	numArray.add(5);
    	numArray.add(12);
    	numArray.add(4);
    	
    	powElemMap = new HashMap<Integer, List<Integer>>();
		for (int index = 0; index < numArray.size(); index++) {
			List<Integer> powElems = new ArrayList<>();
			addPowElements(numArray.get(index), powElems);
			powElemMap.put(index, powElems);
		}

		PowMapSorter sorter = new PowMapSorter(powElemMap);
		Map<Integer, List<Integer>> sortedMap = new TreeMap<Integer, List<Integer>>(sorter);
		sortedMap.putAll(powElemMap);
		System.out.println(powElemMap);
		
		List<Integer> sortedList = new ArrayList<>();
		for (Entry<Integer, List<Integer>> entry : sortedMap.entrySet()) {
			sortedList.add(entry.getKey());
		}
		
		System.out.println("sorting key: "+sortedList);
		
		while (hasEven()) {
			for (int i = 0; i < sortedList.size(); i++) {
				List<Integer> preElems = powElemMap.get(sortedList.get(i));
				for (int j = i+1; j < powElemMap.size(); j++) {
					List<Integer> nextElems = powElemMap.get(sortedList.get(j));
					deleteEvenElem(preElems, nextElems);
//					System.out.println("pre: "+preElems);
//					System.out.println("next: "+nextElems);
				}
			}
			break;
		}
		
		System.out.println(powElemMap);
		// 123 되는거 -> 111 되는거
		// -1은 한줄 지우기 젤 큰거
		
		int index = 0;
		int max = 0;
		int other = 0;
		for (Entry<Integer, List<Integer>> entry : powElemMap.entrySet()) {
			if ( !entry.getValue().isEmpty() && max < getPowValue(entry.getKey()) ){
				index = entry.getKey();
				max = getPowValue(entry.getKey());
			}
			if (!entry.getValue().isEmpty())
				other += getPowValue(entry.getKey());
		}
		
		int deleteCount = max - (other-max);
		System.out.println("index: "+index+" / count: "+deleteCount);
		
		if (deleteCount == 0) {
			System.out.println("1");
//			System.out.println("index: "+index+" / count: "+deleteCount);
//		} else if (board.getElementAt(deleteIndex).getTrueSize() == deleteCount) {
//			System.out.println("2");
//			System.out.println("index: "+index+" / count: "+deleteCount +" -1");
//		} else {
//			System.out.println("3");
//			System.out.println("index: "+index+" / count: "+deleteCount );
		}
		
		

    }
    @SuppressWarnings("boxing")
	private static int getPowValue(Integer key) {
		int result = 0;
		for (Integer value : powElemMap.get(key)) {
			result += Math.pow(2, value);
		}
		return result;
	}
    
    private static void deleteEvenElem(List<Integer> preElems,
			List<Integer> nextElems) {
    	Iterator<Integer> preElemsIter = preElems.iterator();
    	while (preElemsIter.hasNext()) {
    		Integer pre = preElemsIter.next();
    		Iterator<Integer> nextElemsIter = nextElems.iterator();
    		while (nextElemsIter.hasNext()) {
    			Integer next = nextElemsIter.next();
    			if (pre.equals(next)) {
    				preElemsIter.remove();
    				nextElemsIter.remove();
    			}
    		}
    	}
	}

	@SuppressWarnings("boxing")
	private static boolean hasEven() {
		for (int i = 0; i < powElemMap.size(); i++) {
			List<Integer> preElems = powElemMap.get(i);
			for (int j = i+1; j < powElemMap.size(); j++) {
				List<Integer> nextElems = powElemMap.get(j);
				if (compareEachElem(preElems, nextElems))
					return true;
			}
		}
		return false;
	}
    
    private static boolean compareEachElem(List<Integer> preElems, List<Integer> nextElems) {
		for (Integer smallElem : preElems) {
			if (nextElems.contains(smallElem))
				return true;
		}
		return false;
	}
    
    @SuppressWarnings("boxing")
	private static void addPowElements(Integer i, List<Integer> elemList) {
		if (i == 0) {
			return;
		}
		double result = Math.log(i) / Math.log(2);
		elemList.add((int) result);
		addPowElements((int) (i - Math.pow(2, (int) result)), elemList);
	}
}
class PowMapSorter implements Comparator<Integer> {
	Map<Integer, List<Integer>> base;

	public PowMapSorter(Map<Integer, List<Integer>> base) {
		this.base = base;
	}

	// // Note: this comparator imposes orderings that are inconsistent with
	// // equals.
	// public int compare(String a, String b) {
	// if (base.get(a) >= base.get(b)) {
	// return -1;
	// } else {
	// return 1;
	// } // returning 0 would merge keys
	// }

	@Override
	public int compare(Integer o1, Integer o2) {
		int pre = getValue(o1);
		int next = getValue(o2);
		if (pre >= next)
			return 1;
		return -1;
	}

	@SuppressWarnings("boxing")
	private int getValue(Integer key) {
		int result = 0;
		for (Integer value : base.get(key)) {
			result += Math.pow(2, value);
		}
		return result;
	}
}