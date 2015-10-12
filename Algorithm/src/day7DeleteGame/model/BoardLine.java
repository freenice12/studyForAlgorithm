package day7DeleteGame.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BoardLine implements Serializable {

	private static final long serialVersionUID = 3L;
	private List<Boolean> line;

	public BoardLine(int size) {
		line = new ArrayList<Boolean>(size);
		for (int i = 0; i < size; i++) {
			line.add(Boolean.TRUE);
		}
	}

	public BoardLine(List<Boolean> boardLine) {
		line = new ArrayList<Boolean>(boardLine.size());
		line.addAll(boardLine);
	}

	public List<Boolean> getLine() {
		return line;
	}

	public Boolean getElement(int index) {
		return line.get(index);
	}

	public void setLine(List<Boolean> boardLine) {
		line.addAll(boardLine);
	}

	public int getTrueSize() {
		int count = 0;
		for (Boolean b : line) {
			if (b.equals(Boolean.TRUE))
				count++;
		}
		return count;
	}

	public int getSize() {
		return line.size();
	}

	public boolean switchState(int numOfCount) {
		if (numOfCount > getTrueSize())
			return false;
		int chCount = 0;
		for (int i = 0; chCount != numOfCount; i++) {
			if (line.get(i).booleanValue()) {
				line.set(i, Boolean.FALSE);
				chCount++;
			}
		}
		return true;
	}

	public boolean switchStateAt(int row) {
		return line.set(row, Boolean.FALSE).booleanValue();
	}

	@SuppressWarnings("boxing")
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (Boolean b : line) {
			if (b) {
				sb.append("T").append(" ");
				continue;
			}
			sb.append("F").append(" ");
		}
		return sb.toString();
	}

	// public static void main(String[] args) {
	// BoardLine b = new BoardLine(5);
	// BoardLine b1 = new BoardLine(4);
	// BoardLine b2 = new BoardLine(1);
	// List<BoardLine> bs = new ArrayList<>();
	// bs.add(b);
	// bs.add(b1);
	// bs.add(b2);
	//
	// System.out.println(b.switchState(2));
	// System.out.println(b.getLineSize());
	// printKK(bs);
	// System.out.println(b.switchState(2));
	// System.out.println(b.getLineSize());
	// printKK(bs);
	// System.out.println(b.switchState(1));
	// System.out.println(b.getLineSize());
	// printKK(bs);
	// }
	//
	// private static void printKK(List<BoardLine> bs) {
	// for (BoardLine bl : bs) {
	// System.out.println(bl);
	// }
	// }

}
