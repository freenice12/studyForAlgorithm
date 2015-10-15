package day7DeleteGame.client;

import java.util.List;
import java.util.Set;

import org.eclipse.swt.graphics.Point;

import day7DeleteGame.model.Board;

public interface GameMapHandler {
	public Board convertToBoard(List<List<Boolean>> lists);
	public List<List<Boolean>> modifyMap(Set<Point> selectedPoints,	boolean isAuto, boolean canPass);
	public List<List<Boolean>> getBoard();
	public boolean isChanged();
	public void setClientsNum(int size);
}
