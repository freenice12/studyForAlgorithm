package day7DeleteGame.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board implements Serializable {
	private static final long serialVersionUID = 2L;
	private static Board instance;
	private static Random random = new Random();
	private static int limit = 5;
	private List<BoardLine> lines = new ArrayList<>();
	private int maxColNum;
	private boolean isFinish;
	
//	private Board() {
//		lines.add(new BoardLine(1));
//		lines.add(new BoardLine(1));
//		lines.add(new BoardLine(3));
//	}
	
	private Board(int count) {
		for (int i = 0; i < count; i++) {
			lines.add(new BoardLine(random.nextInt(limit)+1));
		}
	}
	
	public Board(List<List<Boolean>> board) {
		setLines(board);
	}

	
	private void setLines(List<List<Boolean>> board) {
		lines.clear();
		for (List<Boolean> boardLine : board) {
			lines.add(new BoardLine(boardLine));
		}
	}
	
	public List<List<Boolean>> getBoard() {
		ArrayList<List<Boolean>> result = new ArrayList<List<Boolean>>();
		for (BoardLine line : lines) {
			result.add(line.getLine());
		}
		return result;
	}
	
	public static Board getInstance() {
		if (instance == null) {
			instance = new Board(random.nextInt(limit)+5);
//			instance = new Board();
		}
		return instance;
	}
	public static Board getNewInstance() {
			instance = new Board(random.nextInt(limit)+5);
		return instance;
	}
	
//	public static Board initInstance() {
//		instance = new Board(random.nextInt(limit)+5);
//		return instance;
//	}
	
	public int getTrueColumnSize() {
		int result = 0;
		for (BoardLine line : lines) {
			if (line.getTrueSize() > 0)
				result++;
		}
		return result;
	}
	
	public List<Integer> getTrueSizeArray() {
		List<Integer> result = new ArrayList<>();
		for (BoardLine line : lines) {
			Integer size = Integer.valueOf(line.getTrueSize());
			result.add(size);
		}
		return result;
	}
	
	public BoardLine getElementAt(int index) {
		return lines.get(index);
	}
	
	public boolean switchAt(int index, int count) {
		BoardLine line = lines.get(index);
		return line.switchState(count);
	}
	
	public boolean switchElementAt(int col, int row) {
		BoardLine line = lines.get(col);
		return line.switchStateAt(row);
	}
	
	public int getSize() {
		return lines.size();
	}
	
	public int getEnableColSize() {
		int enableColumnSize = 0;
		for (BoardLine line : lines) {
			if (line.getTrueSize() > 0)
				enableColumnSize++;
		}
		return enableColumnSize;
	}
	
	public Boolean getElementAt(int i, int j) {
		if (i >= getSize() || j >= lines.get(i).getSize())
			return null;
		return lines.get(i).getLine().get(j);
	}
	public int getMaxColSize() {
		int max = 0;
		for (int i=0; i<getSize(); i++) {
			if (max < getElementSize(i)) {
				max = getElementSize(i);
			}
		}
		return max;
	}
	
	public int getMaxColNum() {
		int max = 0;
		for (int i=0; i<getSize(); i++) {
			if (max < getEnableElementSize(i)) {
				max = getEnableElementSize(i);
				maxColNum = i;
			}
		}
		return maxColNum;
	}

	public int getEnableElementSize(int i) {
		return lines.get(i).getTrueSize();
	}

	public int getElementSize(int i) {
		return lines.get(i).getSize();
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (BoardLine boardLine : lines) {
			sb.append(boardLine).append("\n");
		}	
		return sb.toString();
	}

	public boolean isFinish() {
		isFinish = true;
		for (Integer trueNum : getTrueSizeArray()) {
			if (trueNum.intValue() != 0)
				isFinish = false;
		}
		return isFinish;
	}

	public boolean isEven() {
		int sum = 0;
		for (Integer eleSize : getTrueSizeArray())
			sum += eleSize.intValue();
		return sum % 2 == 0;
	}

	public boolean checkFinish() {
		int only = 0;
		int other = 0;
		for (BoardLine line : lines) {
			if (line.getTrueSize() == 1)
				only++;
			if (line.getTrueSize() > 1)
				other++;
		}
		return only == 1 && other == 0;
	}

	public int getOneCount() {
		int count = 0;
		for (BoardLine line : lines) {
			if (line.getTrueSize() == 1)
				count++;
		}
		return count;
	}

	public void switchOtherLine() {
		int count = 0;
		for (BoardLine line : lines) {
			if (line.getTrueSize() != 0)
				count++;
		}
		if (count == 2 && getOneCount() > 0) {
			for (BoardLine line : lines) {
				if (line.getTrueSize() > 1) {
					line.switchState(line.getTrueSize());
					break;
				}
			}
		} 
		if (count == 2 && getOneCount() == 2) {
			for (BoardLine line : lines) {
				if (line.getTrueSize() == 1) {
					line.switchState(line.getTrueSize());
					break;
				}
			}
		}
		if (count == 3 && getOneCount() > 1) {
			switchAt(getMaxColNum(), getMaxColSize() - 1);
		}
	}
	
}
