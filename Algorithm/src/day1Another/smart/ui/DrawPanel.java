package day1Another.smart.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

import day1Another.smart.bono.Board;
import day1Another.smart.bono.BonoPlayer;

public class DrawPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private final BonoPlayer player;
	private List<Point> jerryWays = null;
	private final int UNIT_PIXEL = 7;

	public DrawPanel(BonoPlayer player) {
		this.player = player;
	}

	public synchronized void setJerryWay(List<Point> ptWay) {
		jerryWays = ptWay;
	}

	public synchronized List<Point> getJerryWay() {
		if (jerryWays == null)
			return Collections.emptyList();
		return new ArrayList<Point>(jerryWays);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Board board = player.getBoard();

		for (int x = 0; x < Board.WIDTH; x++) {
			for (int y = 0; y < Board.HEIGHT; y++) {
				g.setColor(Color.LIGHT_GRAY);
				g.fill3DRect(x * UNIT_PIXEL, y * UNIT_PIXEL, UNIT_PIXEL,
						UNIT_PIXEL, true);
			}
		}

		g.setColor(Color.BLUE);
		for (Point way : getJerryWay()) {
			g.fill3DRect(way.x * UNIT_PIXEL, way.y * UNIT_PIXEL, UNIT_PIXEL,
					UNIT_PIXEL, true);
		}

		for (int x = 0; x < Board.WIDTH; x++) {
			for (int y = 0; y < Board.HEIGHT; y++) {
				Color color = null;
				switch (board.getCharacter(x, y).getType()) {
				case DOOR:
					color = Color.RED;
					break;
				case JERRY:
					color = Color.ORANGE;
					break;
				case OBSTACLE:
					color = Color.BLACK;
					break;
				default:
					// do nothing
				}

				if (color != null) {
					g.setColor(color);
					g.fill3DRect(x * UNIT_PIXEL, y * UNIT_PIXEL, UNIT_PIXEL,
							UNIT_PIXEL, true);
				}
			}
		}

	}

}
