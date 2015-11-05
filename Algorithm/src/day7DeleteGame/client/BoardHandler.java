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
	public Board board;
	private boolean isChanged = true;

	private HashMap<Integer, List<Integer>> powElemMap;
	public int deleteIndex;
	public int deleteCount;

	private int clientsNum;

	public BoardHandler() {
	}

	@Override
	public Board convertToBoard(List<List<Boolean>> lists) {
		this.board = new Board(lists);
		return board;
	}

	@Override
	public List<List<Boolean>> getModifiedBoard(Set<Point> selectedPoints,
			boolean isAuto, boolean canPass) {

		if (!isAuto) {
			switchSelectedElement(selectedPoints);
			return board.getBoard();
		}

		if (findAnswer(canPass))
			return board.getBoard();
		return Collections.EMPTY_LIST;
	}

	public void switchSelectedElement(Set<Point> selectedPoints) {
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

	public boolean findAnswer(boolean canPass) {
		// [21,11,18]
		List<Integer> numArray = board.getTrueSizeArray();

		boolean hasSolution = true;
		
		int remainLines = getRemainLines(numArray);
		if (remainLines == 1) {
			if (board.getMaxColSize() > 1)
				board.switchAt(board.getMaxColNum(), board.getMaxColSize() - 1);
			else 
				board.switchAt(board.getMaxColNum(), board.getMaxColSize());
		} else if (remainLines == 2 && board.getOneCount() > 0) {
			board.switchOtherLine();
		} else if (remainLines == 3) {
			board.switchOtherLine();
		}

		if (!findBestSelection(canPass, numArray)) 
			hasSolution = false;
		return hasSolution;
	}

	private boolean findBestSelection(boolean canPass, List<Integer> numArray) {
		addPowElements(numArray);

		// [1 = 11,2 = 18,0 = 21]
		Map<Integer, List<Integer>> sortedMap = getSortedPowMap();

		// [1, 2, 0]
		List<Integer> sortedList = getSortedListByIndex(sortedMap);

		while (hasEven()) {
			deleteEven(sortedList);
		}

		findIndexAndCount();

		if (deleteCount == 0 && !canPass)
			return false;
		return true;
//		if (modifyBoard(canPass))
//			return true;
//		return false;
	}

	private List<Integer> getSortedListByIndex(
			Map<Integer, List<Integer>> sortedMap) {
		List<Integer> sortedList = new ArrayList<>();
		for (Entry<Integer, List<Integer>> entry : sortedMap.entrySet()) {
			sortedList.add(entry.getKey());
		}
		return sortedList;
	}

	private Map<Integer, List<Integer>> getSortedPowMap() {
		PowMapSorter sorter = new PowMapSorter(powElemMap);
		Map<Integer, List<Integer>> sortedMap = new TreeMap<Integer, List<Integer>>(
				sorter);
		sortedMap.putAll(powElemMap);
		// System.out.println(powElemMap);
		return sortedMap;
	}

	@SuppressWarnings("boxing")
	private void addPowElements(List<Integer> numArray) {
		powElemMap = new HashMap<Integer, List<Integer>>();
		for (int index = 0; index < numArray.size(); index++) {
			List<Integer> powElems = new ArrayList<>();
			addPowElements(numArray.get(index), powElems);
			powElemMap.put(index, powElems);
		}
	}

	@SuppressWarnings("boxing")
	private int getRemainLines(List<Integer> numArray) {
		int count = 0;
		for (Integer num : numArray) {
			if (num != 0)
				count++;
		}
		return count;
//		if (count == 2 && board.getOneCount() == 1)
//			return true;
//		else if (count == 1)
//			return true;
//		return false;
	}

	@SuppressWarnings("boxing")
	private boolean hasEven() {
		for (int i = 0; i < powElemMap.size(); i++) {
			List<Integer> prePowElems = powElemMap.get(i);
			for (int j = i + 1; j < powElemMap.size(); j++) {
				List<Integer> nextPowElems = powElemMap.get(j);
				if (compareElems(prePowElems, nextPowElems))
					return true;
			}
		}
		return false;
	}
	
	private boolean compareElems(List<Integer> preElems, List<Integer> nextElems) {
		for (Integer smallElem : preElems) {
			if (nextElems.contains(smallElem))
				return true;
		}
		return false;
	}
	
	private void deleteEven(List<Integer> sortedList) {
		for (int i = 0; i < sortedList.size(); i++) {
			List<Integer> preElems = powElemMap.get(sortedList.get(i));
			for (int j = i + 1; j < powElemMap.size(); j++) {
				List<Integer> nextElems = powElemMap.get(sortedList.get(j));
				deleteEvenElem(preElems, nextElems);
				// System.out.println("pre: "+preElems);
				// System.out.println("next: "+nextElems);
			}
		}
	}
	
	private void deleteEvenElem(List<Integer> preElems, List<Integer> nextElems) {
		Iterator<Integer> preExponentIter = preElems.iterator();
		while (preExponentIter.hasNext()) {
			Integer preExponent = preExponentIter.next();
			Iterator<Integer> nextExponentIter = nextElems.iterator();
			while (nextExponentIter.hasNext()) {
				Integer nextExponent = nextExponentIter.next();
				if (preExponent.equals(nextExponent)) {
					preExponentIter.remove();
					nextExponentIter.remove();
				}
			}
		}
	}
	
	@SuppressWarnings("boxing")
	private void findIndexAndCount() {
		deleteIndex = 0;
		int maxValue = 0;
		int otherValue = 0;
		int powValue = 0;
		for (Entry<Integer, List<Integer>> entry : powElemMap.entrySet()) {
//			int powValue = getPowValue(entry.getKey());
			powValue = getPowValue(entry.getKey());
			if (!entry.getValue().isEmpty() && maxValue < powValue) {
				deleteIndex = entry.getKey();
				maxValue = powValue;
			}
			if (!entry.getValue().isEmpty())
				otherValue += powValue;
		}
		deleteCount = 2 * maxValue - otherValue;
		
//		deleteCount = maxValue - (otherValue - maxValue);
		// System.out.println("index: " + deleteIndex + " / count: " + deleteCount);
	}
	
	public boolean modifyBoard(boolean canPass) {
		if (deleteCount == 0 && canPass) {
			return false;
		} else if (deleteCount == 0 && !canPass) {
			board.switchAt(board.getMaxColNum(), 1);
		} else if (board.getElementAt(deleteIndex).getTrueSize() == deleteCount	&& board.getOneCount() == 2) {
			board.switchAt(deleteIndex, deleteCount - 1);
		} else {
			board.switchAt(deleteIndex, deleteCount);
		}
		return true;
	}

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

	public int getClientsNum() {
		return clientsNum;
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

	public int getDeleteIndex() {
		return deleteIndex;
	}

	public int getDeleteCount() {
		return deleteCount;
	}

}

class PowMapSorter implements Comparator<Integer> {
	Map<Integer, List<Integer>> base;

	public PowMapSorter(Map<Integer, List<Integer>> base) {
		this.base = base;
	}

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
