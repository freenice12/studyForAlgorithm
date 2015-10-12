package day7DeleteGame.client;

import java.util.List;
import java.util.Set;

import org.eclipse.swt.graphics.Point;

import day7DeleteGame.model.Board;

public class MapHandler {

//	private int clientSize;
	private Board board;

	public MapHandler() {
	}

	public Board convertToBoard(List<List<Boolean>> lists) {
		this.board = new Board(lists);
		return board;
	}

	public List<List<Boolean>> modifyMap(Set<Point> selectedPoints,	boolean isAuto) {
		if (!isAuto) {
			switchSelectedElement(selectedPoints);
			return board.getBoard();
		}
		switchAllMaxCol();
//		if (board.getEnableColSize() == 1 && board.getEnableElementSize(board.getMaxColNum()) == 1) {
//			switchAllMaxCol();
//		}else if (clientSize % 2 == 0 && board.getEnableColSize() > 2) {
//			switchAutoSelectedElement();
//		} else if(board.getEnableColSize() < 3) {
//			if (board.getTrueSizeArray().contains(Integer.valueOf(1)))
//				switchAllMaxCol();
//			else
//				switchMaxColLeaveOne();
//		} else {
//			switchAllMaxCol();
//		}
//			
		return board.getBoard();
	}

//	private void switchAutoSelectedElement() {
//		if (board.getEnableColSize() % 2 == 0 && board.isEven())
//			board.switchAt(board.getMaxColNum(), 1);
//		else if (board.getEnableColSize() % 2 == 0 && !board.isEven())
//			switchAllMaxCol();
//		else if (board.getEnableColSize() % 2 != 0 && !board.isEven())
//			switchMaxColLeaveOne();
//		else
//			switchAllMaxCol();
//	}

//	private void switchMaxColLeaveOne() {
//		board.switchAt(board.getMaxColNum(), board.getEnableElementSize(board.getMaxColNum()) - 1);
//	}

	private void switchSelectedElement(Set<Point> selectedPoints) {
		for (Point point : selectedPoints) {
			board.switchElementAt(point.x, point.y);
		}
	}

	private void switchAllMaxCol() {
		System.out.println("col: "+board.getMaxColNum()+" /count: " + board.getMaxColSize());
		board.switchAt(board.getMaxColNum(), board.getEnableElementSize(board.getMaxColNum()));
	}

//	public void setClientsNum(int size) {
//		clientSize = size;
//	}

}