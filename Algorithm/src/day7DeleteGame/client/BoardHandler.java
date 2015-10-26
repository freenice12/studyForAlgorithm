package day7DeleteGame.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.swt.graphics.Point;

import day7DeleteGame.model.Board;

public class BoardHandler implements GameMapHandler {
	private Board board;
	private boolean isChanged = true;

	private int clientsNum;

	public BoardHandler() {
	}

	@Override
	public Board convertToBoard(List<List<Boolean>> lists) {
		this.board = new Board(lists);
		return board;
	}

	@Override
	public List<List<Boolean>> modifyMap(Set<Point> selectedPoints,
			boolean isAuto, boolean canPass) {
		if (!isAuto) {
			switchSelectedElement(selectedPoints);
			return board.getBoard();
		}
		if (autoSwitch(canPass))
			return Collections.EMPTY_LIST;
		return board.getBoard();
	}

	private void switchSelectedElement(Set<Point> selectedPoints) {
		for (Point point : selectedPoints) {
			board.switchElementAt(point.x, point.y);
		}
	}

	@SuppressWarnings("boxing")
	private void addPowElements(Integer i, List<Integer> elemList) {
		if (i == 0) {
			return;
		}
		double result = Math.log(i) / Math.log(2);
		elemList.add((int) result);
		addPowElements((int) (i - Math.pow(2, (int) result)), elemList);
	}

	private HashMap<Integer, List<Integer>> powElemMap;

	@SuppressWarnings("boxing")
	private boolean autoSwitch(boolean canPass) {

		List<Integer> numArray = board.getTrueSizeArray();

		if (canFinish(numArray)) {
			board.switchOtherLine();
			return false;
		}

		powElemMap = new HashMap<Integer, List<Integer>>();
		for (int index = 0; index < numArray.size(); index++) {
			List<Integer> powElems = new ArrayList<>();
			addPowElements(numArray.get(index), powElems);
			powElemMap.put(index, powElems);
		}

		PowMapSorter sorter = new PowMapSorter(powElemMap);
		Map<Integer, List<Integer>> sortedMap = new TreeMap<Integer, List<Integer>>(
				sorter);
		sortedMap.putAll(powElemMap);
//		System.out.println(powElemMap);

		List<Integer> sortedList = new ArrayList<>();
		for (Entry<Integer, List<Integer>> entry : sortedMap.entrySet()) {
			sortedList.add(entry.getKey());
		}

		// System.out.println("sorting key: "+sortedList);

		while (hasEven()) {
			for (int i = 0; i < sortedList.size(); i++) {
				List<Integer> preElems = powElemMap.get(sortedList.get(i));
				for (int j = i + 1; j < powElemMap.size(); j++) {
					List<Integer> nextElems = powElemMap.get(sortedList.get(j));
					deleteEvenElem(preElems, nextElems);
					// System.out.println("pre: "+preElems);
					// System.out.println("next: "+nextElems);
				}
			}
			break;
		}

		// System.out.println(powElemMap);
		// int index = 0;
		// int value = 0;
		// int max = 0;
		// int other = 0;
		// for (Entry<Integer, List<Integer>> entry : powElemMap.entrySet()) {
		// if ( !entry.getValue().isEmpty() && value <
		// getPowValue(entry.getKey()) ){
		// index = entry.getKey();
		// max = getPowValue(entry.getKey());
		// } else if (!entry.getValue().isEmpty())
		// other += getPowValue(entry.getKey());
		// }

		// 123 되는거 -> 111 되는거
		// -1은 한줄 지우기 젤 큰거??

		int deleteIndex = 0;
		int maxValue = 0;
		int otherValue = 0;
		for (Entry<Integer, List<Integer>> entry : powElemMap.entrySet()) {
			if (!entry.getValue().isEmpty()
					&& maxValue < getPowValue(entry.getKey())) {
				deleteIndex = entry.getKey();
				maxValue = getPowValue(entry.getKey());
			}
			if (!entry.getValue().isEmpty())
				otherValue += getPowValue(entry.getKey());
		}

		int deleteCount = maxValue - (otherValue - maxValue);
		System.out.println("index: " + deleteIndex + " / count: " + deleteCount);

		if (deleteCount == 0 && canPass) {
			return true;
		} else if (deleteCount == 0 && !canPass) {
			board.switchAt(board.getMaxColNum(), 1);
		} else if (board.getElementAt(deleteIndex).getTrueSize() == deleteCount
				&& board.getOneCount() == 2) {
			board.switchAt(deleteIndex, deleteCount - 1);
		} else if ((board.getElementAt(deleteIndex).getTrueSize() == deleteCount)) {
			board.switchAt(deleteIndex, deleteCount);
		} else {
			board.switchAt(deleteIndex, deleteCount);
		}

		return false;

	}

	@SuppressWarnings("boxing")
	private boolean canFinish(List<Integer> numArray) {
		int count = 0;
		for (Integer num : numArray) {
			if (num != 0)
				count ++;
		}
		if (count == 2 && board.getOneCount() == 1)
			return true;
		return false;
	}

	private void deleteEvenElem(List<Integer> preElems, List<Integer> nextElems) {
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
	private boolean hasEven() {
		for (int i = 0; i < powElemMap.size(); i++) {
			List<Integer> preElems = powElemMap.get(i);
			for (int j = i + 1; j < powElemMap.size(); j++) {
				List<Integer> nextElems = powElemMap.get(j);
				if (compareEachElem(preElems, nextElems))
					return true;
			}
		}
		return false;
	}

	private boolean compareEachElem(List<Integer> preElems,
			List<Integer> nextElems) {
		for (Integer smallElem : preElems) {
			if (nextElems.contains(smallElem))
				return true;
		}
		return false;
	}

	// private void autoSwitch() {
	// Another algorithm
	// int targetCol = board.getMaxColNum();
	// int elementSize = board.getEnableElementSize(targetCol);
	// if (board.getTrueColumnSize() > clientsNum + 1) {
	// board.switchAt(targetCol, elementSize);
	// } else if (board.getTrueColumnSize() == clientsNum + 1 && elementSize > 1
	// || elementSize > 1) {
	// board.switchAt(targetCol, elementSize - 1);
	// } else if(board.checkFinish()) {
	// isChanged = false;
	// } else {
	// board.switchAt(targetCol, elementSize);
	// }
	// }

	@Override
	public List<List<Boolean>> getBoard() {
		return board.getBoard();
	}

	@Override
	public boolean isChanged() {
		return isChanged;
	}

	@Override
	public void setClientsNum(int size) {
		this.clientsNum = size;
	}

	@Override
	public String toString() {
		return board.toString();
	}

	@SuppressWarnings("boxing")
	private int getPowValue(Integer key) {
		int result = 0;
		for (Integer value : powElemMap.get(key)) {
			result += Math.pow(2, value);
		}
		return result;
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
