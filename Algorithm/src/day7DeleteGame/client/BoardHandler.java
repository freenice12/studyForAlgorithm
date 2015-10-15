package day7DeleteGame.client;

import java.util.Collections;
import java.util.List;
import java.util.Set;

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

	private boolean autoSwitch(boolean canPass) {
		int targetCol = board.getMaxColNum();
		int elementSize = board.getEnableElementSize(targetCol);
		List<Integer> trueList = board.getTrueSizeArray();

		Collections.sort(trueList);
		Collections.reverse(trueList);

		int result = 0;
		for (Integer ele : trueList) {
			if (result == 0)
				result -= ele.intValue();
			else
				result += ele.intValue();
		}
		System.out.println("result: " + result + " / canPass: " + canPass);
		if (result == 0 && canPass)
			return true;
		else if (result == 0 && !canPass)
			board.switchAt(targetCol, 1);
		else if (result > elementSize)
			board.switchAt(targetCol, elementSize);
		else
			board.switchAt(targetCol, Math.abs(result));

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

}
