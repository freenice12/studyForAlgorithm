package day1Another.smart.bono;

import java.awt.Point;
import java.util.List;
import java.util.Observer;

public class BonoPlayer {
	private Board board = new BonoBoard();

	public Board getBoard() {
		return board;
	}

	@SuppressWarnings("boxing")
	public int play(Point doorPos, Point jerryPos, Tom tom, Observer observer)
			throws Exception {
		board = new BonoBoard();

		board.addCharacter(CharacterImpl.createDoor(), doorPos);
		board.addCharacter(CharacterImpl.createJerry(), jerryPos);

		int stepCount = 0;
		while (true) {
			board.validate();
			if (board.moveJerryAndCheckDone(findNextMove(board)))
				break;

			if (stepCount < 200) {
				Point ptObstacle = tom.getNextObstacle(board);
				board.addCharacter(CharacterImpl.createObstacle(), ptObstacle);
				System.out.println("Add obstacle " + ptObstacle + ".");
			}
			stepCount++;
			if (observer != null)
				observer.update(null, null);
			System.out.println(String.format("%03d :: ", stepCount)
					+ board.findJerry() + " => " + board.findDoor());
			if (isPaused) {
				isPaused = false;
				synchronized (this) {
					this.wait();
				}
			}
		}
		System.out.println("Done. Jerry find way to home !!!");

		// SmartTomUtils.printBoard(board);
		return stepCount;
	}

	private boolean isPaused = false;

	public void pause() {
		isPaused = true;
	}

	public synchronized void resume() {
		this.notify();
	}

	private Direction findNextMove(Board board) {
		Point ptJerry = board.findJerry();
		List<Point> findWays = SmartTomUtils.findFastestWay(board);
		if (findWays.size() < 2)
			return SmartTomUtils.findAdjoinEmpty(board, ptJerry);

		Point ptNext = findWays.get(1);
		if (ptNext.x == ptJerry.x + 1)
			return Direction.EAST;
		if (ptNext.x == ptJerry.x - 1)
			return Direction.WEST;
		if (ptNext.y == ptJerry.y + 1)
			return Direction.SOUTH;
		// if (ptNext.y == ptJerry.y - 1)
		return Direction.NORTH;
	}

	public Point getNextMove() {
		return null;
	}

	public static void main(String[] args) throws Exception {
		Point jerryPos = new Point(75, 85);
		(new BonoPlayer())
				.play(new Point(5, 5), jerryPos, new SmartTom(), null);
	}
}
