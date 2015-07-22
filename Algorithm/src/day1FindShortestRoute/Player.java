package day1FindShortestRoute;

import java.awt.Point;
import java.util.Map;

import com.sun.media.sound.InvalidDataException;

import day1FindShortestRoute.character.Door;
import day1FindShortestRoute.character.JerryMouse;
import day1FindShortestRoute.character.Obstacle;
import day1FindShortestRoute.character.TomCat;
import day1FindShortestRoute.data.History;
import day1FindShortestRoute.enums.Direction;
import day1FindShortestRoute.exception.InvalidDirectException;
import day1FindShortestRoute.exception.NotEmptyException;
import day1FindShortestRoute.interfaces.Board;
import day1FindShortestRoute.interfaces.Jerry;
import day1FindShortestRoute.interfaces.Tom;
import day1FindShortestRoute.ui.TomJerryViewer;

public class Player {

	private Board board;
	private Jerry jerry;
	private Tom tom;

	// int oC = 200;
	public Player(Board board, Jerry jerry, Tom tom) {
		this.board = board;
		this.jerry = jerry;
		this.tom = tom;
	}

	private int obstacleCount = 0;

	public void startGame() {
		while (true) {
			if (board.isFinish()) {
				System.out.println("GAME END : " + board.getStepCount());
				break;
			}

			Direction direction = jerry.getNextMove(board);
			if (null == direction)
				continue;

			try {
				if (board.validate(direction)) {
					System.out.println("---------------" + board.getStepCount()
							+ "---------------------");
					board.moveJerry(direction);
					if (obstacleCount < 200) {
						board.addCharacter(new Obstacle(tom
								.getNextObstacle(board)));
					}
				}
				obstacleCount++;
				board.printBoard();
			} catch (InvalidDataException e) {
				e.printStackTrace();
			} catch (NotEmptyException e) {
				e.printStackTrace();
			} catch (InvalidDirectException e) {
				e.printStackTrace();
			}
		}
//		TwoTest test = new TwoTest(jerry);
//		test.run();
//		
//		TomJerryViewer viewer = new TomJerryViewer(this);
//		viewer.init();
		
		
	}

	public static void main(String[] args) throws InvalidDataException,
			NotEmptyException {
		Point startPoint = new Point(1, 1);
		Point endPoint = new Point(9, 9);
//		Point startPoint = new Point(initX(), initY());
//		Point endPoint = new Point(initX(), initY());
//
//		while (startPoint.equals(endPoint)) {
//			startPoint = new Point(initX(), initY());
//			endPoint = new Point(initX(), initY());
//		}


		System.out.println("startPoint : " + startPoint);
		System.out.println("endPoint : " + endPoint);

		Jerry jerry = new JerryMouse(startPoint);
		Tom tom = new TomCat();
		Door door = new Door(endPoint);

		Board board = new GameBoard();

		board.addCharacter(jerry);
		board.addCharacter(door);

		Player player = new Player(board, jerry, tom);
//		board.addCharacter(new Obstacle(new Point(6,9)));
//		board.addCharacter(new Obstacle(new Point(7,8)));
//		board.addCharacter(new Obstacle(new Point(8,7)));
//		board.addCharacter(new Obstacle(new Point(9,7)));
		player.startGame();
	}

	private static int initX() {
		return (int) (Math.random() * GameBoard.sizeX);
	}

	private static int initY() {
		return (int) (Math.random() * GameBoard.sizeY);
	}

	public Map<Integer, History> getHistory() {
		return jerry.getHistory();
	}
	
	

	//
	// private Board board;
	// private Jerry jerry;
	// private Tom tom;
	//
	// public Player(Board board, Jerry jerry, Tom tom) {
	// this.board = board;
	// this.jerry = jerry;
	// this.tom = tom;
	// }
	//
	// public void startGame() {
	// while(true) {
	// if(board.isFinish()) {
	// System.out.println("GAME END : " + board.getStepCount());
	// return;
	// }
	//
	// Direction direction = jerry.getNextMove(board);
	// if(null == direction)
	// continue;
	// try {
	// if (board.validate(direction)) {
	// System.out.println("---------------" + board.getStepCount() +
	// "---------------------");
	// board.moveJerry(direction);
	// board.addCharacter(new Obstacle(tom.getNextObstacle(board)));
	// }
	// } catch (InvalidDataException e) {
	// e.printStackTrace();
	// } catch (NotEmptyException e) {
	// e.printStackTrace();
	// } catch (InvalidDirectException e) {
	// e.printStackTrace();
	// }
	// // System.out.println("---------------" + board.getStepCount() +
	// "---------------------");
	// // try {
	// // board.moveJerry(direction);
	// // board.addCharacter(new Obstacle(tom.getNextObstacle(board)));
	// // } catch (InvalidDataException e) {
	// // e.printStackTrace();
	// // } catch (NotEmptyException e) {
	// // e.printStackTrace();
	// // } catch (InvalidDirectException e) {
	// // // TODO Auto-generated catch block
	// // e.printStackTrace();
	// // }
	// }
	// }
	//
	// public static void main(String[] args) throws InvalidDataException,
	// NotEmptyException {
	// // Point startPoint = new Point(initX(), initY());
	// // Point endPoint = new Point(initX(), initY());
	// //
	// // while (startPoint.equals(endPoint)) {
	// // startPoint = new Point(initX(), initY());
	// // endPoint = new Point(initX(), initY());
	// // }
	// //
	// // System.out.println("startPoint : " + startPoint);
	// // System.out.println("endPoint : " + endPoint);
	// //
	// // Jerry jerry = new JerryJY(startPoint);
	// // Tom tom = new TomCat();
	// // Door door = new Door(endPoint);
	// //
	// // Board board = new GameBoard();
	// //
	// // board.addCharacter(jerry);
	// // board.addCharacter(door);
	// //
	// // Player player = new Player(board, jerry, tom);
	// // player.startGame();
	// // Point startPoint = new Point(initX(), initY());
	// // Point endPoint = new Point(initX(), initY());
	// //
	// // while (startPoint.equals(endPoint)) {
	// // startPoint = new Point(initX(), initY());
	// // endPoint = new Point(initX(), initY());
	// // }
	//
	// System.out.println("startPoint : " + startPoint);
	// System.out.println("endPoint : " + endPoint);
	//
	// Jerry jerry = new JerryJY(startPoint);
	// Tom tom = new TomCat();
	// Door door = new Door(endPoint);
	//
	// Board board = new GameBoard();
	//
	// board.addCharacter(jerry);
	// board.addCharacter(door);
	//
	// Player player = new Player(board, jerry, tom);
	// player.startGame();
	// }
	//
	// private static int initX() {
	// return (int) (Math.random() * GameBoard.sizeX);
	// }
	//
	// private static int initY() {
	// return (int) (Math.random() * GameBoard.sizeY);
	// }
}
